
    <div id="page-loading">
        <div class="page-loading-inner">
            <div class="sk-three-bounce">
                <div class="sk-child sk-bounce1"></div>
                <div class="sk-child sk-bounce2"></div>
                <div class="sk-child sk-bounce3"></div>
            </div>
        </div>
    </div>

<!--    <#if copyright??>${copyright }</#if>-->
<!--     <#if copyright??> -->
<!--     <div class="wb-footer" style='width:100%;'> -->
<!--         <div>${copyright }</div> -->
<!--     </div> -->
<!--     </#if> -->

</div>




<script language='javascript'>
	<!--图片操作-->
	function showImageDialog(elm, opts, options) {
        require(["util"], function(util){
            var btn = $(elm);
            var ipt = btn.parent().prev();
            var val = ipt.val();
            var img = ipt.parent().next().children();
            options = {'global':false,'class_extra':'','direct':true,'multiple':false,'fileSizeLimit':5120000};
            util.image(val, function(url){
                if(url.url){
                    if(img.length > 0){
                        img.get(0).src = url.url;
                        img.closest(".input-group").show();
                    }
                    ipt.val(url.url);
                    ipt.attr("filename",url.filename);
                }
                if(url.media_id){
                    if(img.length > 0){
                        img.get(0).src = "";
                    }
                    ipt.val(url.media_id);
                }
            }, options);
        });
    }
    function deleteImage(elm){
        require(["jquery"], function($){
            $(elm).prev().attr("src", "${base}/static/images/default-pic.jpg");
            $(elm).parent().prev().find("input").val("");
        });
    }
	<!--图片操作-->
	
    require(['bootstrap'], function ($) {
        $('[data-toggle="tooltip"]').tooltip("destroy").tooltip({
            container: $(document.body)
        });
        $('[data-toggle="popover"]').popover("destroy").popover({
            container: $(document.body)
        });
    });

<!--������ʾ-->
 <#if isfounder?? && routes?? && routes!='system.auth.upgrade'>
    function check_ewei_shopv2_upgrade() {
        require(['util'], function (util) {
            if (util.cookie.get('checkeweishopv2upgrade_sys')) {
                return;
            }
            $.post('{php echo webUrl("system/auth/upgrade/check")}', function (ret) {
                if (ret && ret.status == '1') {
                    var result = ret.result;
                    if (result.filecount > 0 || result.database || result.upgrades) {
                        $('#headingFive').find('.systips').show();
                        if($('#headingFive').attr('aria-expanded')=='false'){
                            $('#headingFive').click();
                        }
                        $('#collapseFive .nomsg').hide();
                        $('#sysversion').text(result.version);
                        $('#sysrelease').text(result.release);
                        $('#collapseFive .upmsg').show();
                    }
                }
            }, 'json');
        });
    }
      function check_ewei_shopv2_upgrade_hide() {
        require(['util'], function (util) {
            util.cookie.set('checkeweishopv2upgrade_sys', 1, 3600);
            $('#collapseFive .nomsg').show();
            $('#collapseFive .upmsg').hide();
            $('#headingFive').find('.systips').hide();
        });
    }
    $(function () {
        setTimeout( function() {
            check_ewei_shopv2_upgrade();
        },4000);
    });
</#if>

    $(function () {
        //$('.page-content').show();
        $('.img-thumbnail').each(function () {
            if ($(this).attr('src').indexOf('nopic.jpg') != -1) {
                $(this).attr('src', "{EWEI_SHOPV2_LOCAL}static/images/nopic.jpg");
            }
        })
//         {php $task_mode =intval(m('cache')->getString('task_mode', 'global'))}
//         {if $task_mode==0}
//             $.getJSON("{php echo webUrl('util/task')}");
            $.getJSON("#");
//         {/if}
    });
</script>
<script language="javascript">
    myrequire(['web/init']);
    if( $('form.form-validate').length<=0){
        window.formInited = true;
    }
    window.formInitTimer = setInterval(function () {
        if (typeof(window.formInited ) !== 'undefined') {
            $('#page-loading').remove();
            clearInterval(window.formInitTimer);
        }else{
            //$('#page-loading').show();
        }
    }, 1);
</script>

</body>
</html>
