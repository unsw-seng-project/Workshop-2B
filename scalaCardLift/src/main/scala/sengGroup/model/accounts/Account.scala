package sengGroup.model.accounts
import sengGroup.model.network._;

class Account (var accessDevice : AccessDevice, val accountId : Int) {
	type Concession = String
	
	var concession : Concession = "Student"
	//def getConcession = concession
		
	var balance : Int = 0
	//def getBalance = balance
	
	var status = AccountStatus.Enabled
	//def getStatus = status
	
	var travelHistory : List[TravelRecord] = List()
	//def getTravelHistory = travelHistory
	
	var userEntryPoint: EntryPoint = _
	//def getUserEntryPOint = userEntryPoint
	
	def setBalance (amount : Int){ balance = amount}
	
	def addToBalance (amount : Int):Boolean = {
		var successful : Boolean = false
		if (!System.currentAccessDevices.contains(accessDevice)
				&& balance + amount <= constants.MAXBALANCE
				&& status == AccountStatus.Enabled) {
			balance += amount
			successful = true
		}
		return successful
	}
	
	def subtractFromBalance (amount : Int):Boolean = {
		var successful : Boolean = false
		if (!System.currentAccessDevices.contains(accessDevice)
				&& balance - amount >= constants.MINBALANCE
				&& status == AccountStatus.Enabled) {
			balance -= amount
			successful = true
		}
		return successful
	}
	
	def startTrip (entryPoint : EntryPoint):Boolean = {
		var successful : Boolean = false;
		if (Network.routeStartsWith(entryPoint)
				&& userEntryPoint == null
				&& !System.currentAccessDevices.contains(accessDevice)
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
				&& !System.currentAccessDevices.contains(accessDevice)
				&& usersFare(route) <= (balance - constants.MINBALANCE)
				&& status == AccountStatus.Enabled) {
			
			travelHistory :: List(TravelRecordFactory.newRecord(route, usersFare(route)))
			balance -= usersFare(route)
			userEntryPoint = null
			successful = true
		}
		return successful
	}
	
	private def usersFare(route:(EntryPoint,ExitPoint)): Int = {
		return (Network.fare(route) + System.concessionRate(concession))
	}

	def changeAccountStatus(newStatus:AccountStatus.AccountStatus):Boolean = {
		var successful : Boolean = false;
		if (newStatus != status) {
			status = newStatus
			successful = true
		}
		return successful
	}
	
	def changeConcessionType(newConcession:Concession):Boolean = {
		var successful : Boolean = false;
		if (newConcession != concession) {
			concession = newConcession
			successful = true
		}
		return successful
	}
}
