/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sengGroup.snippet

import sengGroup.model.network._
import sengGroup.model.accounts._
import net.liftweb.http.SessionVar;
import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._

import net.liftweb._
import net.liftweb.util._
import http._
import SHtml._
import S._


class NetworkManagement {
  def routes(xhtml: NodeSeq): NodeSeq = {

    (Network.routes.toList.sort((a,b) => a._1.name < b._1.name).flatMap(ro => bind("r",
         xhtml, "entry" -> ro._1.name,
               "exit" -> ro._2.name,
                "cost" -> Network.fare(ro))))
  }

  def concession(xhtml: NodeSeq): NodeSeq =
    (SystemManagement.concessionRate.toList.flatMap(ro => bind("c",
         xhtml, "type" -> ro._1,
               "discount" -> ro._2)))

}
