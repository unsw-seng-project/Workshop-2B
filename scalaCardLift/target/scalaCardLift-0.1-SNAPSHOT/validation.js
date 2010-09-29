function checkIt(B){B=(B)?B:window.event;
var A=(B.which)?B.which:B.keyCode;
if(A>31&&(A<48||A>57)){status="This field accepts numbers only.";
return false
}status="";
return true
};