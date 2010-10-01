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

class Trip {
  
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

