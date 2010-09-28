package sengGroup.model.accounts
import sengGroup.model.network._
import sengGroup.model.ResponseObject

class PersonalisedAccount(val acc:Account, 
	var name:String, var password:String) extends AccountTrait {
		
	type Concession = String
	
	def changePassword (oldPassword:String, newPassword:String):Boolean = {
		var successful : Boolean = false
		if (oldPassword == password) {
			password = newPassword
			successful = true
		}
		return successful
	}
	
	def changePersonalDetails (newName:String, pass:String) = {
		var successful : Boolean = false
		if (pass == password 
				&& acc.status == AccountStatus.Enabled) {
			name = newName
		}
	}
	
	def concession = acc.concession
	def balance = acc.balance
	def status = acc.status 
	def travelHistory = acc.travelHistory
	def userEntryPOint = acc.userEntryPoint
	
	def setBalance (amount : Int) = acc.setBalance(amount)	
	def addToBalance (amount : Int):ResponseObject = {
		return acc.addToBalance(amount)
	}
	
	def subtractFromBalance (amount : Int):ResponseObject = {
		return acc.subtractFromBalance(amount)
	}
	
	def startTrip (entryPoint : EntryPoint):ResponseObject = {
		return startTrip(entryPoint : EntryPoint)
	}
	def endTrip (exitPoint : ExitPoint):ResponseObject = {
		return acc.endTrip(exitPoint : ExitPoint)
	}

	def changeAccountStatus(newStatus:AccountStatus.AccountStatus):ResponseObject = {
		return acc.changeAccountStatus(newStatus)
	}
	def changeConcessionType(newConcession:Concession):ResponseObject = {
		return acc.changeConcessionType(newConcession)
	}

}