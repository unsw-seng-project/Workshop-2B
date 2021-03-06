package sengGroup.model.accounts

import sengGroup.model.network._;

object TravelRecordFactory {
	var dateCounter : Int = 0;
	def newRecord (route:(EntryPoint,ExitPoint), cost:Int):TravelRecord = {
		dateCounter += 1;
		return new TravelRecord(route._1, route._2, cost, dateCounter)
	}
}