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

		if (Network.routeStartsWith(entryPoint)
				&& userEntryPoint == constants.NOENTRY
				&& balance >= constants.MINBALANCE + constants.PENALTYFARE
				&& status == AccountStatus.Enabled) {
			userEntryPoint = entryPoint
			successful = true
		}
                if (!successful) message = "error starting Trip at account level"
		return new ResponseObject(successful, message)
		
	}
	def endTrip (exitPoint : ExitPoint):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""
		val route = userEntryPoint -> exitPoint
		if (Network.routes.contains(route)
				&& usersFare(route) <= (balance - constants.MINBALANCE)
				&& status == AccountStatus.Enabled) {
			
			travelHistory =  TravelRecordFactory.newRecord(route, usersFare(route)) :: travelHistory
			balance -= usersFare(route)
			userEntryPoint = constants.NOENTRY

			successful = true
		}
		if (!successful) message = "error ending Trip"
		return new ResponseObject(successful, message)
	}
	
	private def usersFare(route:(EntryPoint,ExitPoint)): Int = {
		return (Network.fare(route) - System.concessionRate(concession))
	}

	def changeAccountStatus(newStatus:AccountStatus.AccountStatus):ResponseObject = {
		var successful : Boolean = false;
                 var message: String = ""

		if (newStatus != status) {
			status = newStatus
			successful = true
		}
		if (!successful) message = "error changing status"
		return new ResponseObject(successful, message)
              }
	
	def changeConcessionType(newConcession:Concession):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""

		if (newConcession != concession) {
			concession = newConcession
			successful = true
		}
		if (!successful) message = "error changing concession"
		return new ResponseObject(successful, message)
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
