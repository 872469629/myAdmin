[#include "/admin/_header.ftl"]
<div class="page-header">当前位置：<span class="text-primary">幻灯片</span></div>
<div class="page-content">

    <form action="list.jhtml" id="search" method="get" class="form-horizontal form-search" role="form">
        <div class="page-toolbar">
            <div class="pull-left">
                <a class='btn btn-primary btn-sm' href="add.jhtml"><i class='fa fa-plus'></i> ${message("admin.common.add")}</a>
            </div>
            <div class="pull-right col-md-6">
                <div class="input-group">
                    <div class="input-group-select">
                        <select name="enabled" class='form-control'>
                            <option value="">条件</option>
                            <option value="name" [#if page.searchProperty == "name"] selected[/#if]>名称</option>
                        </select>
                    </div>
                    <input type="text" class=" form-control" name='searchValue' value="" placeholder="请输入关键词">
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
                    <button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch-remove' data-confirm="确认要删除?" data-href="delete.jhtml"><i class='icow icow-shanchu1'></i> ${message("admin.common.delete")}</button>
                </div>
            </div>
            <table class="table table-hover table-responsive">
                <thead>
                <tr>
                    <th style="width:25px;"></th>
                    <th style='width:50px'>顺序</th>
                    <th style="width: 180px;">标题</th>
                    <th>链接</th>
                    <th style='width:60px'>显示</th>
                    <th style="width: 65px;">操作</th>
                </tr>
                </thead>
                <tbody>
					[#list page.content as bean]
                    <tr>
                        <td><input type='checkbox' name="ids"  value="${bean.id}"/></td>

						<td>${bean.id}</td>
						<td>${bean.advname}</td>
						<td>${bean.link}</td>
						<td>
                            <span class='label [#if bean.enabled?? && bean.enabled==1 ]label-primary[#else]label-default[/#if]'
                                      data-toggle='ajaxSwitch'
                                      data-switch-value='${bean.enabled!'0' }'
                                      data-switch-value0='0|隐藏|label label-default|/admin/ims_ewei_shop_adv/enabled?id=${bean.id}&enabled=1'
                                      data-switch-value1='1|显示|label label-primary|/admin/ims_ewei_shop_adv/enabled?id=${bean.id}&enabled=0'
                            >
                                  [#if bean.enabled?? && bean.enabled==1 ]显示[#else]隐藏[/#if]
                            </span>
                        </td>

                        <td>
                            <div class="btn-group">
                                <a class="btn  btn-op btn-operation" href="edit.jhtml?id=${bean.id}" title="${message("admin.common.edit")}">
									<span data-toggle="tooltip" data-placement="top" title="" data-original-title="${message("admin.common.edit")}">
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
                    <td colspan="3">
                        <div class="btn-group">
                        	<button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch' data-href="/admin/ims_ewei_shop_adv/enabled?enabled=1"><i class='icow icow-xianshi'></i> 显示</button>
                            <button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch'  data-href="/admin/ims_ewei_shop_adv/enabled?enabled=0"><i class='icow icow-yincang'></i> 隐藏</button>
                            <button class="btn btn-default btn-sm btn-operation" type="button" data-toggle='batch-remove' data-confirm="确认要删除?" data-href="delete.jhtml"><i class='icow icow-shanchu1'></i> ${message("admin.common.delete")}</button>
                        </div>
                    </td>
                    <td colspan="2" style="text-align: right">
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
[#include "/admin/_footer.ftl"]