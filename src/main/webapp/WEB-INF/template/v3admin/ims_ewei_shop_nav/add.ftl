[#include "/v3admin/include/_header.ftl"]
<div class="page-header">
    当前位置：<span class="text-primary">[#if bean??]编辑[#else]添加[/#if]导航图标</span>
</div>

<div class="page-content">
    <div class="page-sub-toolbar">
        <span class=''>
                <a class="btn btn-primary btn-sm" href="#">[#if bean??]编辑[#else]添加[/#if]导航图标</a>
        </span>
    </div>
    <form action="save.jhtml" method="post" class="form-horizontal form-validate" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${(bean.id?c)!}" />

        <div class="form-group"></div>
        <div class="form-group">
            <label class="col-lg control-label"></label>
            <div class="col-sm-9 col-xs-12">
                <input type="submit" value="提交" class="btn btn-primary"  />
                <input type="button" name="back" onclick='history.back()' style='margin-left:10px;' value="返回列表" class="btn btn-default" />
            </div>
        </div>
    </form>

</div>
[#include "/v3admin/include/_footer.ftl"]






