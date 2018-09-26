<#include "/admin/_header_base.ftl">

<div class="wb-header" style="position: fixed;">
    <div class="logo {if !empty($system['foldnav'])}small{/if}">
        <#if (copyright.logo)??>
            <img class='logo-img' src="${copyright.logo }" onerror="this.src='${base}/static/images/nologo.png'"/>
        </#if>
    </div>
    <ul>
        <li>
            <a href="/admin/common/index.jhtml" data-toggle="tooltip" data-placement="bottom" title="管理首页"><i class="icow icow-homeL"></i></a>
        </li>
        <li class="wb-shortcut"><a id="showmenu"><i class="icow icow-list"></i></a></li>
    </ul>
    <div class="wb-topbar-search expand-search" id="navwidth">
        <form action="" id="topbar-search">
            <input type="hidden" name="c" value="site" />
            <input type="hidden" name="a" value="entry" />
            <input type="hidden" name="m" value="ewei_shopv2" />
            <input type="hidden" name="do" value="web" />
            <input type="hidden" name="r" value="search" />
            <div class="input-group">
                <input type="text" placeholder="请输入关键词进行功能搜索..." class="form-control wb-search-box" maxlength="15" name="keyword" {if $system['merch']} data-merch="1"{/if} />
                <span class="input-group-btn">
                    <a class="btn wb-header-btn"><i class="icow icow-sousuo-sousuo"></i></a>
                </span>
            </div>
        </form>
        <div class="wb-search-result">
            <ul></ul>
        </div>
    </div>
    <div class="wb-header-flex"></div>

    <ul>

		<#if appqrcode??>
	        <li   class="wxcode_box">
	            <i class="icow icow-erweima2" style="margin-right: 10px"></i>手机管理后台
	            <img src="${base}/static/images/new.gif" alt=""  style="margin-top: -10px ">
	            <div class="wx_code">
	                <img src="${appqrcode }" alt="">
	                <div class="text">扫码登录小程序管理后台</div>
	            </div>
	        </li>
		</#if>
        <#if role?? && role=='founder'>
        <li>
            <a href="#" >
                <i class="icow icow-qiehuan" style="margin-right: 10px;color: #f34347"></i>系统管理
            </a>
        </li>
        </#if>
        <li class="dropdown <#if merch??>auto</#if> ellipsis">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
            	${right_menu.menu_title!'' }
                <span></span>
            </a>

            <ul class="dropdown-menu" {if $system['routes'][0]=='system'}style="width:100%;left:0"{/if}>
            	<#if right_menu.menu_items?? && (right_menu.menu_items?size>0) >
	                <#list right_menu.menu_items as item >
	                 <li>
	                     <a href="#" <#if item.blank?? >target="_blank"</#if>>
	                         <i class="icow ${item.icon } " style="font-size: 30px;"></i>
	                         <span style="display: block">${item.text!'' }</span>
	                     </a>
	                 </li>
	                 </#list>
	                <li><a href="#">返回系统</a></li>
                <#else>
                <li style="margin-top: 0;height: 50px;line-height: 50px"><a href="#">返回系统</a></li>
                </#if>
            </ul>
        </li>
        <li data-toggle="tooltip" data-placement="bottom" title="退出登录" data-href="{$system['right_menu']['logout']}">
            <a class="wb-header-logout"><i class="icow icow-exit"></i></a>
        </li>
    </ul>

    <div class="fast-nav {if !empty($system['foldnav'])}indent{/if}">
    	<#if history??>
            <div class="fast-list history">
                <span class="title">最近访问</span>
                <#list history as item>
                    <a href="${item.url!'' }">${item.title!'' }</a>
                </#list>
                <a href="javascript:;" id="btn-clear-history" {if $system['merch']} data-merch="1"{/if}>清除最近访问</a>
            </div>
        </#if>
        <div class="fast-list menu">
            <span class="title">全部导航</span>
