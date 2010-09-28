function checkIt(B){B=(B)?B:window.event;
var A=(B.which)?B.which:B.keyCode;
if(A>31&&(A<48||A>57)){status="This field accepts numbers only.";
return false
}status="";
return true
}function toggle(B,A){var D=document.getElementById(A);
var C=document.getElementById(B).childNodes;
document.getElementById("details").style.display="none";
document.getElementById("TripHistory").style.display="none";
document.getElementById("addToBalance").style.display="none";
D.style.display="block"
};