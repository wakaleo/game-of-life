/*
 * WPtouch 1.9.x -The WPtouch Core JS File
 */

var $wpt = jQuery.noConflict();

if ( ( navigator.platform == 'iPhone' || navigator.platform == 'iPod' ) && typeof orientation != 'undefined' ) { 
    var touchStartOrClick = 'touchstart'; 
} else {
    var touchStartOrClick = 'click'; 
};

/* Try to get out of frames! */
if ( window.top != window.self ) { 
    window.top.location = self.location.href
}

$wpt.fn.wptouchFadeToggle = function( speed, easing, callback ) { 
    return this.animate( {
        opacity: 'toggle'
    }, speed, easing, callback ); 
};

/**
     * @description Cookie 相关操作
     * @static
     */
var Cookie = {
    /**
         * @description 读取 cookie
         * @param {String} name cookie key
         * @returns {String} 对应 key 的值，如 key 不存在则返回 ""
         */
    readCookie: function (name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return decodeURIComponent(c.substring(nameEQ.length,c.length));
        }
        return "";
    },
        
    /**
         * @description 清除 Cookie
         * @param {String} name 清除 key 为 name 的该条 Cookie
         */
    eraseCookie: function (name) {
        this.createCookie(name,"",-1);
    },

    /**
         * @description 创建 Cookie
         * @param {String} name 每条 Cookie 唯一的 key
         * @param {String} value 每条 Cookie 对应的值，将被 UTF-8 编码
         * @param {Int} days Cookie 保存时间
         */
    createCookie: function (name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime()+(days*24*60*60*1000));
            expires = "; expires="+date.toGMTString();
        }
        document.cookie = name+"="+encodeURIComponent(value)+expires+"; path=/";
    }
};
function wptouch_switch_confirmation(skin, e ) {
    if ( document.cookie && document.cookie.indexOf( 'btouch_switch_toggle' ) > -1 ) {
        // just switch
        Cookie.createCookie("btouch_switch_toggle", skin, 365);
        $wpt( 'a#switch-link' ).toggleClass( 'offimg' );
        setTimeout('switch_delayer()', 1250 ); 
    } else {
        // ask first
        Cookie.createCookie("btouch_switch_toggle", skin, 365);
        if ( confirm( "Switch to regular view? \n \n You can switch back again in the top bar." ) ) {
            $wpt( 'a#switch-link' ).toggleClass( 'offimg' );
            setTimeout( 'switch_delayer()', 1350 ); 
        } else {
            e.preventDefault();
            e.stopImmediatePropagation();
        }
    }
}

if ( $wpt( '#prowl-success' ).length ) {
    setTimeout( function() {
        $wpt( '#prowl-success' ).fadeOut( 350 );
    }, 5250 );
}
if ( $wpt( '#prowl-fail' ).length ) {
    setTimeout( function() {
        $wpt( '#prowl-fail' ).fadeOut( 350 );
    }, 5250 );
}

$wpt(function() {
    var tabContainers = $wpt( '#menu-head > ul' );   
    $wpt( '#tabnav a' ).bind(touchStartOrClick, function () {
        tabContainers.hide().filter( this.hash ).show();
        $wpt( '#tabnav a' ).removeClass( 'selected' );
        $wpt( this ).addClass( 'selected' );
        return false;
    }).filter( ':first' ).trigger( touchStartOrClick );
});

function bnc_showhide_coms_toggle() {
    $wpt( '#commentlist' ).wptouchFadeToggle( 350 );
    $wpt( 'img#com-arrow' ).toggleClass( 'com-arrow-down' );
    $wpt( 'h3#com-head' ).toggleClass( 'comhead-open' );
}
	
