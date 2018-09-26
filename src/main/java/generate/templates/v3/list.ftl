[#include "/v3admin/include/_header.ftl"]
<div class="page-header">当前位置：<span class="text-primary">${name}</span></div>
<div class="page-content">

    <form action="list.jhtml" id="search" method="get" class="form-horizontal form-search" role="form">
        <div class="page-toolbar">
            <div class="pull-left">
                <a class='btn btn-primary btn-sm' href="add.jhtml"><i class='fa fa-plus'></i> ${smbol}message("admin.common.add")}</a>
            </div>
            <div class="pull-right col-md-6">
                <div class="input-group">
                    <div class="input-group-select">
                        <select name="enabled" class='form-control'>
                            <option value="">条件</option>
                            <option value="name" [#if page.searchProperty == "name"] selected[/#if]>名称</option>
                        </select>
                    </div>
                    <input type="text" class=" form-control" name='searchValue' value="${page.searchValue}" placeholder="请输入关键词">
                    <span class="input-group-btn">
                        <button class="btn btn-primary" type="submit"> 搜索</button>
                    </span>
                </div>
            </div>
        </div>
    </form>


    [#if page.content?size==0]
		<div class="panel panel-default">
            <div class="panel-body empty-data">未查询到相关数据</div>
        </div>
	[#else]
		<form action="" method="post" >
            <div class="page-table-header">
                <input type="checkbox">
                <div class="btn-group">
                    <button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch-remove' data-confirm="确认要删除?" data-href="delete.jhtml"><i class='icow icow-shanchu1'></i> ${smbol}message("admin.common.delete")}</button>
                </div>
            </div>
            <table class="table table-hover table-responsive">
                <thead>
                <tr>
                    <th style="width:25px;"></th>
                    <#list columnList as column>
                    <#if "parentSelect"==column.type||"url"==column.type||"text"==column.type||"digits"==column.type||"enumSelect"==column.type||"date"==column.type>
                    <th style="width:60px;">${column.label}</th>
					</#if>
					</#list>
                    <th style="width: 95px">${smbol}message("admin.common.action")}</th>
                </tr>
                </thead>
                <tbody>
					[#list page.content as bean]
                    <tr>
                        <td><input type='checkbox' name="ids"  value="${bean.id}"/></td>

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
                            <div class="btn-group">
                                <a class="btn  btn-op btn-operation" href="edit.jhtml?id=${smbol}bean.id}" title="${smbol}message("admin.common.edit")}">
									<span data-toggle="tooltip" data-placement="top" title="" data-original-title="${smbol}message("admin.common.edit")}">
										<i class="icow icow-bianji2"></i>
									</span>
                                </a>
                            </div>
                        </td>
                    </tr>
					[/#list]
                </tbody>
                <tfoot>
                <tr>
                    <td><input type="checkbox"></td>
                    <td colspan="2">
                        <div class="btn-group">
                            <button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch-remove' data-confirm="确认要删除?" data-href="delete.jhtml"><i class='icow icow-shanchu1'></i> ${smbol}message("admin.common.delete")}</button>
                        </div>
                    </td>
                    <td colspan="4" style="text-align: right">
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
							[#include "/v3admin/include/pagination.ftl"]
						[/@pagination]
                    </td>
                </tr>
                </tfoot>
            </table>
        </form>
	[/#if]
</div>
[#include "/v3admin/include/_footer.ftl"]