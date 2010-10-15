package sengGroup.model.network

object Network {

        val entryPoints : List[EntryPoint] = List(new EntryPoint(1, "Central"),
                                             new EntryPoint(2, "Town Hall"),
                                             new EntryPoint(3, "St Leonards"),
                                             new EntryPoint(4, "Chatswood"),
                                             new EntryPoint(5, "St James"),
                                             new EntryPoint(6, "Wynyard"),
                                             new EntryPoint(7, "Bondi"))
        val exitPoints : List[ExitPoint] = List(new ExitPoint(1, "Central - Exit"),
                                             new ExitPoint(2, "Town Hall- Exit"),
                                             new ExitPoint(3, "St Leonards- Exit"),
                                             new ExitPoint(4, "Chatswood- Exit"),
                                             new ExitPoint(5, "St James- Exit"),
                                             new ExitPoint(6, "Wynyard- Exit"),
                                             new ExitPoint(7, "Bondi- Exit"))
	
	type Route = (EntryPoint,ExitPoint)

	val routes : Set[Route] = Set(entryPoints(0) -> exitPoints(0),
                                      entryPoints(1) -> exitPoints(1),
                                      entryPoints(2) -> exitPoints(2),
                                      entryPoints(3) -> exitPoints(3),
                                      entryPoints(4) -> exitPoints(4),
                                      entryPoints(5) -> exitPoints(5),
                                      entryPoints(6) -> exitPoints(6),
                                      entryPoints(0) -> exitPoints(1),
                                      entryPoints(1) -> exitPoints(0),
                                      entryPoints(2) -> exitPoints(0),
                                      entryPoints(0) -> exitPoints(2),
                                      entryPoints(2) -> exitPoints(3),
                                      entryPoints(3) -> exitPoints(2),
                                      entryPoints(2) -> exitPoints(4),
                                      entryPoints(4) -> exitPoints(2),
                                      entryPoints(4) -> exitPoints(5),
                                      entryPoints(5) -> exitPoints(4),
                                      entryPoints(5) -> exitPoints(6),
                                      entryPoints(6) -> exitPoints(5))
	
	var fare : Map[Route, Int] = Map(entryPoints(0) -> exitPoints(0) -> 5,
                                      entryPoints(1) -> exitPoints(1) -> 5,
                                      entryPoints(2) -> exitPoints(2) -> 5,
                                      entryPoints(3) -> exitPoints(3) -> 5,
                                      entryPoints(4) -> exitPoints(4) -> 5,
                                      entryPoints(5) -> exitPoints(5) -> 5,
                                      entryPoints(6) -> exitPoints(6) -> 5,
                                      entryPoints(0) -> exitPoints(1) -> 5,
                                      entryPoints(1) -> exitPoints(0) -> 15,
                                      entryPoints(2) -> exitPoints(0) -> 15,
                                      entryPoints(0) -> exitPoints(2) -> 15,
                                      entryPoints(2) -> exitPoints(3) -> 15,
                                      entryPoints(3) -> exitPoints(2) -> 15,
                                      entryPoints(2) -> exitPoints(4) -> 15,
                                      entryPoints(4) -> exitPoints(2) -> 15,
                                      entryPoints(4) -> exitPoints(5) -> 20,
                                      entryPoints(5) -> exitPoints(4) -> 20,
                                      entryPoints(5) -> exitPoints(6) -> 20,
                                      entryPoints(6) -> exitPoints(5) -> 20)
	
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