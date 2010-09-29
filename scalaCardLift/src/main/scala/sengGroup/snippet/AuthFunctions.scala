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



class AuthFunctions {

   def  logout  ()  :  Box[LiftResponse] = {
      if(authSessionVar.is != 0) {
        SystemManagement.getAccount(authSessionVar.is).get.isLoggedInSomewhere = false;
      }
      authSessionVar.remove();

      isLoggedIn.set(false)
      //redirect to home
      return Full(RedirectResponse("Logout"));

    }
    def loginStatus(xhtml: NodeSeq): NodeSeq = {
      if (authSessionVar.is == 0 ) {
        bind("loggedIn", xhtml, "Login" -> "Login")
      }
      else {
        bind ("loggedIn", xhtml, "Login" ->  authSessionVar.is.toString)
      }
      
    }

    def  loginAnon(xhtml  :  NodeSeq)  :  NodeSeq  =  {
      var  user  =  "";
      def  auth  ()  =  {
        if (SystemManagement.accountExists(user.toInt)) {
          if (!SystemManagement.getAccount(user.toInt).get.isLoggedInSomewhere) {
            authSessionVar.set(user.toInt)
            var currentUser =  SystemManagement.getAccount(authSessionVar.is).get
            isLoggedIn.set(true)
            currentUser.isLoggedInSomewhere = true;
            S.redirectTo("account/details")
          }
          else S.error("You are already logged in")

        } else {
          S.error("User Does not Exist");
        }

      }
      bind("login",  xhtml,
           "user"  ->  SHtml.text(user,  user  =  _,  "maxlength"  ->  "40",  "onKeyPress"->"return checkIt(event)"),
           "submit"  ->  SHtml.submit("Login",  auth))
    }
}
