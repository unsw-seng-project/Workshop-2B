package sengGroup.model.accounts

import sengGroup.model.network._;

class TravelRecord(
		val entryPoint:EntryPoint, val exitPoint:ExitPoint,
		val cost:Int, val date:Int) {
}