<#assign base=request.contextPath >
<!-- {php $copyright = m('common')->getCopyright(true)} -->
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>我的后台</title>
        <link rel="shortcut icon" href="{$_W['siteroot']}{$_W['config']['upload']['attachdir']}/{if !empty($_W['setting']['copyright']['icon'])}{$_W['setting']['copyright']['icon']}{else}images/global/wechat.jpg{/if}" />
        <link href="${base}/static/css/bootstrap.min.css?v=3.3.7" rel="stylesheet">
        <link href="${base}/static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
        <link href="${base}/static/css/animate.css" rel="stylesheet">
        <link href="${base}/static/css/v3.css?v=4.1.0" rel="stylesheet">
        <link href="${base}/static/css/common_v3.css?v=3.0.1" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${base}/static/fonts/v3/iconfont.css?v=2016070717">
        <link rel="stylesheet" type="text/css" href="${base}/static/fonts/iconfont.css?v=2016070717">
        <link rel="stylesheet" type="text/css" href="${base}/static/fonts/wxiconx/iconfont.css?v=2016070717">
        <!--<link rel="stylesheet" href="//at.alicdn.com/t/font_244637_dkvlqrgjbde1m7vi.css">-->
        <script src="${base}/static/fonts/v3/iconfont.js"></script>

        <script src="${base}/resource/js/lib/jquery-1.11.1.min.js"></script>
        <script src="${base}/static/js/dist/jquery/jquery.gcjs.js"></script>
        <script src="${base}/resource/js/app/util.js"></script>


        <script type="text/javascript">
            window.sysinfo = {
            		<#if uniacid??>'uniacid':${uniacid}</#if>
            		<#if acid??>'acid':${acid}</#if>
            		<#if openid??>'openid':${openid}</#if>
            		<#if uid??>'uid':${uid}</#if>
            'isfounder': <#if isfounder??>1<#else>0</#if>,
            'siteroot': ${siteroot!'""'},
                    'siteurl': ${siteurl!'""'},
                    'attachurl': ${attachurl!'""'},
                    'attachurl_local': ${attachurl_local!'""'},
                    'attachurl_remote': ${attachurl_remote!'""'},
                    'module' : {'url' : <#if MODULE_URL??>'MODULE_URL'<#else>""</#if>, 'name' : <#if IN_MODULE??>'IN_MODULE'<#else>""</#if>},
            'cookie' : {'pre': <#if config?? && cookie?? && pre?? >${config}${cookie}${pre}<#else>""</#if>},
            'account' :<#if account??>${account}<#else>""</#if>,
            };
        </script>


        <!-- 兼容我的后台1.5.3 -->
        <link href="${base}/static/css/we7.common.css?v=1.0.0" rel="stylesheet">
        <script type="text/javascript" src="${base}/resource/js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="${base}/resource/js/app/common.min.js?v=20170802"></script>
        <script type="text/javascript">if(util){util.clip = function(){}}</script>
        <!-- 兼容我的后台1.6 -->
        <!-- 兼容我的后台1.6 -->
        <link href="${base}/static/css/we7.common172.css?v=1.0.0" rel="stylesheet">



        <script src="${base}/static/js/require.js"></script>

        <script src="${base}/static/js/config1.0.js"></script>

        <script>
            require.config({
                waitSeconds: 0
            });
        </script>
        <script src="${base}/static/js/myconfig.js"></script>
        <script type="text/javascript">
                if (navigator.appName == 'Microsoft Internet Explorer') {
                    if (navigator.userAgent.indexOf("MSIE 5.0") > 0 || navigator.userAgent.indexOf("MSIE 6.0") > 0 || navigator.userAgent.indexOf("MSIE 7.0") > 0) {
                        alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器.');
                    }
                }
                myrequire.path = "{$_W['siteroot']}addons/ewei_shopv2/static/js/";

            function preview_html(txt)
            {
                var win = window.open("", "win", "width=300,height=600"); // a window object
                win.document.open("text/html", "replace");
                win.document.write($(txt).val());
                win.document.close();
            }
        </script>
    </head>

    <body>