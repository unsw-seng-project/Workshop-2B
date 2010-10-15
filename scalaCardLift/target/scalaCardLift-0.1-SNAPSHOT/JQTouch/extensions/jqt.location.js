(function(B){if(B.jQTouch){B.jQTouch.addExtension(function A(){var I,F,H;
function D(){return navigator.geolocation
}function G(J){if(D()){H=J;
navigator.geolocation.getCurrentPosition(C);
return true
}else{console.log("Device not capable of geo-location.");
J(false);
return false
}}function C(J){I=J.coords.latitude;
F=J.coords.longitude;
if(H){H(E())
}}function E(){if(I&&F){return{latitude:I,longitude:F}
}else{console.log("No location available. Try calling updateLocation() first.");
return false
}}return{updateLocation:G,getLocation:E}
})
}})(jQuery);