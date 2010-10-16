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
      <h1> Account {acc.accountId} </h1>
      <ul class="field">
        <li><h3> Balance: </h3> {acc.balance} </li>
        <li><h3> Status: </h3> {acc.status}</li>
        <li><h3> EntryPoint: </h3> {acc.userEntryPoint.name} </li>
        <li><h3> Concession:  </h3> {acc.concession}</li>
        <li><h3> Is Personal Account: </h3> {acc.isPersonalAccount}</li>
      </ul>



    }
  }


}
