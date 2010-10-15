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
		}
		return successful
	}
		
	def replaceAccessDevice (acc:Account, ad:AccessDevice):ResponseObject = {
		var successful : Boolean = false;
                var message = "";
                
                if (SystemManagement.getAccessDevice(acc.accountId).isDefined && acc.accessDevice == ad) {
                      val newAd:AccessDevice = SystemManagement.newAccessDevice

                      SystemManagement.currentAccessDevices -= ad
		      SystemManagement.bannedAccessDevices  += ad

                      SystemManagement.currentAccessDevices += newAd -> acc
                      acc.accessDevice = newAd
                      successful = true
                }

		if (!successful) message = "guards not satisfied"
		
            	println("replacement worked? " + successful)
		return new ResponseObject(successful, message)

	}

}