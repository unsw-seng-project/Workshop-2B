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
      authSessionVar.remove();
      isLoggedIn.set(false)
      //redirect to home
      return Full(RedirectResponse("/logout.html"));

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
          authSessionVar.set(user.toInt)
          isLoggedIn.set(true)
          S.redirectTo("/myAccount.html")

        } else {
          S.error("User Does not Exist");
        }

      }
      bind("login",  xhtml,
           "user"  ->  SHtml.text(user,  user  =  _,  "maxlength"  ->  "40",  "onKeyPress"->"return checkIt(event)"),
           "submit"  ->  SHtml.submit("Login",  auth))
    }
}
