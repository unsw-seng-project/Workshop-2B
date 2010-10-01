package sengGroup.model.accounts
import sengGroup.model.network._;
import sengGroup.model.ResponseObject


class Account (var accessDevice : AccessDevice, val accountId : Int) {

	type Concession = String

        var isLoggedInSomewhere :Boolean = false;

	var concession : Concession = "Student"
	//def getConcession = concession
		
	var balance : Int = 0
	//def getBalance = balance
	
	var status = AccountStatus.Enabled
	//def getStatus = status
	
	var travelHistory : List[TravelRecord] = List()
	//def getTravelHistory = travelHistory
	
	var userEntryPoint: EntryPoint = constants.NOENTRY
	//def getUserEntryPOint = userEntryPoint

       var isPersonalAccount:Boolean = false


	def setBalance (amount : Int){ balance = amount}
	
	def addToBalance (amount : Int):ResponseObject = {
		var successful : Boolean = false
                var message: String = ""

		if (!System.currentAccessDevices.contains(accessDevice)
				&& balance + amount <= constants.MAXBALANCE
				&& status == AccountStatus.Enabled) {
			balance += amount
			successful = true
		}
		if (!successful) message = "error adding to balance"
		return new ResponseObject(successful, message)
	}
	
	def subtractFromBalance (amount : Int):ResponseObject = {
		var successful : Boolean = false
                var message: String = ""
		if (!System.currentAccessDevices.contains(accessDevice)
				&& balance - amount >= constants.MINBALANCE
				&& status == AccountStatus.Enabled) {
			balance -= amount
			successful = true
		}
                if (!successful) message = "error subtracting from balance"
		return new ResponseObject(successful, message)
	}
	
	def startTrip (entryPoint : EntryPoint):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""
                var ro : ResponseObject = new ResponseObject(false,"")

		if (!Network.routeStartsWith(entryPoint)) ro = new ResponseObject(false, "Not a valid entry location")
		if (userEntryPoint != constants.NOENTRY) ro = new ResponseObject(false, "User already travelling")
		if (balance < constants.MINBALANCE + constants.PENALTYFARE) ro = new ResponseObject(false, "Not enough money")
		if (status != AccountStatus.Enabled) ro = new ResponseObject(false, "Acount is disabled")

                else {
			userEntryPoint = entryPoint
			ro = new ResponseObject(true, "Trip started successfully")
		}
		return ro
		
	}
	def endTrip (exitPoint : ExitPoint):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""
		val route = userEntryPoint -> exitPoint
                var ro : ResponseObject = new ResponseObject(false,"")

		if (!Network.routes.contains(route)) ro = new ResponseObject(false, "Exit not connected to your entry point")
		if (usersFare(route) > (balance - constants.MINBALANCE)) ro = new ResponseObject(false, "Not enough money to finish trip")
		if (status != AccountStatus.Enabled) ro = new ResponseObject(false, "Acount is disabled")

                else {
			
			travelHistory =  TravelRecordFactory.newRecord(route, usersFare(route)) :: travelHistory
			balance -= usersFare(route)
			userEntryPoint = constants.NOENTRY

			ro = new ResponseObject(true, "Trip completed successfully")
		}
		return ro
	}
	
	private def usersFare(route:(EntryPoint,ExitPoint)): Int = {
		return (Network.fare(route) - System.concessionRate(concession))
	}




  //personal account stuff
        var name:String = ""
        var password:String = ""
        var address:String = ""

	def changePassword (newPassword:String) = {
		password = newPassword
	}

	def changePersonalDetails (newName:String, pass:String, newAddress:String):Boolean = {
		var successful : Boolean = false
		if (pass.equals(password) && status == AccountStatus.Enabled) {
			name = newName
                        address = newAddress
                        successful = true
		}
                return successful
	}

        def upgrade (newName:String, pass:String, address:String):Boolean = {
		var successful : Boolean = false
		if (status == AccountStatus.Enabled && !isPersonalAccount) {
			name = newName
                        password = pass
                        successful = true
                        isPersonalAccount = true
		}
                return successful

	}
}
