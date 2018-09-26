[#include "/v3admin/include/_header.ftl"]
<div class="page-header">
    当前位置：<span class="text-primary">${optype}${name}</span>
</div>

<div class="page-content">
    <div class="page-sub-toolbar">
        <span class=''>
                <a class="btn btn-primary btn-sm" href="#">${optype}${name}</a>
        </span>
    </div>
    <form action="save.jhtml" method="post" class="form-horizontal form-validate" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${editId}" />
    <#list columnList as column>
    <div class="form-group">
        <label class="col-lg control-label <#if column.required>must</#if>">${column.label}</label>
        <div class="col-sm-9 col-xs-12">
            <#if "url"==column.type||"text"==column.type||"digits"==column.type>
            <input type="text" name="${column.name}" class="form-control" value="${smbol}(bean.${column.name})!}" <#if column.required>data-rule-required="true"</#if> />
            <#elseif "img"==column.type>
            <div class="input-group ">
                <input type="text" name="${column.name}" value="" class="form-control" autocomplete="off">
                <span class="input-group-btn">
                            <button class="btn btn-primary" type="button" onclick="showImageDialog(this);">选择图片</button>
                        </span>
            </div>
            <div class="input-group " style="margin-top:.5em;">
                <img src="${smbol}(bean.${column.name})!'${smbol}base}/resources/v3admin/images/default-pic.jpg'}" onerror="this.src='${smbol}base}/resources/v3admin/images/nopic.png'; this.title='图片未找到.'" class="img-responsive img-thumbnail" width="150">
                <em class="close" style="position:absolute; top: 0px; right: -14px;" title="删除这张图片" onclick="deleteImage(this)">×</em>
            </div>
            <span class="help-block">请选择图片</span>
            <#elseif "file"==column.type>
            <div class="input-group ">
                <input type="text" name="${column.name}" value="" class="form-control" autocomplete="off">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" onclick="showImageDialog(this);">选择文件</button>
                </span>
            </div>
            <span class="help-block">请选择文件</span>
            <#elseif "editor"==column.type>
            <div class="">
                <textarea id="${column.name}" name="${column.name}" type="text/plain" style="height:300px;">${smbol}(bean.${column.name})!}</textarea>
                <script type="text/javascript">
                    require(['util'], function(util){
                        util.editor('${column.name}', {
                            height : 300,
                            dest_dir : '',
                            image_limit : 5120000,
                            allow_upload_video : true,
                            audio_limit : 5120000,
                            callback : ''
                        });
                    });
                </script>
            </div>
            <#elseif "textArea"==column.type>
            <textarea name="${column.name}" class="form-control"rows="5" >${smbol}(bean.${column.name})!}</textarea>
            <#elseif "date"==column.type>
            <div class="input-group">
                <span class="input-group-addon">选择时间</span>
                <input type="text" name="${column.name}"  value="${smbol}.now?string('yyyy-MM-dd HH:mm:ss')}" placeholder="请选择日期时间" readonly="readonly" class="datetimepicker form-control" style="padding-left:12px;" />
                <script type="text/javascript">
                    require(["datetimepicker"], function(){
                        var option = {
                            lang : "zh",
                            step : 5,
                            timepicker : true,
                            closeOnDateSelect : true,
                            format : "Y-m-d H:i"
                        };
                        $(".datetimepicker[name = '${column.name}']").datetimepicker(option);
                    });
                </script>
            </div>
            <#elseif "enumSelect"==column.type>
            <select id="type" name="type" class="form-control tp_is_default" style="width: 105px;">
            [#list ${column.name}s as ${column.name}]
                <option value="${smbol}${column.name}}" [#if ${column.name} == bean.${column.name}] selected="selected"[/#if]>${smbol}message("${model}.${column.name?cap_first}." + ${column.name})}</option>
            [/#list]
            </select>
            <span class='help-block'>请选择${column.label}</span>
            <#elseif "parentSelect"==column.type>
            <select id="type" name="type" class="form-control tp_is_default" style="width: 105px;">
            [#list ${column.name}s as ${column.name}]
                <option value="${smbol}${column.name}.id}" [#if ${column.name}.id == bean.${column.name}.id] selected="selected"[/#if]>${smbol}${column.name}.name}</option>
            [/#list]
            </select>
            <span class='help-block'>请选择${column.label}</span>
            </#if>
        </div>
    </div>
    </#list>

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






