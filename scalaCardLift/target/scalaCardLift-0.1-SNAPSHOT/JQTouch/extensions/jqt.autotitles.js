(function(A){if(A.jQTouch){A.jQTouch.addExtension(function B(E){var D=".toolbar h1";
A(function(){A("body").bind("pageAnimationStart",function(I,G){if(G.direction==="in"){var F=A(D,A(I.target));
var H=A(I.target).data("referrer");
if(F.length&&H){F.html(H.text())
}}})
});
function C(F){D=F
}return{setTitleSelector:C}
})
}})(jQuery);