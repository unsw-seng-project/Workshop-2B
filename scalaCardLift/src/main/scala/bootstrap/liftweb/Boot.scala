package bootstrap.liftweb

import _root_.sengGroup.model.accounts._
import _root_.sengGroup.snippet._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._


/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("sengGroup")

   
   val AuthRequired = If(() => isLoggedIn.get,
                                       () => RedirectResponse("/login"))

   val AuthNotRequired = If(() => !isLoggedIn.get,
                                       () => RedirectResponse("/index"))

    val AdminOnly = If(() => adminLoggedIn.get,
                                       () => RedirectResponse("/adminlogin"))


     //   println("not logged in " + (authSessionVar.get))

    // Build SiteMap
    val entries = Menu(Loc("Home", List("index"), "Home")) ::
                  Menu(Loc("Account", List("account") -> true, "Account", AuthRequired)) ::
                  Menu(Loc("Logout", List("logout"), "Logout"))::
                  Menu(Loc("Login", List("login"), "Login", AuthNotRequired))::
                  Menu(Loc("AdminLogin", List("adminlogin"), "AdminLogin"))::
                  Menu(Loc("AdminLogout", List("adminlogout"), "AdminLogout"))::
                  Menu(Loc("Admin", List("admin"), "Admin", AdminOnly))::
                  Menu(Loc("Status", List("status"), "status" )) ::
                  Menu(Loc("MakeTrip", List("makeTrip"), "MakeTrip"))::
                  Menu(Loc("Routes", List("routes"), "Routes"))::
                  Menu(Loc("iPhone", List("iPhone"), "iPhone")) :: Nil
                  Menu(Loc("demo", List("demo/index"), "demo")) :: Nil

    LiftRules.setSiteMap(SiteMap(entries:_*))

   // LiftRules.dispatch.append  {
   //   case  Req("logout"  ::  Nil,  _,  _)  => AuthFunctions.logout  _
   // }
  }
}

