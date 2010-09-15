package accounts
import network._;

object Administrator {
	def penaliseUser (acc:Account):Boolean = {
		var successful : Boolean = false;
		if (constants.PENALTYFARE <= (acc.balance - constants.MINBALANCE)) {
			acc.balance -= constants.PENALTYFARE
		}
		return successful
	}
}