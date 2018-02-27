/*
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @fileoverview util and every page should be used.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 0.1.0.0, Jan 29, 2018
 */

/**
 * @description 皮肤脚本
 * @static
 */
var Skin = {
  init: function () {
    $('body').on('click', '.content-reset img', function () {
      window.open(this.src);
    });

    $(window).scroll(function () {
      if ($('#headerNav').length === 0) {
        return
      }
      if ($(window).scrollTop() > 64) {
        $('#headerNav').addClass('header__nav--fixed');
        $('.main').css('margin-top', '100px');
      } else {
        $('#headerNav').removeClass('header__nav--fixed');
        $('.main').css('margin-top', '50px');
      }
    });
    $(window).scroll();
  },
  initArticle: function () {
    Skin._share('#articleShare')
    Skin._share('#articleSideShare')
    Skin._share('#articleBottomShare')

    var $postSide = $('.post__side')
    if ($(window).height() >= $('.post').height()) {
      $postSide.css('opacity', 1)
    }
    $postSide.css('left', (($('.post').offset().left - 20) / 2 - 27) + 'px')

    var sideAbsoluteTop = ($(window).height() - 249) / 2 + 125
    var beforScrollTop = $(window).scrollTop()
    $(window).scroll(function () {
      var scrollTop = $(window).scrollTop()
      var bottomTop = $('.article__bottom').offset().top
      if (scrollTop > 65) {
        $postSide.css('opacity', 1)

        if (beforScrollTop - scrollTop > 0) {
          // up
          $('.header').addClass('header--fixed').css({'top': '0'})
          $('.main').css('padding-top', '64px')
          if ($(window).height() <= $('.post').height() && scrollTop < bottomTop - $(window).height()) {
            $('.article__toolbar').css({
              'bottom': 0,
              'opacity': 1
            })
          }
        } else if (beforScrollTop - scrollTop < 0) {
          // down
          $('.header').css({'top': '-64px'}).removeClass('header--fixed')
          $('.main').css('padding-top', '0')
          $('.article__toolbar').css({
            'bottom': '-44px',
            'opacity': 0
          })
        }

      } else {
        if ($(window).height() <= $('.post').height()) {
          $postSide.css('opacity', 0)
        }

        $('.header').removeClass('header--fixed').css('top', '-64px')
        $('.main').css('padding-top', '0')
      }

      if (scrollTop > bottomTop - $(window).height()) {
        if (bottomTop < $(window).height()) {
          $postSide.css({
            'position': 'absolute',
            'top': (bottomTop - 125) + 'px'
          })
        } else {
          $postSide.css({
            'position': 'absolute',
            'top': (bottomTop - sideAbsoluteTop) + 'px'
          })
        }
      } else {
        $postSide.css({
          'position': 'fixed',
          'top': '50%'
        })
      }

      beforScrollTop = scrollTop
    })

    $(window).scroll()
  },
  _share: function (id) {
    var $this = $(id)
    var $qrCode = $this.find('.article__code')
    var shareURL = $qrCode.data('url')
    var avatarURL = $qrCode.data('avatar')
    var title = encodeURIComponent($qrCode.data('title') + ' - ' + $qrCode.data('blogtitle')),
      url = encodeURIComponent(shareURL)

    var urls = {}
    urls.tencent = 'http://share.v.t.qq.com/index.php?c=share&a=index&title=' + title +
      '&url=' + url + '&pic=' + avatarURL
    urls.weibo = 'http://v.t.sina.com.cn/share/share.php?title=' +
      title + '&url=' + url + '&pic=' + avatarURL
    urls.google = 'https://plus.google.com/share?url=' + url
    urls.twitter = 'https://twitter.com/intent/tweet?status=' + title + ' ' + url

    $this.find('span').click(function () {
      var key = $(this).data('type')

      if (!key) {
        return
      }

      if (key === 'wechat') {
        if ($qrCode.find('canvas').length === 0) {
          $qrCode.qrcode({
            width: 128,
            height: 128,
            text: shareURL
          });
        } else {
          $qrCode.slideToggle();
        }
        return false;
      }

      window.open(urls[key], '_blank', 'top=100,left=200,width=648,height=618')
    })
  }
};
Skin.init();