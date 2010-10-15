(function(B){if(B.jQTouch){B.jQTouch.addExtension(function A(C){B.fn.makeFloaty=function(D){var F={align:"top",spacing:20,time:".3s"};
var E=B.extend({},F,D);
E.align=(E.align=="top")?"top":"bottom";
return this.each(function(){var G=B(this);
G.css({"-webkit-transition":"top "+E.time+" ease-in-out",display:"block","min-height":"0 !important"}).data("settings",E);
B(document).bind("scroll",function(){if(G.data("floatyVisible")===true){G.scrollFloaty()
}});
G.scrollFloaty()
})
};
B.fn.scrollFloaty=function(){return this.each(function(){var D=B(this);
var F=D.data("settings");
var E=B("html").attr("clientHeight");
var G=window.pageYOffset+((F.align=="top")?F.spacing:E-F.spacing-D.get(0).offsetHeight);
D.css("top",G).data("floatyVisible",true)
})
};
B.fn.hideFloaty=function(){return this.each(function(){var D=B(this);
var E=D.get(0).offsetHeight;
D.css("top",-E-10).data("floatyVisible",false)
})
};
B.fn.toggleFloaty=function(){return this.each(function(){var D=B(this);
if(D.data("floatyVisible")===true){D.hideFloaty()
}else{D.scrollFloaty()
}})
}
})
}})(jQuery);