function doWPtouchReady() {

    $wpt( '#headerbar-menu a' ).bind( touchStartOrClick, function( e ){
        $wpt( '#wptouch-menu' ).wptouchFadeToggle( 350 );
        $wpt( '#headerbar-menu a' ).toggleClass( 'open' );
    });

    $wpt( 'a#searchopen, #wptouch-search-inner a' ).bind( touchStartOrClick, function( e ){	
        $wpt( '#wptouch-search' ).wptouchFadeToggle( 350 );
    });
	
    $wpt( 'a#prowlopen' ).bind( touchStartOrClick, function( e ){	
        $wpt( '#prowl-message' ).wptouchFadeToggle( 350 );
    });
	
    $wpt( 'a#wordtwitopen' ).bind( touchStartOrClick, function( e ){	
        $wpt( '#wptouch-wordtwit' ).wptouchFadeToggle( 350 );
    });

    $wpt( 'a#gigpressopen' ).bind( touchStartOrClick, function( e ){	
        $wpt( '#wptouch-gigpress' ).wptouchFadeToggle( 350 );
    });

    $wpt( 'a#loginopen, #wptouch-login-inner a' ).bind( touchStartOrClick, function( e ){	
        $wpt( '#wptouch-login' ).wptouchFadeToggle(350);
    });
	
    $wpt( 'a#obook' ).bind( touchStartOrClick, function() {
        $wpt( '#bookmark-box' ).wptouchFadeToggle(350);
    });
	
    $wpt( '.singlentry img, .singlentry .wp-caption' ).each( function() {
        if ( $wpt( this ).width() <= 250 ) {
            $wpt( this ).addClass( 'aligncenter' );
        }
    });
	
    if ( $wpt( '#FollowMeTabLeftSm' ).length ) {
        $wpt( '#FollowMeTabLeftSm' ).remove();
    }
	
    $wpt( '.post' ).fitVids();

}

$wpt( document ).ready( function() {
    doWPtouchReady();
} );

/*! 
* FitVids 1.0
* Copyright 2011, Chris Coyier - http://css-tricks.com + Dave Rupert - http://daverupert.com
* Credit to Thierry Koblentz - http://www.alistapart.com/articles/creating-intrinsic-ratios-for-video/
* Released under the WTFPL license - http://sam.zoy.org/wtfpl/
* Date: Thu Sept 01 18:00:00 2011 -0500
*/
(function( $ ){
    $.fn.fitVids = function( options ) {
        var settings = {
            customSelector: null
        }
        var div = document.createElement('div'),
        ref = document.getElementsByTagName('base')[0] || document.getElementsByTagName('script')[0];
        div.className = 'fit-vids-style';
        div.innerHTML = '&shy;<style>         \
.fluid-width-video-wrapper {        \
width: 100%;                     \
position: relative;              \
padding: 0;                      \
}                                   \
\
.fluid-width-video-wrapper iframe,  \
.fluid-width-video-wrapper object,  \
.fluid-width-video-wrapper embed {  \
position: absolute;              \
top: 0;                          \
left: 0;                         \
width: 100%;                     \
height: 100%;                    \
}                                   \
</style>';
        ref.parentNode.insertBefore(div,ref);
        if ( options ) {
            $.extend( settings, options );
        }
        return this.each(function(){
            var selectors = [
            "iframe[src^='http://player.vimeo.com']",
            "iframe[src^='http://www.youtube.com']",
            "iframe[src^='http://www.kickstarter.com']",
            "object",
            "embed"
            ];
            if (settings.customSelector) {
                selectors.push(settings.customSelector);
            }
            var $allVideos = $(this).find(selectors.join(','));
            $allVideos.each(function(){
                var $this = $(this),
                height = this.tagName == 'OBJECT' ? $this.attr('height') : $this.height(),
                aspectRatio = height / $this.width();
                $this.wrap('<div class="fluid-width-video-wrapper" />').parent('.fluid-width-video-wrapper').css('padding-top', (aspectRatio * 100)+"%");
                $this.removeAttr('height').removeAttr('width');
            });
        });
    }
})( jQuery );