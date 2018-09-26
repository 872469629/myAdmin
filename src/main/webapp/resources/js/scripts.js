
jQuery(document).ready(function() {

    /*
        Fullscreen background
    */
    $.backstretch("https://garage.jscdcn.com/admin/images/login_bg.jpg");

    /*
        Form validation
    */


    var $loginForm = $(".login-form");//表单
    var $enPassword = $("#enPassword");
    var $username = $("#username");
    var $password = $("#password");
    var $captcha = $("#captcha");
    var $captchaImage = $("#captchaImage");
    var $tip = $("#tip");
    captcha.jhtml
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
        $(this).removeClass('input-error');
    });
    //关闭提示
    $('.close').click(function () {
        $(this).parent().fadeOut(300);
    })
    // 更换验证码
    $captchaImage.click( function() {$().alert()
        $captchaImage.attr("src", "/admin/common/captcha.jhtml?captchaId=50ee6cc2-ef36-4a4c-9835-6f1a426f4232&timestamp=" + new Date().getTime());
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

    });


});