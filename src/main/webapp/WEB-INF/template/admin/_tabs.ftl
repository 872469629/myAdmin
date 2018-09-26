<div class="subnav-scene">
	${sencondAuthResource.resName!'' }
</div>

<#if sencondAuthResource??>
	<#if sencondAuthResource.children?? &&  (sencondAuthResource.children?size>0) >
		<#list sencondAuthResource.children as children>
			<#if children.children?? && (children.children?size>0) >
				<div class='menu-header <#if curentAuthResource?? && children.children?? && children.children?seq_contains(curentAuthResource)>active data-active</#if>'>
					<div class="menu-icon fa fa-caret-<#if curentAuthResource?? && children.children?? && children.children?seq_contains(curentAuthResource)>down<#else>right</#if>"></div>${children.resName!'' }
				</div>
				<ul <#if curentAuthResource?? && children.children?? && children.children?seq_contains(curentAuthResource)>style="display: block"</#if>>
					<#list children.children as item>
						<li <#if curentAuthResource?? && item?? && item.id == curentAuthResource.id>class="active"</#if>><a href="${item.actionStr }" style="cursor: pointer;" data-route="{$threemenu['route']}">${item.resName!'' }</a>
					</#list>
				</ul>
			<#else>
<!-- 			{if $submenu['title']<>'历史日志'} -->
				<ul class="single">
					<li <#if curentAuthResource?? && children.children?? && children.children?seq_contains(curentAuthResource)>class="active"</#if> style=" position: relative"><a href="{$submenu['url']}" style="cursor: pointer;" data-route="{$submenu['route']}">${children.resName!'' }</a></li>
				</ul>
			</#if>
		</#list>
	</#if>
</#if>
<!--6Z2S5bKb5piT6IGU5LqS5Yqo572R57uc56eR5oqA5pyJ6ZmQ5YWs5Y+454mI5p2D5omA5pyJ-->