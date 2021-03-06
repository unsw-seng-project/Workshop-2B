package sengGroup.model.accounts

import net.liftweb.http.SessionVar;

import sengGroup.model.network._
import sengGroup.model.ResponseObject


object authSessionVar extends SessionVar[Int](0) {

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
	                      = Map("Adult"->10, "Student"->5, "Child"->0)

         //add some accounts
         newAccount
         newAccount
         changeConcessionType(getAccessDevice(1).get, "Student")
         startTrip (getAccessDevice(1).get, Network.entryPoints(0))
         endTrip (getAccessDevice(1).get, Network.exitPoints(1))
         startTrip (getAccessDevice(1).get, Network.entryPoints(2))
         endTrip (getAccessDevice(1).get, Network.exitPoints(4))

         upgrade(getAccessDevice(2).get, "Joe Blogs", "password", "UNSW")

         newAccount
         startTrip (getAccessDevice(3).get, Network.entryPoints(2))

         newAccount
         changeAccountStatus(getAccessDevice(4).get, AccountStatus.Disabled)

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

        def changeDetails (ad:AccessDevice, name:String, password:String, address:String) : ResponseObject = {
          var ro = new ResponseObject(false, "account error")
          if (accountExists(ad) && getAccount(ad).get.isPersonalAccount) {
            if (password == getAccount(ad).get.password) {
              getAccount(ad).get.changePersonalDetails(name, password,  address)
              ro = new ResponseObject(true, "details changed successfully")
            } else {
              ro = new ResponseObject(false, "password incorrect")
            }
          }
          return ro


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
          var ro:ResponseObject = new ResponseObject(success, "Access Device not defined");
          if (currentAccessDevices.contains(accessDevice)) {
            if (getAccount(accessDevice).get.userEntryPoint != constants.NOENTRY) {
              Administrator.penaliseUser(getAccount(accessDevice).get)
            }
            ro = getAccount(accessDevice).get.startTrip(entryPoint)
            success = true
          } 
          return ro
        }

       def endTrip (accessDevice : AccessDevice, exitPoint:ExitPoint) : ResponseObject = {
          var success = false;
          var ro:ResponseObject = new ResponseObject(success, "Access Device not defined");
          if (currentAccessDevices.contains(accessDevice)) {
              ro = getAccount(accessDevice).get.endTrip(exitPoint)
              success = true
          }
          return ro
        }

        def changeAccountStatus(accessDevice:AccessDevice, newStatus:AccountStatus.AccountStatus):ResponseObject = {
             var successful : Boolean = false;
             var message: String = ""

              if (!currentAccessDevices.contains(accessDevice)) message = "Access Device not defined"
              if (newStatus == currentAccessDevices(accessDevice).status) message = "error changing status"

              else {
                currentAccessDevices(accessDevice).status = newStatus
                successful = true
                message = "status updated"
              }

              return new ResponseObject(successful, message)
        }

	def changeConcessionType(accessDevice:AccessDevice, newConcession:Concession):ResponseObject = {
		var successful : Boolean = false;
                var message: String = ""

                if (!currentAccessDevices.contains(accessDevice)) message = "Access Device not defined"
		if (newConcession == currentAccessDevices(accessDevice).concession) message = "error changing concession"

                else {
                  currentAccessDevices(accessDevice).concession = newConcession
		  successful = true
                  message = "concession updated"
		}
		return new ResponseObject(successful, message)
        }

}