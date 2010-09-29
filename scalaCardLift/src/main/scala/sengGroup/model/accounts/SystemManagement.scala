package sengGroup.model.accounts

import net.liftweb.http.SessionVar;

import sengGroup.model.network._
import sengGroup.model.ResponseObject


object authSessionVar extends SessionVar[Int](0) {
   registerGlobalCleanupFunc(ignore => {
       println("Logging out")
       SystemManagement.getAccount(authSessionVar.is).get.isLoggedInSomewhere = false
   })

}
object isLoggedIn extends SessionVar[Boolean](false);

object SystemManagement {
	type Concession = String
	
	var deviceCounter:Int = 0
        var accountCounter:Int = 0
	
	var allAccessDevices : List[AccessDevice] = List()
	var currentAccessDevices : Map[AccessDevice, Account] = Map()
	var bannedAccessDevices : Set[AccessDevice] = Set()
	
	var accounts : Set[Account] = Set()
	
	val concessionRate : Map[String, Int] 
	                      = Map("Student"->5,
	                     		  "Adult"->10,
	                     		  "Child"->0)

        def newAccount:Account = {
          accountCounter += 1
          val accessDevice:AccessDevice = newAccessDevice

          val newAccount:Account = new Account(accessDevice, accountCounter)
          accounts += newAccount
          currentAccessDevices += accessDevice -> newAccount


          return newAccount

        }
	
	def newAccessDevice:AccessDevice = {
		deviceCounter += 1
		val newDevice:AccessDevice = new AccessDevice(deviceCounter)
		allAccessDevices = newDevice :: allAccessDevices
                println("new device created with id " + newDevice.deviceID)
                return newDevice
	}
	                     		  
	
	def upgrade (ad:AccessDevice, name:String, password:String, address:String) = {
          if (accountExists(ad) && !getAccount(ad).get.isPersonalAccount) {
            getAccount(ad).get.upgrade(name, password, address)
          }
        }

        def changeDetails (ad:AccessDevice, name:String, password:String, address:String) = {
          if (accountExists(ad) && getAccount(ad).get.isPersonalAccount) {
            if (password != getAccount(ad).get.password)
              getAccount(ad).get.changePersonalDetails(name, password,  address)
          }
        }

        def changePassword (ad:AccessDevice, oldPassword:String, newPassword:String) = {
          if (accountExists(ad) && getAccount(ad).get.isPersonalAccount) {
            if (oldPassword == getAccount(ad).get.password)
              getAccount(ad).get.changePassword(newPassword)
          }
        }


        def accountExists(accountID : Int) : Boolean = accounts.exists(x => x.accountId == accountID)

          def accountExists(ad : AccessDevice) : Boolean = accounts.exists(x => x.accessDevice == ad)


        def getAccount(accountID : Int) : Option[Account] = accounts.find(x => x.accountId == accountID)
        
        def getAccount(accessDevice : AccessDevice) : Option[Account] = accounts.find(x => x.accessDevice == accessDevice)


        def getAccessDevice(findDeviceID:Int): Option[AccessDevice] = allAccessDevices.find(x => x.deviceID == findDeviceID)

        def startTrip (accessDevice : AccessDevice, entryPoint:EntryPoint) : ResponseObject = {
          var success = false;
          var ro:ResponseObject = new ResponseObject(success, "");
          if (currentAccessDevices.isDefinedAt(accessDevice)) {
            if (getAccount(accessDevice).get.userEntryPoint != constants.NOENTRY) Administrator.penaliseUser(getAccount(accessDevice).get)

            ro = getAccount(accessDevice).get.startTrip(entryPoint)
            success = true
          } 
          else ro = new ResponseObject(success, "error starting trip")
          return ro
        }

       def endTrip (accessDevice : AccessDevice, exitPoint:ExitPoint) : ResponseObject = {
          var success = false;
          var ro:ResponseObject = new ResponseObject(success, "");
          if (currentAccessDevices.isDefinedAt(accessDevice)) {
            if (getAccount(accessDevice).get.userEntryPoint != constants.NOENTRY) {
              ro = getAccount(accessDevice).get.endTrip(exitPoint)
              success = true
            }
          }
          else ro = new ResponseObject(success, "error ending trip")
          return ro
        }

}