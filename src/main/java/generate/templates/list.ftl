[#include "/admin/include/_header.ftl"]
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="breadcrumb">
		<a href="${base}/admin/common/index.jhtml">${smbol}message("admin.breadcrumb.home")}</a> &raquo; ${name} <span>(${smbol}message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${smbol}message("admin.common.add")}
			</a>
			<div class="buttonGroup">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${smbol}message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${smbol}message("admin.common.refresh")}
				</a>
				<div id="pageSizeMenu" class="dropdownMenu">
					<a href="javascript:;" class="button">
						${smbol}message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<ul>
						<li[#if page.pageSize == 10] class="current"[/#if] val="10">10</li>
						<li[#if page.pageSize == 20] class="current"[/#if] val="20">20</li>
						<li[#if page.pageSize == 50] class="current"[/#if] val="50">50</li>
						<li[#if page.pageSize == 100] class="current"[/#if] val="100">100</li>
					</ul>
				</div>
			</div>
			<div id="searchPropertyMenu" class="dropdownMenu">
				<div class="search">
					<span class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${smbol}page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<ul>
					<li[#if page.searchProperty == "name"] class="current"[/#if] val="title">名称</li>
				</ul>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<#list columnList as column>
                <#if "parentSelect"==column.type||"url"==column.type||"text"==column.type||"digits"==column.type||"enumSelect"==column.type||"date"==column.type>
                <th>
                    <a href="javascript:;" class="sort" name="${column.name}">${column.label}</a>
                </th>
            	</#if>
				</#list>
				<th>
					<span>${smbol}message("admin.common.action")}</span>
				</th>
			</tr>
			[#list page.content as bean]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${ad.id}" />
					</td>

                    <#list columnList as column>
						<#if "parentSelect"==column.type>
                    	<td>${smbol}bean.${column.name}.name}</td>
                        <#elseif "url"==column.type||"text"==column.type||"digits"==column.type>
                    	<td>${smbol}bean.${column.name}}</td>
                        <#elseif "enumSelect"==column.type>
                    	<td>${smbol}message("${modelName}.${column.name?cap_first}." + bean.${column.name})}</td>
                        <#elseif "date"==column.type>
                    	<td>
						[#if bean.${column.name}??]
							<span title="${smbol}bean.${column.name}?string("yyyy-MM-dd HH:mm:ss")}">${smbol}bean.${column.name}}</span>
						[#else]
							-
						[/#if]
						</td>
						</#if>
                	</#list>
					<td>
						<a href="edit.jhtml?id=${smbol}bean.id}">[${smbol}message("admin.common.edit")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
[#include "/admin/include/_footer.ftl"]