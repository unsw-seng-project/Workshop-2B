package network

object Network {
	
	val entryPoints : Set[EntryPoint] = Set()
	val exitPoints : Set[ExitPoint] = Set()
	
	type Route = (EntryPoint,ExitPoint)

	val routes : Set[Route] = setUpRoutes
	
	var fare : Map[Route, Int] = Map()
	
	def routeStartsWith(entryPoint:EntryPoint):Boolean = 
		routes.unzip._1.contains(entryPoint)
		
	def setUpEntryPoints : Set[EntryPoint] = {
		var ep : Set[EntryPoint] = Set()
		ep += new EntryPoint(1, "London")
		
		return ep
	}
	
	def setUpExitPoints : Set[ExitPoint] = {
		var ep : Set[ExitPoint] = Set()
		ep += new ExitPoint(2, "Paris")
		
		return ep
	}		
		
	def setUpRoutes : Set[(EntryPoint,ExitPoint)] = {

		var r : Set[(EntryPoint,ExitPoint)] = Set()
		
		//should put some checking here.
		r += (entryPoints.find(_.getName=="London").get 
				-> exitPoints.find(_.getName=="Paris").get)
		return r		
	}
	


}