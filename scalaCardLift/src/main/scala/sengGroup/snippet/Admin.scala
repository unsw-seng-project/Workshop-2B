/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 package sengGroup.snippet

 import _root_.sengGroup.model.network._
 import _root_.sengGroup.model.accounts._
 import net.liftweb.http.SessionVar;
 import scala.xml.{NodeSeq}
 import net.liftweb.util.Helpers._

 import net.liftweb._
 import net.liftweb.util._
 import http._
 import SHtml._
 import S._


 object adminLoggedIn extends SessionVar[Boolean](false);


 class Admin {

    def logout(xhtml : NodeSeq) : NodeSeq = {
      adminLoggedIn.set(false)
      S.redirectTo("home")
    }

    def  login(xhtml  :  NodeSeq)  :  NodeSeq  =  {
      var  pass  =  "";
      def  auth  ()  =  {
        if (Administrator.adminPassword.equals(pass)) {
          adminLoggedIn.set(true)
          S.redirectTo("Admin")

        } else {
          S.error("Admin Password Incorrect");
        }

      }
      bind("login",  xhtml,
           "password"  ->  SHtml.password(pass,  pass  =  _),
           "submit"  ->  SHtml.submit("Login",  auth))
    }

    def  forceLogout(xhtml  :  NodeSeq)  :  NodeSeq  =  {
      var  user  =  "";
      def  logoutUser  ()  =  {
        if (SystemManagement.accountExists(user.toInt)) {
          SystemManagement.getAccount(user.toInt).get.isLoggedInSomewhere = false
          S.notice("User Logged Out")
        } else {
          S.error("User doesn't exist");
        }

      }
      bind("login",  xhtml,
           "userID"  ->  SHtml.text(user,  user  =  _),
           "submit"  ->  SHtml.submit("Reset",  logoutUser))
    }

    def changeConcession (xhtml : NodeSeq) : NodeSeq = {

      var chosenMethod: Box[String] = Full(SystemManagement.concessionRate.keys.next())
      var user: String = "";


      def processForm () : Unit = {
        if (SystemManagement.accountExists(user.toInt) && chosenMethod.isDefined) {
          println("changing concession of: " + user.toInt +" from " 
                  + SystemManagement.getAccount(user.toInt).get.concession + " to " + chosenMethod.open_!)

          SystemManagement.getAccount(user.toInt).get.concession = chosenMethod.open_!
          S.notice("Concession Updated")

        } else {
          S.error("User Does not Exist");
        }


      } //some form manipulation stuff

      bind("concession", xhtml,
           "user"  ->  SHtml.text(user,  user  =  _,  "maxlength"  ->  "40"),
           "dropDown" -> SHtml.selectObj[String](
          SystemManagement.concessionRate.keys.toList.map(v => (v,v.toString)),
          Empty, selected => chosenMethod = Full(selected) ),
           "submit" -> SHtml.submit( "Change Concession", processForm /*function to call*/)
      )
    }

     def changeStatus (xhtml : NodeSeq) : NodeSeq = {

      var chosenMethod: Box[AccountStatus.Value] = Full(AccountStatus.Enabled)
      var user: String = "";

      val radios =
      SHtml.radio( AccountStatus.elements.toList.map(_.toString), Empty,
                   selected =>
                    chosenMethod = Box(AccountStatus.valueOf(selected)) )


      def processForm () : Unit = {
        if (SystemManagement.accountExists(user.toInt)) {
          println("changing status of: " + user.toInt +" from "
                  + SystemManagement.getAccount(user.toInt).get.status + " to " + chosenMethod.open_!)

          SystemManagement.getAccount(user.toInt).get.status = chosenMethod.open_!
          S.notice("Status Updated")

        } else {
          S.error("User Does not Exist");
        }
      } //some form manipulation stuff

      bind("s", xhtml,
           "user"  ->  SHtml.text(user,  user  =  _,  "maxlength"  ->  "40"),
           "radio" -> radios.toForm,
           "submit" -> SHtml.submit( "Change Status" /*button name*/,
            processForm /*function to call*/)
      )
    }

   def ban (xhtml : NodeSeq) : NodeSeq = {

      var user: String = "";
      var deviceID: String = "";


      def processForm () : Unit = {
        if (SystemManagement.accountExists(user.toInt)
            && SystemManagement.getAccessDevice(deviceID.toInt).isDefined) {

        val account = SystemManagement.getAccount(user.toInt).get
          println("banning device of: " + user.toInt)
          
          val ro = Administrator.replaceAccessDevice(account, SystemManagement.getAccessDevice(deviceID.toInt).get)
          if (ro.guardsOK) S.notice("Device Banned with id: + " + deviceID + ", and new Device given with ID: " + account.accessDevice.deviceID)
          else S.error(ro.errorMessage)

        } else {
          S.error("Incorrect Input");
        }
      } //some form manipulation stuff

      bind("s", xhtml,
           "user"  ->  SHtml.text(user,  user  =  _,  "maxlength"  ->  "40"),
           "device" -> SHtml.text(deviceID,  deviceID  =  _,  "maxlength"  ->  "40"),
           "submit" -> SHtml.submit( "Ban Device" /*button name*/,
            processForm /*function to call*/)
      )
    }



  }
