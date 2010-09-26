package sengGroup.model.accounts

object AccountStatus extends Enumeration {
  type AccountStatus = Value
  val Enabled = Value("Enabled")
  val Disabled = Value("Disabled") 
}
