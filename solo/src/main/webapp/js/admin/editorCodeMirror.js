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
 * @fileoverview markdowm CodeMirror editor
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.4.2.4, Apr 25, 2017
 */

Util.processClipBoard = function (text, cm) {
    var text = toMarkdown(text, {
        converters: [], gfm: true
    });

    // ascii 160 替换为 30
    text = $('<div>' + text + '</div>').text().replace(/\n{2,}/g, '\n\n').replace(/ /g, ' ');
    return $.trim(text);
};
/**
 * @description 获取字符串开始位置
 * @param {String} string 需要匹配的字符串
 * @param {String} prefix 匹配字符
 * @return {Integer} 以匹配字符开头的位置
 */
Util.startsWith = function (string, prefix) {
    return (string.match("^" + prefix) == prefix);
};
/**
 * 粘贴中包含图片和文案时，需要处理为 markdown 语法
 * @param {object} clipboardData
 * @param {object} cm
 * @returns {String}
 */
Util.processClipBoard = function (clipboardData, cm) {
    if (clipboardData.getData("text/html") === '' && clipboardData.items.length === 2) {
        return '';
    }

    var text = toMarkdown(clipboardData.getData("text/html"), {
        converters: [
            {
                filter: 'img',
                replacement: function (innerHTML, node) {
                    if (1 === node.attributes.length) {
                        return "";
                    }
                    return "![](" + node.src + ")";
                }
            }
        ], gfm: true
    });

    // code 中 <, > 进行转义
    var codes = text.split('```');
    if (codes.length > 1) {
        for (var i = 0, iMax = codes.length; i < iMax; i++) {
            if (i % 2 === 1) {
                codes[i] = codes[i].replace(/<\/span><span style="color:#\w{6};">/g, '').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            }
        }
    }
    text = codes.join('```');

    // ascii 160 替换为 30
    text = $('<div>' + text + '</div>').text().replace(/\n{2,}/g, '\n\n').replace(/ /g, ' ');
    return $.trim(text);
};

Util.initUploadFile = function (obj) {
    var isImg = false;
    $('#' + obj.id).fileupload({
        multipart: true,
        pasteZone: obj.pasteZone,
        dropZone: obj.pasteZone,
        url: "https://up.qbox.me/",
        paramName: "file",
        add: function (e, data) {
            if (data.files[0].name) {
                var processName = data.files[0].name.match(/[a-zA-Z0-9.]/g).join('');
                filename = getUUID() + '-' + processName;

                // 文件名称全为中文时，移除 ‘-’
                if (processName.split('.')[0] === '') {
                    filename = getUUID() + processName;
                }
            } else {
                filename = getUUID() + '.' + data.files[0].type.split("/")[1];
            }


            if (window.File && window.FileReader && window.FileList && window.Blob) {
                var reader = new FileReader();
                reader.readAsArrayBuffer(data.files[0]);
                reader.onload = function (evt) {
                    var fileBuf = new Uint8Array(evt.target.result.slice(0, 11));
                    isImg = data.files[0].type.indexOf('image') === 0 ? true : false;

                    data.submit();
                }
            } else {
                data.submit();
            }
        },
        formData: function (form) {
            var data = form.serializeArray();

            data.push({
                name: 'key', value: "file/" + (new Date()).getFullYear() + "/"
                + ((new Date()).getMonth() + 1) + '/' + filename
            });

            data.push({name: 'token', value: obj.qiniuUploadToken});

            return data;
        },
        submit: function (e, data) {
            if (obj.editor.replaceRange) {
                var cursor = obj.editor.getCursor();
                obj.editor.replaceRange(obj.uploadingLabel, cursor, cursor);
            } else {
                $('#' + obj.id + ' input').prop('disabled', false);
            }
        },
        done: function (e, data) {
            var qiniuKey = data.result.key;
            if (!qiniuKey) {
                alert("Upload error");

                return;
            }

            if (obj.editor.replaceRange) {
                var cursor = obj.editor.getCursor();

                if (isImg) {
                    obj.editor.replaceRange('![' + filename + '](' + obj.qiniuDomain + '/' + qiniuKey + ') \n\n',
                        CodeMirror.Pos(cursor.line, cursor.ch - obj.uploadingLabel.length), cursor);
                } else {
                    obj.editor.replaceRange('[' + filename + '](' + obj.qiniuDomain + '/' + qiniuKey + ') \n\n',
                        CodeMirror.Pos(cursor.line, cursor.ch - obj.uploadingLabel.length), cursor);
                }
            } else {
                obj.editor.$it.val('![' + filename + '](' + obj.qiniuDomain + '/' + qiniuKey + ') \n\n');
                $('#' + obj.id + ' input').prop('disabled', false);
            }
        },
        fail: function (e, data) {
            alert("Upload error: " + data.errorThrown);
            if (obj.editor.replaceRange) {
                var cursor = obj.editor.getCursor();
                obj.editor.replaceRange('',
                    CodeMirror.Pos(cursor.line, cursor.ch - obj.uploadingLabel.length), cursor);
            } else {
                $('#' + obj.id + ' input').prop('disabled', false);
            }
        }
    }).on('fileuploadprocessalways', function (e, data) {
        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
            alert(currentFile.error);
        }
    });
}