<!--             <#list menus as item> -->
<%--                 <a href="javascript:;" <#if item.index==0>class="active"</#if> data-tab="tab-${item.index}">${item.title!'' }</a> --%>
<!--             </#list> -->
<!--             {if !empty($system['funbar']['open']) && empty($system['merch'])} -->
<!--                 <a href="javascript:;" class="bold" data-tab="funbar">自定义快捷导航</a> -->
<!--             {/if} -->
        </div>
        <div class="fast-list list">
            {loop $sysmenus['shopmenu'] $index $shopmenu}
                <div class="list-inner {if $index==0}in{/if}" data-tab="tab-{$index}">
                    {loop $shopmenu['items'] $shopmenu_item}
                        <a href="{$shopmenu_item['url']}">{$shopmenu_item['title']}</a>
                    {/loop}
                </div>
            {/loop}
            {if !empty($system['funbar']['open']) && empty($system['merch'])}
                <div class="list-inner" data-tab="funbar" id="funbar-list">
                    {loop $system['funbar']['data'] $funbar_item}
                        <a href="{$funbar_item['href']}" style="{if $funbar_item['bold']}font-weight: bold;{/if} color: {$funbar_item['color']};">{$funbar_item['text']}</a>
                    {/loop}
                    <a href="javascript:;" class="text-center funbar-add-btn"><i class="fa fa-plus"></i> 添加快捷导航</a>
                    {if !empty($system['funbar']['data'])}
                        <a href="{php echo webUrl('sysset/funbar')}" class="text-center funbar-add-btn"><i class="fa fa-edit"></i> 编辑快捷导航</a>
                    {/if}
                    {template 'funbar'}
                </div>
            {/if}
        </div>
    </div>
</div>


    <!-- 一级导航 -->
    <div class="wb-nav {if !empty($system['foldnav'])}fold{/if}">
        <p class="wb-nav-fold"><i class="icow icow-zhedie"></i></p>
        <ul id="navheight">
        	<#list oneResourceList as item>
                <li <#if sencondAuthResource?? && sencondAuthResource.id == item.id>class="active"</#if>>
                    <a href="${item.actionStr }">
                        <#if item.iconSrc??>
                       		<#if item.iconSrc=='plugins'>
                       			<svg class="iconplug" aria-hidden="true">
                            	<use xlink:href="#icow-yingyong3"></use>
                            <#else>
                               <i class="icow icow-${item.iconSrc }"  <#if item.iconColor??>style="color: ${item.iconColor }"</#if>></i>
                               <#if item.iconSrc=='sysset'>
	                           		<span class="wb-nav-title point"></span>
                               </#if>
                       		</#if>
                        </#if>
                        <span class="wb-nav-title">${item.resName }</span>
                    </a>
                    <span class="wb-nav-tip">{$sysmenu['text']}</span>
                </li>
            </#list>
            <#if role?? && role=='founder'>
            <#if routes?? && routes=='system'>
            <li class="sysset">
            	<#if right_menu.menu_items?? && (right_menu.menu_items?size>0) >
            	<#list right_menu.menu_items as item >
                        <a href="#" <#if item.blank?? >target="_blank"</#if>>
                            <i class="icow ${item.icon }"></i>
                            <span class="wb-nav-title">${item.text!'' }</span>
                        </a>
                </#list>
                </#if>
            </li>
            <#else>
            <li class="sysset">
                <i class="icow icow-qiehuan"></i>

                <span class="wb-nav-title" data-href="">系统管理</span>
                <div class="syssetsub">
                    <div class="syssettitle">系统管理</div>
                    <a href="{php echo webUrl('system/plugin')}"><i class="icow icow-plugins "></i>应用</a>
                    <a href="{php echo webUrl('system/copyright')}"><i class="icow icow-banquan"></i>版权</a>
                    <a href="{php echo webUrl('system/data')}"><i class="icow icow-statistics"></i>数据</a>
                    <a href="{php echo webUrl('system/site')}"><i class="icow icow-wangzhan"></i>网站</a>
                    <a href="{php echo webUrl('system/auth')}"><i class="icow icow-iconfont-shouquan"></i>授权</a>
                    <a href="{php echo webUrl('system/auth/upgrade')}"><i class="icow icow-gengxin"></i>更新</a>
                    <span class="syssettips"></span>
                </div>
            </li>
            </#if>
            </#if>
        </ul>


    </div>
    <!--低分辨率一级导航显示不全问题 start-->
    <script>
        var navheight = document.getElementById('navheight');
        var navwidth = document.getElementById('navwidth')
        var vh = document.body.clientHeight;
        var vw = screen.width;
        if(vh < 800){
            navheight.classList.add("wb-navheight");
        } else {
            navheight.classList.remove("wb-navheight");
        }
        if(vw < 1300 ){
            navwidth.classList.add("wb-navwidth");
        }
    </script>
    <!--低分辨率一级导航显示不全问题 end-->


    <!-- 二级导航 -->
    <#if sencondAuthResource??>
        <div class="wb-subnav">
          <div style="width: 100%;height: 100%;overflow-y: auto">
              <#include "/admin/_tabs.ftl">
              <div class="wb-subnav-fold icow"></div>
          </div>
        </div>
	</#if>

    <!-- {if !$no_right} -->
        <div class="wb-panel <#if foldpanel?? && foldpanel==1><#else>in</#if>">
            <div class="panel-group" id="panel-accordion">
