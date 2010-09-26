package sengGroup.model.network

object Network {

        val epList : List[EntryPoint] = List(new EntryPoint(1, "London"),new EntryPoint(1, "Japan"))
        val exList : List[ExitPoint] = List(new ExitPoint(1, "LondonExit"),new ExitPoint(1, "JapanExit"))
	
	val entryPoints : List[EntryPoint] = epList
	val exitPoints : List[ExitPoint] = exList
	
	type Route = (EntryPoint,ExitPoint)

	val routes : Set[Route] = Set(epList(1) -> exList(1), epList(0) -> exList(0))
	
	var fare : Map[Route, Int] = Map()
	
	def routeStartsWith(entryPoint:EntryPoint):Boolean = 
		routes.exists(r => r._1 == (entryPoint))
		
//	def setUpEntryPoints : Set[EntryPoint] = {
//		var ep : Set[EntryPoint] = Set()
//		ep +=
///
//		return ep
//	}
//
//	def setUpExitPoints : Set[ExitPoint] = {
//		var ep : Set[ExitPoint] = Set()
//		ep += new ExitPoint(2, "Paris")
//
//		return ep
//	}


}