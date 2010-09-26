package sengGroup.snippet

import sengGroup.model.network._
import sengGroup.model.accounts._

import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._

import net.liftweb._ 
import http._
import SHtml._
import S._

class HelloWorld {
  def howdy = <span>Welcome to scalaCardLift at {Network.routes}</span>

  def routes(xhtml: NodeSeq): NodeSeq =
    (Network.routes.toList.flatMap(ro => bind("r",
         xhtml, "entry" -> ro._1.name,
               "exit" -> ro._2.name)))

  def newAccountForm (xhtml: NodeSeq): NodeSeq = {
      def newAccount () : Unit = {
        val account:Account = SystemManagement.newAccount
        notice("new Account created with id" + account.accountId)
      }

      bind("account", xhtml, "submit" -> submit("New Account", newAccount))

  }


   
}

