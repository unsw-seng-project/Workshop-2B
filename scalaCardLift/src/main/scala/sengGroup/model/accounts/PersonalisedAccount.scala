package sengGroup.model.accounts
import sengGroup.model.network._

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
	def addToBalance (amount : Int):Boolean = {
		return acc.addToBalance(amount)
	}
	
	def subtractFromBalance (amount : Int):Boolean = {
		return acc.subtractFromBalance(amount)
	}
	
	def startTrip (entryPoint : EntryPoint):Boolean = {
		return startTrip(entryPoint : EntryPoint)
	}
	def endTrip (exitPoint : ExitPoint):Boolean = {
		return acc.endTrip(exitPoint : ExitPoint)
	}

	def changeAccountStatus(newStatus:AccountStatus.AccountStatus):Boolean = {
		return acc.changeAccountStatus(newStatus)
	}
	def changeConcessionType(newConcession:Concession):Boolean = {
		return acc.changeConcessionType(newConcession)
	}

}