admin.editors.CodeMirror = {
    /*
     * @description 初始化编辑器
     * @param conf 编辑器初始化参数
     * @param conf.kind 编辑器类型
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     * @param conf.height 编辑器高度
     * @param conf.codeMirrorLanguage codeMirror 编辑器当前解析语言
     * @returns {obj} editor
     */
    init: function (conf) {
        var emojis = "+1,-1,100,1234,8ball,a,ab,abc,abcd,accept,aerial_tramway,airplane,alarm_clock,alien,ambulance,anchor,angel,anger,angry,anguished,ant,apple,aquarius,aries,arrow_backward,arrow_double_down,arrow_double_up,arrow_down,arrow_down_small,arrow_forward,arrow_heading_down,arrow_heading_up,arrow_left,arrow_lower_left,arrow_lower_right,arrow_right,arrow_right_hook,arrow_up,arrow_up_down,arrow_up_small,arrow_upper_left,arrow_upper_right,arrows_clockwise,arrows_counterclockwise,art,articulated_lorry,astonished,atm,b,baby,baby_bottle,baby_chick,baby_symbol,back,baggage_claim,balloon,ballot_box_with_check,bamboo,banana,bangbang,bank,bar_chart,barber,baseball,basketball,bath,bathtub,battery,bear,bee,beer,beers,beetle,beginner,bell,bento,bicyclist,bike,bikini,bird,birthday,black_circle,black_joker,black_medium_small_square,black_medium_square,black_nib,black_small_square,black_square,black_square_button,blossom,blowfish,blue_book,blue_car,blue_heart,blush,boar,boat,bomb,book,bookmark,bookmark_tabs,books,boom,boot,bouquet,bow,bowling,bowtie,boy,bread,bride_with_veil,bridge_at_night,briefcase,broken_heart,bug,bulb,bullettrain_front,bullettrain_side,bus,busstop,bust_in_silhouette,busts_in_silhouette,cactus,cake,calendar,calling,camel,camera,cancer,candy,capital_abcd,capricorn,car,card_index,carousel_horse,cat,cat2,cd,chart,chart_with_downwards_trend,chart_with_upwards_trend,checkered_flag,cherries,cherry_blossom,chestnut,chicken,children_crossing,chocolate_bar,christmas_tree,church,cinema,circus_tent,city_sunrise,city_sunset,cl,clap,clapper,clipboard,clock1,clock10,clock1030,clock11,clock1130,clock12,clock1230,clock130,clock2,clock230,clock3,clock330,clock4,clock430,clock5,clock530,clock6,clock630,clock7,clock730,clock8,clock830,clock9,clock930,closed_book,closed_lock_with_key,closed_umbrella,cloud,clubs,cn,cocktail,coffee,cold_sweat,collision,computer,confetti_ball,confounded,confused,congratulations,construction,construction_worker,convenience_store,cookie,cool,cop,copyright,corn,couple,couple_with_heart,couplekiss,cow,cow2,credit_card,crescent_moon,crocodile,crossed_flags,crown,cry,crying_cat_face,crystal_ball,cupid,curly_loop,currency_exchange,curry,custard,customs,cyclone,dancer,dancers,dango,dart,dash,date,de,deciduous_tree,department_store,diamond_shape_with_a_dot_inside,diamonds,disappointed,disappointed_relieved,dizzy,dizzy_face,do_not_litter,dog,dog2,dollar,dolls,dolphin,donut,door,doughnut,dragon,dragon_face,dress,dromedary_camel,droplet,dvd,e-mail,ear,ear_of_rice,earth_africa,earth_americas,earth_asia,egg,eggplant,eight,eight_pointed_black_star,eight_spoked_asterisk,electric_plug,elephant,email,end,envelope,es,euro,european_castle,european_post_office,evergreen_tree,exclamation,expressionless,eyeglasses,eyes,facepunch,factory,fallen_leaf,family,fast_forward,fax,fearful,feelsgood,feet,ferris_wheel,file_folder,finnadie,fire,fire_engine,fireworks,first_quarter_moon,first_quarter_moon_with_face,fish,fish_cake,fishing_pole_and_fish,fist,five,flags,flashlight,floppy_disk,flower_playing_cards,flushed,foggy,football,fork_and_knife,fountain,four,four_leaf_clover,fr,free,fried_shrimp,fries,frog,frowning,fu,fuelpump,full_moon,full_moon_with_face,game_die,gb,gem,gemini,ghost,gift,gift_heart,girl,globe_with_meridians,goat,goberserk,godmode,golf,grapes,green_apple,green_book,green_heart,grey_exclamation,grey_question,grimacing,grin,grinning,guardsman,guitar,gun,haircut,hamburger,hammer,hamster,hand,handbag,hankey,hash,hatched_chick,hatching_chick,headphones,hear_no_evil,heart,heart_decoration,heart_eyes,heart_eyes_cat,heartbeat,heartpulse,hearts,heavy_check_mark,heavy_division_sign,heavy_dollar_sign,heavy_exclamation_mark,heavy_minus_sign,heavy_multiplication_x,heavy_plus_sign,helicopter,herb,hibiscus,high_brightness,high_heel,hocho,honey_pot,honeybee,horse,horse_racing,hospital,hotel,hotsprings,hourglass,hourglass_flowing_sand,house,house_with_garden,hurtrealbad,hushed,ice_cream,icecream,id,ideograph_advantage,imp,inbox_tray,incoming_envelope,information_desk_person,information_source,innocent,interrobang,iphone,it,izakaya_lantern,jack_o_lantern,japan,japanese_castle,japanese_goblin,japanese_ogre,jeans,joy,joy_cat,jp,key,keycap_ten,kimono,kiss,kissing,kissing_cat,kissing_closed_eyes,kissing_face,kissing_heart,kissing_smiling_eyes,koala,koko,kr,large_blue_circle,large_blue_diamond,large_orange_diamond,last_quarter_moon,last_quarter_moon_with_face,laughing,leaves,ledger,left_luggage,left_right_arrow,leftwards_arrow_with_hook,lemon,leo,leopard,libra,light_rail,link,lips,lipstick,lock,lock_with_ink_pen,lollipop,loop,loudspeaker,love_hotel,love_letter,low_brightness,m,mag,mag_right,mahjong,mailbox,mailbox_closed,mailbox_with_mail,mailbox_with_no_mail,man,man_with_gua_pi_mao,man_with_turban,mans_shoe,maple_leaf,mask,massage,meat_on_bone,mega,melon,memo,mens,metal,metro,microphone,microscope,milky_way,minibus,minidisc,mobile_phone_off,money_with_wings,moneybag,monkey,monkey_face,monorail,mortar_board,mount_fuji,mountain_bicyclist,mountain_cableway,mountain_railway,mouse,mouse2,movie_camera,moyai,muscle,mushroom,musical_keyboard,musical_note,musical_score,mute,nail_care,name_badge,neckbeard,necktie,negative_squared_cross_mark,neutral_face,new,new_moon,new_moon_with_face,newspaper,ng,nine,no_bell,no_bicycles,no_entry,no_entry_sign,no_good,no_mobile_phones,no_mouth,no_pedestrians,no_smoking,non-potable_water,nose,notebook,notebook_with_decorative_cover,notes,nut_and_bolt,o,o2,ocean,octocat,octopus,oden,office,ok,ok_hand,ok_woman,older_man,older_woman,on,oncoming_automobile,oncoming_bus,oncoming_police_car,oncoming_taxi,one,open_file_folder,open_hands,open_mouth,ophiuchus,orange_book,outbox_tray,ox,package,page_facing_up,page_with_curl,pager,palm_tree,panda_face,paperclip,parking,part_alternation_mark,partly_sunny,passport_control,paw_prints,peach,pear,pencil,pencil2,penguin,pensive,performing_arts,persevere,person_frowning,person_with_blond_hair,person_with_pouting_face,phone,pig,pig2,pig_nose,pill,pineapple,pisces,pizza,plus1,point_down,point_left,point_right,point_up,point_up_2,police_car,poodle,poop,post_office,postal_horn,postbox,potable_water,pouch,poultry_leg,pound,pouting_cat,pray,princess,punch,purple_heart,purse,pushpin,put_litter_in_its_place,question,rabbit,rabbit2,racehorse,radio,radio_button,rage,rage1,rage2,rage3,rage4,railway_car,rainbow,raised_hand,raised_hands,raising_hand,ram,ramen,rat,recycle,red_car,red_circle,registered,relaxed,relieved,repeat,repeat_one,restroom,revolving_hearts,rewind,ribbon,rice,rice_ball,rice_cracker,rice_scene,ring,rocket,roller_coaster,rooster,rose,rotating_light,round_pushpin,rowboat,ru,rugby_football,runner,running,running_shirt_with_sash,sa,sagittarius,sailboat,sake,sandal,santa,satellite,satisfied,saxophone,school,school_satchel,scissors,scorpius,scream,scream_cat,scroll,seat,secret,see_no_evil,seedling,seven,shaved_ice,sheep,shell,ship,shipit,shirt,shit,shoe,shower,signal_strength,six,six_pointed_star,ski,skull,sleeping,sleepy,slot_machine,small_blue_diamond,small_orange_diamond,small_red_triangle,small_red_triangle_down,smile,smile_cat,smiley,smiley_cat,smiling_imp,smirk,smirk_cat,smoking,snail,snake,snowboarder,snowflake,snowman,sob,soccer,soon,sos,sound,space_invader,spades,spaghetti,sparkle,sparkler,sparkles,sparkling_heart,speak_no_evil,speaker,speech_balloon,speedboat,squirrel,star,star2,stars,station,statue_of_liberty,steam_locomotive,stew,straight_ruler,strawberry,stuck_out_tongue,stuck_out_tongue_closed_eyes,stuck_out_tongue_winking_eye,sun_with_face,sunflower,sunglasses,sunny,sunrise,sunrise_over_mountains,surfer,sushi,suspect,suspension_railway,sweat,sweat_drops,sweat_smile,sweet_potato,swimmer,symbols,syringe,tada,tanabata_tree,tangerine,taurus,taxi,tea,telephone,telephone_receiver,telescope,tennis,tent,thought_balloon,three,thumbsdown,thumbsup,ticket,tiger,tiger2,tired_face,tm,toilet,tokyo_tower,tomato,tongue,top,tophat,tractor,traffic_light,train,train2,tram,triangular_flag_on_post,triangular_ruler,trident,triumph,trolleybus,trollface,trophy,tropical_drink,tropical_fish,truck,trumpet,tshirt,tulip,turtle,tv,twisted_rightwards_arrows,two,two_hearts,two_men_holding_hands,two_women_holding_hands,u5272,u5408,u55b6,u6307,u6708,u6709,u6e80,u7121,u7533,u7981,u7a7a,uk,umbrella,unamused,underage,unlock,up,us,v,vertical_traffic_light,vhs,vibration_mode,video_camera,video_game,violin,virgo,volcano,vs,walking,waning_crescent_moon,waning_gibbous_moon,warning,watch,water_buffalo,watermelon,wave,wavy_dash,waxing_crescent_moon,waxing_gibbous_moon,wc,weary,wedding,whale,whale2,wheelchair,white_check_mark,white_circle,white_flower,white_large_square,white_medium_small_square,white_medium_square,white_small_square,white_square_button,wind_chime,wine_glass,wink,wolf,woman,womans_clothes,womans_hat,womens,worried,wrench,x,yellow_heart,yen,yum,zap,zero,zzz".split(",");

        CodeMirror.registerHelper("hint", "emoji", function (cm) {
            var word = /[\w$]+/;
            var cur = cm.getCursor(), curLine = cm.getLine(cur.line);
            var start = cur.ch, end = start;
            while (end < curLine.length && word.test(curLine.charAt(end))) {
                ++end;
            }
            while (start && word.test(curLine.charAt(start - 1))) {
                --start;
            }
            var tok = cm.getTokenAt(cur);
            var autocompleteHints = [];
            var input = tok.string.trim();
            var matchCnt = 0;
            for (var i = 0; i < emojis.length; i++) {
                var displayText = emojis[i];
                var text = emojis[i];
                if (Util.startsWith(text, input)) {
                    autocompleteHints.push({
                        displayText: '<span style="font-size: 1rem;line-height:22px"><img style="width: 1rem;margin:3px 0;float:left" src="'
                        + latkeConfig.servePath + '/js/lib/emojify.js-1.1.0/images/basic/' + text + '.png"> ' +
                        displayText.toString() + '</span>',
                        text: ":" + text + ": "
                    });
                    matchCnt++;
                }

                if (matchCnt > 10) {
                    break;
                }
            }

            return {list: autocompleteHints, from: CodeMirror.Pos(cur.line, start), to: CodeMirror.Pos(cur.line, end)};
        });

        CodeMirror.commands.autocompleteEmoji = function (cm) {
            cm.showHint({hint: CodeMirror.hint.emoji, completeSingle: false});
            return CodeMirror.Pass;
        };

        // init codemirror
        var commentEditor = new Editor({
            element: document.getElementById(conf.id),
            dragDrop: false,
            lineWrapping: true,
            status: false,
            htmlURL: latkeConfig.servePath + '/console/markdown/2html',
            toolbar: [
                {name: 'bold'},
                {name: 'italic'},
                {name: 'quote'},
                {name: 'link'},
                {name: 'unordered-list'},
                {name: 'ordered-list'},
                {
                    name: 'image',
                    html: '<span style="display: inline-block;top:1px" class="tooltipped tooltipped-n" aria-label="' + Label.uploadFilesLabel + '" ><form id="' + conf.id + 'fileUpload" method="POST" enctype="multipart/form-data"><label class="icon-upload"><input type="file"/></label></form></span>'
                },
                {name: 'redo'},
                {name: 'undo'},
                {name: 'preview'},
                {name: 'fullscreen'}],
            extraKeys: {
                "Cmd-/": "autocompleteEmoji",
                "Ctrl-/": "autocompleteEmoji"
            }
        });
        commentEditor.render();

        Util.initUploadFile({
            "id": conf.id + 'fileUpload',
            "pasteZone": $('#' + conf.id).next().next(),
            "qiniuUploadToken": qiniu.qiniuUploadToken,
            "editor": commentEditor.codemirror,
            "uploadingLabel": 'uploading...',
            "qiniuDomain": '//' + qiniu.qiniuDomain
        });

        this[conf.id] = commentEditor.codemirror;

        this[conf.id].on('changes', function (cm) {
            if ($('#' + conf.id).parent().find('.CodeMirror-preview').length === 0) {
                return false;
            }

            $.ajax({
                url: latkeConfig.servePath + '/console/markdown/2html',
                type: "POST",
                cache: false,
                data: {
                    markdownText: cm.getValue()
                },
                success: function (result, textStatus) {
                    $('#' + conf.id).parent().find('.CodeMirror-preview').html(result.html);
                    hljs.initHighlighting.called = false;
                    hljs.initHighlighting();
                }
            });
        });

        // after render, call back function
        if (typeof (conf.fun) === "function") {
            conf.fun();
        }
    },
    /*
     * @description 获取编辑器值
     * @param {string} id 编辑器id
     * @returns {string} 编辑器值
     */
    getContent: function (id) {
        return this[id].getValue();
    },
    /*
     * @description 设置编辑器值
     * @param {string} id 编辑器 id
     * @param {string} content 设置编辑器值
     */
    setContent: function (id, content) {
        this[id].setValue(content);
        var $preview = $("#" + id).parent().find(".markdown-preivew");
        $preview.find(".markdown-preview-main").html(content);
    },
    /*
     * @description 销毁编辑器值
     * @param {string} id 编辑器 id
     */
    remove: function (id) {
        this[id].toTextArea();
        $('.editor-toolbar').remove();
    }
};