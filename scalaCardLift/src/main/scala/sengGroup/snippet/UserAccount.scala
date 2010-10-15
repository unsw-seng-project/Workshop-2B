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
     
    def validate () : Unit = {
          val ro = currentUser.addToBalance(amount.toInt)
          if (ro.guardsOK) S.redirectTo("/account/details")
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
            "concession" -> currentUser.concession,
       "LastTrip" -> currentUser.userEntryPoint.getName,
       "status" -> currentUser.status.toString,
        "NumberofTrips" -> currentUser.travelHistory.size,
        "personalStatus"-> currentUser.isPersonalAccount)
  }

  def personalLogin = {
    var name = ""
    var password = ""
    var address = ""

    def redirect (): Unit  = {
      if (currentUser.password == password) {
        S.redirectTo("/account/personal")
      }
      else S.error("Password Incorrect")

    }

    def upgrade (): Unit = {
       SystemManagement.upgrade(currentUser.accessDevice, name, password, address)

      println("current user personal status after upgrade = " + currentUser.isPersonalAccount)
      println("name = " + currentUser.name)
      println("address = " + currentUser.address)
      println("password = " + currentUser.password)

       S.redirectTo("/account/personal")
    }

      if (currentUser.isPersonalAccount) {
        <span>
        { SHtml.password("", p => password = p)}
        { submit("Login", redirect)}
        </span>
      } else {
        <div>
       	<LABEL>Name:</LABEL>
        { text("Name", n => name = n)}
        <LABEL>Address:</LABEL>
        { text("Address", a => address = a)}
        <br />
        <LABEL>New Password: </LABEL>
        { SHtml.password("", p => password = p)}
        { submit("Upgrade", upgrade)}

        </div>
      }



  }

  def changePassword = {
        if (!currentUser.isPersonalAccount) S.redirectTo("/account/personalLogin")
          var newPassword = ""
          var oldPassword = ""

      def changePassword (): Unit = {
        if (currentUser.password == oldPassword) {
          SystemManagement.changePassword(currentUser.accessDevice, oldPassword, newPassword)
          S.notice("Your password is now: " + currentUser.password)
        }
        else S.error("Password Incorrect")
      }

         <span>
          { password("", p => oldPassword = p)}
          { password("", p => newPassword = p)}
          { submit("Change Password", changePassword)}
         </span>

  }
  
  def personal = {
    if (!currentUser.isPersonalAccount) {
      println("current user personal status = " + currentUser.isPersonalAccount)
      S.redirectTo("/account/personalLogin")
    }
    var name = ""
    var password = ""
    var address = ""

    def changeDetails () : Unit = {
      var ro = SystemManagement.changeDetails(currentUser.accessDevice, name, password, address)
      S.notice(ro.errorMessage)
    }
        <div>
       	<LABEL>Name:</LABEL>
        { text("Name", n => name = n)}
        <LABEL>Address:</LABEL>
        { text("Address", a => address = a)}
        <br />
        <LABEL>Password to change:</LABEL>
        { SHtml.password("", p => password = p)}
        { submit("ChangeDetails", changeDetails)}

        </div>

  }

  def newAccountForm (xhtml: NodeSeq): NodeSeq = {
      def newAccount () : Unit = {
        val account:Account = SystemManagement.newAccount
        notice("new Account created with id" + account.accountId)
      }

      bind("account", xhtml, "submit" -> submit("New Account", newAccount))

  }


}
