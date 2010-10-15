(function(A){A.fn.transition=function(C,B){return this.each(function(){var E=A(this);
var H={speed:"300ms",callback:null,ease:"ease-in-out"};
var G=A.extend({},H,B);
if(G.speed===0){E.css(C);
window.setTimeout(G.callback,0)
}else{if(A.browser.safari){var F=[];
for(var D in C){F.push(D)
}E.css({webkitTransitionProperty:F.join(", "),webkitTransitionDuration:G.speed,webkitTransitionTimingFunction:G.ease});
if(G.callback){E.one("webkitTransitionEnd",G.callback)
}setTimeout(function(I){I.css(C)
},0,E)
}else{E.animate(C,G.speed,G.callback)
}}})
}
})(jQuery);