package sengGroup.model.accounts
import sengGroup.model.network._;

object Administrator {
	
	var adminPassword:String = ""
	
	def penaliseUser (acc:Account):Boolean = {
		var successful : Boolean = false;
		if (constants.PENALTYFARE <= (acc.balance - constants.MINBALANCE)) {
			acc.balance -= constants.PENALTYFARE
			successful = true
		}
		return successful
	}
		
	def replaceAccessDevice (acc:Account, newAd:AccessDevice):Boolean = {
		var successful : Boolean = false;
		
		if (!System.currentAccessDevices.contains(newAd)
				&& !System.bannedAccessDevices.contains(newAd)
				&& newAd != acc.accessDevice) {
			System.banAccessDevice(acc.accessDevice)
			if (System.activateDevice(newAd)) {
				acc.accessDevice = newAd
				successful = true
			}
		}			
		return successful

	}
}