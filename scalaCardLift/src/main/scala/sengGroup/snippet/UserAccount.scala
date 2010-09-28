/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sengGroup.snippet

import sengGroup.model.accounts._
import net.liftweb.http.SessionVar;
import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._

import net.liftweb._
import net.liftweb.util._
import http._
import SHtml._
import S._


class UserAccount {

  var currentUser =  SystemManagement.getAccount(authSessionVar.is).get
  
  def addToBalance(xhtml: NodeSeq): NodeSeq = {
     var  amount  =  "";
     
    def validate () : Unit = {    val ro = currentUser.addToBalance(amount.toInt)
          if (ro.guardsOK) S.redirectTo("/myAccount.html")
          else {
          S.error(ro.errorMessage);
        }
    }

      bind("add",  xhtml,
           "current" -> currentUser.balance,
           "newAmount"  ->  SHtml.text(amount.toString(),  amount  =  _,  "maxlength"  ->  "40",  "onKeyPress"->"return checkIt(event)"),
           "submit"  ->  SHtml.submit("Submit",  validate))
  }
  
  def tripHistory(xhtml: NodeSeq): NodeSeq = {
    return currentUser.travelHistory.toList.flatMap(rec => bind("r",
         xhtml, "entry" -> rec.entryPoint.name,
               "exit" -> rec.exitPoint.name,
                "cost" -> rec.cost,
                "date" -> rec.date))
  }


  def accountDetails(xhtml: NodeSeq): NodeSeq = {
    return bind("accDetails",  xhtml,
           "balance"  ->  currentUser.balance,
            "accountID" -> currentUser.accountId,
            "deviceID" -> currentUser.accessDevice.deviceID,
       "LastTrip" -> currentUser.userEntryPoint.getName,
       "status" -> currentUser.status.toString,
        "NumberofTrips" -> currentUser.travelHistory.size)
  }
  
 // def personalDetails(xhtml: NodeSeq): NodeSeq = {
//       obj match {
 //       case x:PersonalAccount => bind("")
//        case _ => bind("personal", xhtml, "result" -> "This is not a personalAccount")
//}
  //}

  def newAccountForm (xhtml: NodeSeq): NodeSeq = {
      def newAccount () : Unit = {
        val account:Account = SystemManagement.newAccount
        notice("new Account created with id" + account.accountId)
      }

      bind("account", xhtml, "submit" -> submit("New Account", newAccount))

  }

}
