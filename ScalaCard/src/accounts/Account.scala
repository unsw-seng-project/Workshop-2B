package accounts
import network._;

class Account (var accessDevice : AccessDevice, val accountId : Int) {
	type Concession = String 
	
	val concession : Concession = "Student"
	
	var balance : Int = 0
	var status = AccountStatus.Enabled
	var travelHistory : List[TravelRecord] = List()

	var userEntryPoint: EntryPoint = _
	
	def setBalance (amount : Int){ balance = amount}

	def getBalance():Int = balance
	
	def addToBalance (amount : Int) {balance += amount}
	def subtractFromBalance (amount : Int) {balance -= amount}
	
	def startTrip (entryPoint : EntryPoint):Boolean = {
		var successful : Boolean = false;
		if (Network.routeStartsWith(entryPoint)
				&& userEntryPoint == null
				&& balance >= constants.MINBALANCE + constants.PENALTYFARE
				&& status == AccountStatus.Enabled) {
			userEntryPoint = entryPoint
			successful = true
		}
		
		return successful
	}
	def endTrip (exitPoint : ExitPoint):Boolean = {
		var successful : Boolean = false;
		val route = userEntryPoint -> exitPoint
		if (Network.routes.contains(route)
				&& usersFare(route) <= (balance - constants.MINBALANCE)
				&& status == AccountStatus.Enabled) {
			
			travelHistory :: List(TravelRecordFactory.newRecord(route, usersFare(route)))
			balance -= usersFare(route)
			userEntryPoint = null
		}
		return successful
	}
	
	def usersFare(route:(EntryPoint,ExitPoint)): Int = {
		return (Network.fare(route) + System.concessionRate(concession))
	}
	
}
