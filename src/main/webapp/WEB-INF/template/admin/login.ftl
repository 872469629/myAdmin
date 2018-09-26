<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>极速云商管理系统</title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${base}/resources/css/bootstrap.min.css?v=3.3.6">
    <link rel="stylesheet" href="${base}/resources/css/form-elements.css">
    <link rel="stylesheet" href="${base}/resources/css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body >
    <!-- Top content -->
    <div class="top-content" >

        <div class="inner-bg">
            <div class="container">
                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 text">
                        <h1><strong><img src="https://img.jisuyunshang.com/images/managesystemlogo.png" alt=""></strong> </h1>
                        <div class="description">
                            <!--<p>-->
                            <!--This is a free responsive login form made with Bootstrap.-->
                            <!--Download it on <a href="#"><strong>AZMIND</strong></a>, customize and use it as you like!-->
                            <!--</p>-->
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-sm-offset-3 form-box">
                        <div class="form-bottom">
                            <div class=" alert-danger alert-dismissible col-sm-3 " role="alert" style="display: none;">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close" style="top:4px;"><span aria-hidden="true">&times;</span></button>
                                <strong>提示!</strong> <span id="tip"></span>
                            </div>

                            <form role="form" action="login.jhtml" method="post" class="login-form">
                                <input type="hidden" id="enPassword" name="enPassword">
                                [#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("adminLogin")]
				                    <input type="hidden" name="captchaId" value="${captchaId}" />
                                [/#if]
                                <div class="form-group">
                                    <label class="sr-only" for="username">用户名</label>
                                    <input type="text" name="username" placeholder="用户名" class="form-username form-control" id="username">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="password">密码</label>
                                    <input type="password" name="password" placeholder="密码" class="form-password form-control" id="password">
                                </div>
                                [#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("adminLogin")]
                                <div class="form-group">
                                    <label class="sr-only" for="captcha">验证码</label>
                                    <input type="text" name="captcha" placeholder="验证码" maxlength="4"  class="form-verification-code form-control" id="captcha" autocomplete="off">
                                    <div class="group-bar"><img class="code-img vm" id="captchaImage" src="common/captcha.jhtml?captchaId=${captchaId}"></div>
                                </div>
                                [/#if]
                                <button type="submit" class="btn">登录</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; height: 240px; width: 1903px; z-index: -999999; position: fixed;">
        <div style="background:rgba(0,0,0,0.4);width: 100%;height: 100%;"></div>
        <img src="" style="position: absolute; margin: 0px; padding: 0px; border: none; width: 1903px; height: 1269px; max-height: none; max-width: none; z-index: -999999; left: 0px; top: -514.5px;">
    </div>

    <script src="${base}/resources/js/jquery-1.11.1.min.js"></script>
    <script src="${base}/resources/js/bootstrap.min.js"></script>
    <script src="${base}/resources/js/jquery.backstretch.min.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jsbn.js"></script>
    <script type="text/javascript" src="${base}/resources/js/prng4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/rng.js"></script>
    <script type="text/javascript" src="${base}/resources/js/rsa.js"></script>
    <script type="text/javascript" src="${base}/resources/js/base64.js"></script>
    <script type="text/javascript" src="${base}/resources/js/common.js"></script>

    <!--[if lt IE 10]>
    <script src="${base}/resources/js/placeholder.js"></script>
    <![endif]-->

    <script type="text/javascript">
    $().ready( function() {
        $.backstretch("https://garage.jscdcn.com/admin/images/login_bg.jpg");

        var $loginForm = $(".login-form");//表单
        var $enPassword = $("#enPassword");
        var $username = $("#username");
        var $password = $("#password");
        var $captcha = $("#captcha");
        var $captchaImage = $("#captchaImage");
        var $tip = $("#tip");
        $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
            $(this).removeClass('input-error');
        });
        //关闭提示
        $('.close').click(function () {
            $(this).parent().fadeOut(300);
        });

        [#if failureMessage??]
		$('.alert-danger').fadeIn(300).children('#tip').text("${failureMessage.content}");
        [/#if]

        // 更换验证码
        $captchaImage.click( function() {
            $captchaImage.attr("src", "common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + new Date().getTime());
        });

        $loginForm.on('submit', function(e) {

            $(this).find('input[type="text"], input[type="password"], textarea').each(function(){
                if( $(this).val() == "" ) {
                    e.preventDefault();
                    $(this).addClass('input-error');
                }
                else {
                    $(this).removeClass('input-error');
                }
            });


            if ($username.val() == "") {
                $('.alert-danger').fadeIn(300).children('#tip').text("请输入您的用户名");
                return false;
            }
            if ($password.val() == "") {
                $('.alert-danger').fadeIn(300).children('#tip').text("请输入您的密码");
                return false;
            }
            if ($captcha.val() == "") {
                $('.alert-danger').fadeIn(300).children('#tip').text("请输入您的验证码");
                return false;
            }else{
                $('.alert-danger').fadeOut(300).children('#tip').text("");
            }


            var rsaKey = new RSAKey();
            rsaKey.setPublic(b64tohex("${modulus}"), b64tohex("${exponent}"));
            var enPassword = hex2b64(rsaKey.encrypt($password.val()));
            $enPassword.val(enPassword);
        });
    });
    </script>
</body>

</html>