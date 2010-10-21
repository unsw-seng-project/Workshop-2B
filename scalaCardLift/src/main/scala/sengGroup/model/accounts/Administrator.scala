package sengGroup.model.accounts
import sengGroup.model.ResponseObject
import sengGroup.model.network._;


object Administrator {
	
	var adminPassword:String = ""
	
	def penaliseUser (acc:Account):Boolean = {
		var successful : Boolean = false;
		if (constants.PENALTYFARE <= (acc.balance - constants.MINBALANCE)) {
                        println("penalising user " + acc.accountId)
			acc.balance -= constants.PENALTYFARE
			successful = true
                        acc.userEntryPoint = constants.NOENTRY
		} else { //insufficient money to pay for penalty, balance set to zero
                        println("penalising user " + acc.accountId)
			acc.balance -= acc.balance
			successful = true
                        acc.userEntryPoint = constants.NOENTRY
                }
		return successful
	}
		
	def replaceAccessDevice (acc:Account, ad:AccessDevice):ResponseObject = {
		var successful : Boolean = false;
                var message = "";
                var ro : ResponseObject = new ResponseObject(false,"")

                if (!SystemManagement.getAccessDevice(acc.accountId).isDefined) return new ResponseObject(false, "Access device does not match the account")
                if (acc.accessDevice != ad) return new ResponseObject(false, "Account access device does not match given access device")
                else {
                      val newAd:AccessDevice = SystemManagement.newAccessDevice

                      SystemManagement.currentAccessDevices -= ad
		      SystemManagement.bannedAccessDevices  += ad

                      //successful = SystemManagement.activateDevice(newAd)
                      SystemManagement.currentAccessDevices += newAd -> acc
                      acc.accessDevice = newAd
                      acc.userEntryPoint = constants.NOENTRY
                      return new ResponseObject(true, "Amount added successfully")

                }


		
            	//println("replacement worked? " + successful)
		//return ro

	}

}