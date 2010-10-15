(function(B){if(B.jQTouch){B.jQTouch.addExtension(function A(){var C=[];
C[0]="uncached";
C[1]="idle";
C[2]="checking";
C[3]="downloading";
C[4]="updateready";
C[5]="obsolete";
var D=window.applicationCache;
D.addEventListener("cached",F,false);
D.addEventListener("checking",F,false);
D.addEventListener("downloading",F,false);
D.addEventListener("error",F,false);
D.addEventListener("noupdate",F,false);
D.addEventListener("obsolete",F,false);
D.addEventListener("progress",F,false);
D.addEventListener("updateready",F,false);
function F(M){var J,I,K,L;
J=(H())?"yes":"no";
I=C[D.status];
K=M.type;
L="online: "+J;
L+=", event: "+K;
L+=", status: "+I;
if(K=="error"&&navigator.onLine){L+=" There was an unknown error, check your Cache Manifest."
}console.log(L)
}function H(){return navigator.onLine
}if(!B("html").attr("manifest")){console.log("No Cache Manifest listed on the <html> tag.")
}D.addEventListener("updateready",function(I){if(C[D.status]!="idle"){D.swapCache();
console.log("Swapped/updated the Cache Manifest.")
}},false);
function E(){D.update()
}function G(){setInterval(function(){D.update()
},10000)
}return{isOnline:H,checkForUpdates:E,autoCheckForUpdates:G}
})
}})(jQuery);