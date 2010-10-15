(function(A){A.jQTouch=function(E){A.support.WebKitCSSMatrix=(typeof WebKitCSSMatrix=="object");
A.support.touch=(typeof Touch=="object");
A.support.WebKitAnimationEvent=(typeof WebKitTransitionEvent=="object");
var T,P=A("head"),J=[],I=0,L={},b,V,D,B=RegExp(" Mobile/").test(navigator.userAgent),F=true,U=0,d=[],R={},c=A.jQTouch.prototype.extensions,e=["slide","flip","slideup","swap","cube","pop","dissolve","fade","back"],N=[],H="";
W(E);
function W(j){var m={addGlossToIcon:true,backSelector:".back, .cancel, .goback",cacheGetRequests:true,cubeSelector:".cube",dissolveSelector:".dissolve",fadeSelector:".fade",fixedViewport:true,flipSelector:".flip",formSelector:"form",fullScreen:true,fullScreenClass:"fullscreen",icon:null,touchSelector:"a, .touch",popSelector:".pop",preloadImages:false,slideSelector:"body > * > ul li a",slideupSelector:".slideup",startupScreen:null,statusBar:"default",submitSelector:".submit",swapSelector:".swap",useAnimations:true,useFastTouch:true};
L=A.extend({},m,j);
if(L.preloadImages){for(var k=L.preloadImages.length-1;
k>=0;
k--){(new Image()).src=L.preloadImages[k]
}}if(L.icon){var l=(L.addGlossToIcon)?"":"-precomposed";
H+='<link rel="apple-touch-icon'+l+'" href="'+L.icon+'" />'
}if(L.startupScreen){H+='<link rel="apple-touch-startup-image" href="'+L.startupScreen+'" />'
}if(L.fixedViewport){H+='<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"/>'
}if(L.fullScreen){H+='<meta name="apple-mobile-web-app-capable" content="yes" />';
if(L.statusBar){H+='<meta name="apple-mobile-web-app-status-bar-style" content="'+L.statusBar+'" />'
}}if(H){P.append(H)
}A(document).ready(function(){for(var p in c){var q=c[p];
if(A.isFunction(q)){A.extend(R,q(R))
}}for(var p in e){var o=e[p];
var n=L[o+"Selector"];
if(typeof (n)=="string"){Y({name:o,selector:n})
}}d.push("input");
d.push(L.touchSelector);
d.push(L.backSelector);
d.push(L.submitSelector);
A(d.join(", ")).css("-webkit-touch-callout","none");
A(L.backSelector).tap(M);
A(L.submitSelector).tap(Q);
T=A("body");
if(L.fullScreenClass&&window.navigator.standalone==true){T.addClass(L.fullScreenClass+" "+L.statusBar)
}T.bind("touchstart",g).bind("orientationchange",Z).trigger("orientationchange").submit(O);
if(L.useFastTouch&&A.support.touch){T.click(function(r){var i=A(r.target);
if(i.attr("target")=="_blank"||i.attr("rel")=="external"||i.is('input[type="checkbox"]')){return true
}else{return false
}});
T.mousedown(function(i){var r=(new Date()).getTime()-U;
if(r<200){return false
}})
}if(A("body > .current").length==0){V=A("body > *:first")
}else{V=A("body > .current:first");
A("body > .current").removeClass("current")
}A(V).addClass("current");
location.hash=A(V).attr("id");
S(V);
scrollTo(0,0);
a()
})
}function h(p){if(J.length>1){var o=Math.min(parseInt(p||1,10),J.length-1);
if(isNaN(o)&&typeof (p)==="string"&&p!="#"){for(var j=1,l=J.length;
j<l;
j++){if("#"+J[j].id===p){o=j;
break
}}}if(isNaN(o)||o<1){o=1
}var m=J[0].animation;
var n=J[0].page;
J.splice(0,o);
var k=J[0].page;
G(n,k,m,true);
return R
}else{console.error("No pages in history.");
return false
}}function f(k,l){var m=J[0].page;
if(typeof (k)==="string"){k=A(k)
}if(typeof (l)==="string"){for(var j=N.length-1;
j>=0;
j--){if(N[j].name===l){l=N[j];
break
}}}if(G(m,k,l)){S(k,l);
return R
}else{console.error("Could not animate pages.");
return false
}}function K(){return D
}function M(o){var k=A(o.target);
if(k.attr("nodeName")!=="A"){k=k.parent("a")
}var n=k.attr("target"),m=k.attr("hash"),l=null;
if(F==false||!k.length){console.warn("Not able to tap element.");
return false
}if(k.attr("target")=="_blank"||k.attr("rel")=="external"){return true
}for(var j=N.length-1;
j>=0;
j--){if(k.is(N[j].selector)){l=N[j];
break
}}if(n=="_webapp"){window.location=k.attr("href")
}else{if(k.is(L.backSelector)){h(m)
}else{if(m&&m!="#"){k.addClass("active");
f(A(m).data("referrer",k),l)
}else{k.addClass("loading active");
X(k.attr("href"),{animation:l,callback:function(){k.removeClass("loading");
setTimeout(A.fn.unselect,250,k)
},$referrer:k})
}}}return false
}function S(k,j){var i=k.attr("id");
J.unshift({page:k,animation:j,id:i})
}function G(l,j,k,i){if(j.length===0){A.fn.unselect();
console.error("Target element is missing.");
return false
}A(":focus").blur();
scrollTo(0,0);
var m=function(o){if(k){j.removeClass("in reverse "+k.name);
l.removeClass("current out reverse "+k.name)
}else{l.removeClass("current")
}j.trigger("pageAnimationEnd",{direction:"in"});
l.trigger("pageAnimationEnd",{direction:"out"});
clearInterval(dumbLoop);
V=j;
location.hash=V.attr("id");
a();
var n=j.data("referrer");
if(n){n.unselect()
}U=(new Date()).getTime();
F=true
};
l.trigger("pageAnimationStart",{direction:"out"});
j.trigger("pageAnimationStart",{direction:"in"});
if(A.support.WebKitAnimationEvent&&k&&L.useAnimations){j.one("webkitAnimationEnd",m);
F=false;
j.addClass(k.name+" in current "+(i?" reverse":""));
l.addClass(k.name+" out"+(i?" reverse":""))
}else{j.addClass("current");
m()
}return true
}function a(){dumbLoop=setInterval(function(){var i=V.attr("id");
if(location.hash==""){location.hash="#"+i
}else{if(location.hash!="#"+i){try{h(location.hash)
}catch(j){console.error("Unknown hash change.")
}}}},100)
}function C(i,j){var k=null;
A(i).each(function(m,n){var l=A(this);
if(!l.attr("id")){l.attr("id","page-"+(++I))
}l.appendTo(T);
if(l.hasClass("current")||!k){k=l
}});
if(k!==null){f(k,j);
return k
}else{return false
}}function X(i,j){var l={data:null,method:"GET",animation:null,callback:null,$referrer:null};
var k=A.extend({},l,j);
if(i!="#"){A.ajax({url:i,data:k.data,type:k.method,success:function(m,o){var n=C(m,k.animation);
if(n){if(k.method=="GET"&&L.cacheGetRequests&&k.$referrer){k.$referrer.attr("href","#"+n.attr("id"))
}if(k.callback){k.callback(true)
}}},error:function(m){if(k.$referrer){k.$referrer.unselect()
}if(k.callback){k.callback(false)
}}})
}else{if($referrer){$referrer.unselect()
}}}function O(j,k){var i=(typeof (j)==="string")?A(j):A(j.target);
if(i.length&&i.is(L.formSelector)&&i.attr("action")){X(i.attr("action"),{data:i.serialize(),method:i.attr("method")||"POST",animation:N[0]||null,callback:k});
return false
}return true
}function Q(j){var i=A(this).closest("form");
if(i.length){evt=jQuery.Event("submit");
evt.preventDefault();
i.trigger(evt);
return false
}return true
}function Y(i){if(typeof (i.selector)=="string"&&typeof (i.name)=="string"){N.push(i);
A(i.selector).tap(M);
d.push(i.selector)
}}function Z(){D=window.innerWidth<window.innerHeight?"profile":"landscape";
T.removeClass("profile landscape").addClass(D).trigger("turn",{orientation:D})
}function g(q){var u=A(q.target);
if(!A(q.target).is(d.join(", "))){var r=A(q.target).closest("a");
if(r.length){u=r
}else{return 
}}if(event){var i=null,o=event.changedTouches[0].clientX,n=event.changedTouches[0].clientY,j=(new Date).getTime(),m=0,k=0,p=0;
u.bind("touchmove",l).bind("touchend",s);
i=setTimeout(function(){u.makeActive()
},100)
}function l(x){t();
var w=Math.abs(m);
var v=Math.abs(k);
if(w>v&&(w>35)&&p<1000){u.trigger("swipe",{direction:(m<0)?"left":"right"}).unbind("touchmove touchend")
}else{if(v>1){u.removeClass("active")
}}clearTimeout(i)
}function s(){t();
if(k===0&&m===0){u.makeActive();
u.trigger("tap")
}else{u.removeClass("active")
}u.unbind("touchmove touchend");
clearTimeout(i)
}function t(){var v=event.changedTouches[0]||null;
m=v.pageX-o;
k=v.pageY-n;
p=(new Date).getTime()-j
}}A.fn.unselect=function(i){if(i){i.removeClass("active")
}else{A(".active").removeClass("active")
}};
A.fn.makeActive=function(){return A(this).addClass("active")
};
A.fn.swipe=function(i){if(A.isFunction(i)){return this.each(function(j,k){A(k).bind("swipe",i)
})
}};
A.fn.tap=function(j){if(A.isFunction(j)){var i=(L.useFastTouch&&A.support.touch)?"tap":"click";
return A(this).live(i,j)
}else{A(this).trigger("tap")
}};
R={getOrientation:K,goBack:h,goTo:f,addAnimation:Y,submitForm:O};
return R
};
A.jQTouch.prototype.extensions=[];
A.jQTouch.addExtension=function(B){A.jQTouch.prototype.extensions.push(B)
}
})(jQuery);