<!--                 {ifp 'order.list.status1|order.list.status4'} -->
                <div class="panel panel-default">
                    <div class="panel-heading" data-toggle="collapse" data-parent="#panel-accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        <h4 class="panel-title">
                            <i class="icow icow-dingdan"></i> <a class="news">订单消息</a> <span></span>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse {if $_W['action']!='shop.comment' && $_W['routes']!='shop.index.notice' && ($_W['action']!='apply' && $_W['plugin']!='commission')}in{/if}" aria-labelledby="headingOne">
                        <ul class="panel-body">
                            {if !empty($system['order1'])}
                            <li class="panel-list">
                                <a class="panel-list-text" href="{php echo webUrl('order/list/status1')}">待发货订单 <span class="pull-right text-warning">({$system['order1']})</span> </a>
                            </li>
                            {/if}
                            {if !empty($system['order4'])}
                            <li class="panel-list">
                                <a class="panel-list-text" href="{php echo webUrl('order/list/status4')}">维权订单<span class="pull-right text-danger">({$system['order4']})</span></a>
                            </li>
                            {/if}
                            {if empty($system['order1'])&&empty($system['order4'])}
                            <li class="panel-list">
                                <div class="panel-list-text text-center">暂无消息提醒</div>
                            </li>
                            {/if}
                        </ul>
                    </div>
                </div>
                <#if notice?? && notice!='none' && merch??>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo" data-toggle="collapse" data-parent="#panel-accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            <h4 class="panel-title">
                                <i class="icow icow-gonggao"></i> <a>内部公告</a> <span></span>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse {if $_W['routes']=='shop.index.notice'}in{/if}" role="tabpanel" aria-labelledby="headingTwo">
                            <ul class="panel-body">
                                {if empty($system['notice'])}
                                <li class="panel-list small">
                                    <div class="panel-list-text text-center">暂无消息提醒</div>
                                </li>
                                {else}
                                {loop $system['notice'] $notice_item}
                                <li class="panel-list small">
                                    <a class="panel-list-text" href="javascript:;" data-toggle="ajaxModal" data-href="{php echo webUrl('shop/index/view', array('id'=>$notice_item['id']))}" title="{$notice_item['title']}">{$notice_item['title']}</a>
                                </li>
                                {/loop}
                                <li class="panel-list small" style="padding: 10px;">
                                    <a class="panel-list-text text-center" href="{php echo webUrl('shop/index/notice')}"><span class="text-primary">查看更多</span></a>
                                </li>
                                {/if}
                            </ul>
                        </div>
                    </div>
                </#if>
                <#if merch??>
