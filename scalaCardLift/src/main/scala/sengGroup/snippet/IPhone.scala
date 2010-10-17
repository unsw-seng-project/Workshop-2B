/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package sengGroup.snippet

import sengGroup.model.accounts._
import sengGroup.model.network._
import net.liftweb.http.SessionVar;
import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._

import net.liftweb._
import net.liftweb.util._
import http._
import SHtml._
import S._


class IPhone {
  def viewAcc (xhtml:NodeSeq) : NodeSeq = {
    val accountId = S.param("accId")
    val acc = SystemManagement.getAccount(accountId.get.toInt).get

    val entry : String = acc.userEntryPoint match {
      case  constants.NOENTRY => "Not Travelling"
      case _ => acc.userEntryPoint.name
    }

    {
      <div id="header">

        <h1>Account Info</h1>
        <a id="backButton" href="/iphone/accounts.html">Accounts</a>
      </div>
      <h1> Account {acc.accountId} </h1>
      <ul class="field">
        <li><h3> Device ID: </h3> {acc.accessDevice.deviceID} </li>
        <li><h3> Balance: </h3> {acc.balance} </li>
        <li><h3> Status: </h3> {acc.status}</li>
        <li><h3> EntryPoint: </h3> {acc.userEntryPoint.name} </li>
        <li><h3> Concession:  </h3> {acc.concession}</li>
        <li><h3> Is Personal Account: </h3> {acc.isPersonalAccount}</li>
      </ul>


    }
  }

  def personalDetails (xhtml: NodeSeq) : NodeSeq = {
    val accountId = S.param("accId")
    val acc = SystemManagement.getAccount(accountId.get.toInt).get
    
    if (acc.isPersonalAccount) {
      return bind("acc", xhtml, "name" -> acc.name, "address" -> acc.address)
    }
    else return {<li>You need to upgrade your account</li>}
  }

  def tripHistory(xhtml: NodeSeq): NodeSeq = {
    val accountId = S.param("accId")
    println("generating trip history for " + accountId)
    val acc = SystemManagement.getAccount(accountId.get.toInt).get

    if (acc.travelHistory.size > 0) return acc.travelHistory.toList.flatMap(rec => bind("trip",
                                                                xhtml, "entry" -> rec.entryPoint.name,
                                                                "exit" -> rec.exitPoint.name,
                                                                "cost" -> rec.cost,
                                                                "date" -> rec.date))
    else return {<li>No Trips have been made</li>}
  }

  def start (xhtml : NodeSeq) : NodeSeq = {

      var chosenEntryPoint: Box[EntryPoint] = Empty
      var accessDeviceString: String = "";

      def processForm () : Unit = {
        val accessDevice = SystemManagement.getAccessDevice(accessDeviceString.toInt)
        if (accessDevice.isDefined && SystemManagement.accountExists(accessDevice.get) && chosenEntryPoint.isDefined) {
          println("starting Trip with device: " + accessDeviceString.toInt +" at "
                  + chosenEntryPoint.open_!.name)

          val ro = SystemManagement.startTrip(accessDevice.get, (chosenEntryPoint.open_!))
          S.notice(ro.errorMessage)

        } else {
          S.error("User Does not Exist");
        }
      } //some form manipulation stuff

      bind("start", xhtml,
           "deviceID"  ->  SHtml.text(accessDeviceString,  accessDeviceString  =  _,  "maxlength"  ->  "40"),
           "accessPoint" -> SHtml.selectObj[EntryPoint](
          Network.entryPoints.map(v => (v,v.name.toString)),
          Empty, selected => chosenEntryPoint = Full(selected) ),
           "submit" -> SHtml.submit( "Start Trip" /*button name*/,
            processForm /*function to call*/)
      )
    }

  def end (xhtml : NodeSeq) : NodeSeq = {

      var chosenExitPoint: Box[ExitPoint] = Empty
      var accessDeviceString: String = "";

      def processForm () : Unit = {
        val accessDevice = SystemManagement.getAccessDevice(accessDeviceString.toInt)
        if (accessDevice.isDefined && SystemManagement.accountExists(accessDevice.get) && chosenExitPoint.isDefined) {
          println("ending Trip with device: " + accessDeviceString.toInt +" at "
                  + chosenExitPoint.open_!.name)

          val ro = SystemManagement.endTrip(accessDevice.get, (chosenExitPoint.open_!))
          S.notice(ro.errorMessage)

        } else {
          S.error("User Does not Exist");
        }
      } //some form manipulation stuff

      bind("end", xhtml,
           "deviceID"  ->  SHtml.text(accessDeviceString,  accessDeviceString  =  _,  "maxlength"  ->  "40"),
           "accessPoint" -> SHtml.selectObj[ExitPoint](
          Network.exitPoints.map(v => (v,v.name.toString)),
          Empty, selected => chosenExitPoint = Full(selected) ),
           "submit" -> SHtml.submit( "End Trip" /*button name*/,
            processForm /*function to call*/)
      )
  }


}
