// JavaScript Document

function checkIt(evt) {
    evt = (evt) ? evt : window.event
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        status = "This field accepts numbers only."
        return false
    }
    status = ""
    return true
}


function toggle(mainDiv, showHideDiv) {
	var ele = document.getElementById(showHideDiv)
	var divs = document.getElementById(mainDiv).childNodes
	
	document.getElementById("details").style.display="none"
	document.getElementById("TripHistory").style.display="none"
	document.getElementById("addToBalance").style.display="none"

	ele.style.display = "block";
}