[#include "/admin/_header.ftl"]
<div class="page-header">
    当前位置：<span class="text-primary">[#if bean??]编辑[#else]添加[/#if]幻灯片</span>
</div>

<div class="page-content">
    <div class="page-sub-toolbar">
        <span class=''>
                <a class="btn btn-primary btn-sm" href="#">[#if bean??]编辑[#else]添加[/#if]幻灯片</a>
        </span>
    </div>
    <form action="save.jhtml" method="post" class="form-horizontal form-validate" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${(bean.id?c)!}" />
    <div class="form-group">
         <label class="col-lg control-label">排序</label>
         <div class="col-sm-9 col-xs-12">
             <input type="text" name="displayorder" class="form-control" value="${(bean.displayorder)! }"/>
             <span class='help-block'>数字越大，排名越靠前</span>
         </div>
    </div>
    <div class="form-group">
        <label class="col-lg control-label must">幻灯片标题</label>
        <div class="col-sm-9 col-xs-12">
            <input type="text" name="advname" class="form-control" value="${(bean.advname)!}" data-rule-required="true" />
        </div>
    </div>
	<div class="form-group">
        <label class="col-lg control-label">幻灯片图片</label>
        <div class="col-sm-9 col-xs-12">
            <div class="input-group ">
                <input type="text" name="thumb" value="${bean.thumb}" class="form-control" autocomplete="off">
                <span class="input-group-btn">
                        <button class="btn btn-primary" type="button" onclick="showImageDialog(this);">选择图片</button>
                    </span>
            </div>
            <div class="input-group " style="margin-top:.5em;">
                <img src="${(bean.thumb)!'${base}/static/images/default-pic.jpg'}" onerror="this.src='${base}/static/images/nopic.png'; this.title='图片未找到.'" class="img-responsive img-thumbnail" width="150">
                <em class="close" style="position:absolute; top: 0px; right: -14px;" title="删除这张图片" onclick="deleteImage(this)">×</em>
            </div>
            <span class="help-block">建议尺寸:640 * 350 , 请将所有幻灯片图片尺寸保持一致</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg control-label must">显示</label>
        <div class="col-sm-9 col-xs-12">
            <input type="text" name="displayorder" class="form-control" value="${(bean.displayorder)!}" data-rule-required="true" />
        </div>
    </div>

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
[#include "/admin/_footer.ftl"]






