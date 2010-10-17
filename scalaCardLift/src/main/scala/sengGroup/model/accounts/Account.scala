package sengGroup.model.accounts
import sengGroup.model.network._;
import sengGroup.model.ResponseObject


class Account (var accessDevice : AccessDevice, val accountId : Int) {



        type Concession = String

        var isLoggedInSomewhere :Boolean = false;

	var concession : Concession = "Adult"
	//def getConcession = concession
		
	var balance : Int = 30
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

    		var successful : Boolean = false;
                var message: String = ""
                var ro : ResponseObject = new ResponseObject(false,"")


                if (!SystemManagement.currentAccessDevices.contains(accessDevice)) return new ResponseObject(false, "Access device is not part of the system")
                if (balance + amount > constants.MAXBALANCE) return new ResponseObject(false, "Sum of amount and balance exceeds maximum value for balance")
                if (status != AccountStatus.Enabled) return new ResponseObject(false, "Account is disabled. Function does not work in this status.")
                else {


			balance += amount
			return new ResponseObject(true, "Amount added successfully")

		}

		//return ro

	}
	
	def subtractFromBalance (amount : Int):ResponseObject = {
		var successful : Boolean = false
                var message: String = ""

                var ro : ResponseObject = new ResponseObject(false,"")


                if (!SystemManagement.currentAccessDevices.contains(accessDevice)) return new ResponseObject(false, "Access device is not part of the system")
                if (balance - amount < constants.MINBALANCE) return new ResponseObject(false, "Balance minimum will be exceeded if the amount is subtracted")
                if (status != AccountStatus.Enabled) return new ResponseObject(false, "Account is disabled. Function does not work in this status.")
                else {


			balance -= amount
			return new ResponseObject(true, "Amount subtracted successfully")

		}

		//return ro
	}
	
	def startTrip (entryPoint : EntryPoint):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""
                var ro : ResponseObject = new ResponseObject(false,"")

		if (!Network.routeStartsWith(entryPoint)) return new ResponseObject(false, "Not a valid entry location")
		if (userEntryPoint != constants.NOENTRY) return new ResponseObject(false, "User already travelling")
		if (balance < constants.MINBALANCE + constants.PENALTYFARE) return new ResponseObject(false, "Not enough money")
		if (status != AccountStatus.Enabled) return new ResponseObject(false, "Account is disabled")

                else {
			userEntryPoint = entryPoint
			return new ResponseObject(true, "Trip started successfully")
		}
		//return ro
		
	}
	def endTrip (exitPoint : ExitPoint):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""
		val route = userEntryPoint -> exitPoint
                var ro : ResponseObject = new ResponseObject(false,"")


                if (userEntryPoint == constants.NOENTRY) return new ResponseObject(false, "User is not yet travelling")
		if (!Network.routes.contains(route)) return new ResponseObject(false, "Exit not connected to your entry point")
		if (usersFare(route) > (balance - constants.MINBALANCE)) return new ResponseObject(false, "Not enough money to finish trip")
		if (status != AccountStatus.Enabled) return new ResponseObject(false, "Account is disabled")

                else {
			
			travelHistory =  TravelRecordFactory.newRecord(route, usersFare(route)) :: travelHistory
			balance -= usersFare(route)
			userEntryPoint = constants.NOENTRY

			return new ResponseObject(true, "Trip completed successfully")
		}
		//return ro
                //multiple return statements used because setting ro = new ResponseObject(boolean,message) for some reason doesnt work...

	}
	
	private def usersFare(route:(EntryPoint,ExitPoint)): Int = {
           var fare:int = 0
           if (!Network.routes.contains(route)) fare = 20 //route does not exist, penalty fare
           else {
             fare = (Network.fare(route) + SystemManagement.concessionRate(concession))
           }
		return fare
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

                if (!pass.equals(password))  return false //password does not match
                if (status != AccountStatus.Enabled) return false //account is disabled
                else {

			name = newName
                        address = newAddress
                        successful = true
		}
                return successful
	}

        def upgrade (newName:String, pass:String, newAddress:String):Boolean = {
		var successful : Boolean = false
                if (status != AccountStatus.Enabled) return false //account is disabled
                if (isPersonalAccount) return false //account is already a personal account
		else {
			name = newName
                        address = newAddress
                        password = pass
                        successful = true
                        isPersonalAccount = true
		}
                return successful

	}
}