<!--                     {ifp 'commission.apply.view1|commission.apply.view2'} -->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree" data-toggle="collapse" data-parent="#panel-accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            <h4 class="panel-title">
                                <i class="icow icow-yongjinmingxi"></i> <a>佣金提现</a> <span></span>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse {if $_W['action']=='apply' && $_W['plugin']=='commission'}in{/if}" role="tabpanel" aria-labelledby="headingFour">
                            <ul class="panel-body">
                                {if !empty($system['commission1'])}
                                <li class="panel-list">
                                    <a class="panel-list-text" href="{php echo webUrl('commission/apply', array('status'=>1))}">待审核申请<span class="pull-right text-warning">({$system['commission1']})</span></a>
                                </li>
                                {/if}
                                {if !empty($system['commission2'])}
                                <li class="panel-list">
                                    <a class="panel-list-text" href="{php echo webUrl('commission/apply', array('status'=>2))}">待打款申请<span class="pull-right text-danger">({$system['commission2']})</span></a>
                                </li>
                                {/if}
                                {if empty($system['commission1'])&&empty($system['commission2'])}
                                <li class="panel-list">
                                    <div class="panel-list-text text-center">暂无消息提醒</div>
                                </li>
                                {/if}
                            </ul>
                        </div>
                    </div>
                </#if>
<!--                 {ifp 'shop.comment.edit'} -->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFour" data-toggle="collapse" data-parent="#panel-accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                        <h4 class="panel-title">
                            <i class="icow icow-pingjia"></i> <a>评价</a> <span></span>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse {if $_W['action']=='shop.comment'}in{/if}" role="tabpanel" aria-labelledby="headingFour">
                        <ul class="panel-body">
                            <#if comment??>
                            <li class="panel-list">
                                <div class="panel-list-text text-center">暂无消息提醒</div>
                            </li>
                            </#if>
                            <li class="panel-list">
                                <a class="panel-list-text" href="#">待审核评价<span class="pull-right text-warning">({$system['comment']})</span></a>
                            </li>
                            {/if}
                        </ul>
                    </div>
                </div>
                <!--系统更新-->
                <#if isfounder?? && routes?? && routes!='system.auth.upgrade' && role?? && role !='vice_founder'>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive" data-toggle="collapse" data-parent="#panel-accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                        <h4 class="panel-title">
                            <i class="icow icow-lingdang1"></i> <a style="position:relative;">系统提示 <i class="systips"></i></a> <span></span>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse {if $_W['action']=='shop.comment'}in{/if}" role="tabpanel" aria-labelledby="headingFour">
                        <ul class="panel-body">
                            <li class="panel-list">
                                <div class="panel-list-text nomsg">暂无消息提醒</div>
                                <div class="panel-list-text upmsg" style="display: none; max-height: none;">
                                    <div>检测到更新</div>
                                    <div>新版本 <span id="sysversion">------</span></div>
                                    <div>新版本 <span id="sysrelease">------</span></div>
                                    <div>
                                        <a class="text-primary" href="{php echo webUrl('system/auth/upgrade')}">立即更新</a>
                                        <a class="text-warning" href="javascript:check_ewei_shopv2_upgrade_hide();" style="margin-left: 15px;">暂不提醒</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                </#if>
            </div>
        </div>
        <div class="wb-panel-fold <#if foldpanel?? && foldpanel==1><#else>in</#if>"><#if foldpanel?? && foldpanel==1><i class="icow icow-info"></i> 消息提醒<#else><i class="fa fa-angle-double-right"></i> 收起面板</#if></div>
    <!-- {/if} -->
    <div class="wb-container <#if foldpanel?? && foldpanel==1>right-panel</#if>">
