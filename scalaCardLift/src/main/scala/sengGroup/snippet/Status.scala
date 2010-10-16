/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package sengGroup.snippet

import sengGroup.model.accounts._
import net.liftweb.http.SessionVar;
import scala.xml._
import net.liftweb.util.Helpers._

import net.liftweb._
import net.liftweb.util._
import http._
import SHtml._
import S._


class Status {

  def accounts (xhtml:NodeSeq) : NodeSeq = {

    (SystemManagement.accounts.toList.flatMap(acc => bind("a",
         xhtml, "id" -> SHtml.link("/iphone/acc/"+acc.accountId,() => {} , Text(S.? (acc.accountId.toString))))))
  }
       
}
