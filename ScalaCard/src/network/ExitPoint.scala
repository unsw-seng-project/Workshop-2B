package network

class ExitPoint (pointIDc:Int, namec:String) extends AccessPoint {
	val pointID: Int = pointIDc
	val name: String = namec
	
	def getPointID() : Int = pointID
	def getName() : String = name
}