[#include "/admin/include/_header.ftl"]
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/webuploader.js"></script>
<script type="text/javascript" src="${base}/resources/admin/ueditor/ueditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
    <#list columnList as column>
		var $${column.name} = $("#${column.name}");
		<#if "file"==column.type>
		$${column.name}_upload.uploader();
		<#elseif "editor"==column.type>
		$${column.name}.editor();
		</#if>
	</#list>


    // 表单验证
    $inputForm.validate({
        rules: {
		<#list columnList as column>
			${column.name}: {
				<#if column.required>required: true</#if>
				<#if "url"==column.type>
					<#if column.required>,</#if>pattern: /^(http:\/\/|https:\/\/|ftp:\/\/|mailto:|\/|#).*$/i
				<#elseif "digits"==column.type>
					<#if column.required>,</#if>digits:true
				</#if>

            }<#if column_has_next>,</#if>
		</#list>
        }
    });

	[@flash_message /]
});
</script>
	<div class="breadcrumb">
		<a href="${base}/admin/common/index.jhtml">${messagehome}</a> &raquo; ${optype}${name}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<input type="hidden" name="id" value="${editId}" />
		<table class="input">
			<#list columnList as column>
				<tr>
                    <th>
						<#if column.required><span class="requiredField">*</span></#if>${column.label}:
                    </th>
                    <td>
						<#if "url"==column.type||"text"==column.type||"digits"==column.type>
                        	<input type="text" id="${column.name}" name="${column.name}" class="text" value="${smbol}(bean.${column.name})!}" maxlength="200" />
						<#elseif "file"==column.type>
							<span class="fieldSet">
								<input type="text" id="${column.name}" name="${column.name}" class="text" value="${smbol}(bean.${column.name})!}" maxlength="200" />
								<a href="javascript:;" id="${column.name}_upload" class="button">${smbol}message("admin.upload.filePicker")}</a>
								<a href="${smbol}(bean.${column.name})!}" target="_blank">${smbol}message("admin.common.view")}</a>
							</span>
						<#elseif "editor"==column.type>
							<textarea id="${column.name}" name="${column.name}" class="editor">${smbol}(bean.${column.name})!}</textarea>
						<#elseif "textArea"==column.type>
							<textarea id="${column.name}" name="${column.name}" >${smbol}(bean.${column.name})!}</textarea>
						<#elseif "date"==column.type>
							<input type="text" id="${column.name}" name="${column.name}" class="text Wdate" value="[#if ad.beginDate??]${smbol}bean.${column.name}?string("yyyy-MM-dd HH:mm:ss")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
						<#elseif "enumSelect"==column.type>
							<select id="${column.name}" name="${column.name}">
								[#list ${column.name}s as ${column.name}]
									<option value="${smbol}${column.name}}" [#if ${column.name} == bean.${column.name}] selected="selected"[/#if]]${smbol}message("${model}.${column.name?cap_first}." + ${column.name})}</option>
								[/#list]
                            </select>
						<#elseif "parentSelect"==column.type>
							<select id="${column.name}.id" name="${column.name}">
							[#list ${column.name}s as ${column.name}]
									<option value="${smbol}${column.name}.id}" [#if ${column.name}.id == bean.${column.name}.id] selected="selected"[/#if]>${smbol}${column.name}.name}</option>
								[/#list]
                            </select>
						</#if>
                    </td>
                </tr>
			</#list>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${smbol}message("admin.common.submit")}" />
					<input type="button" class="button" value="${smbol}message("admin.common.back")}" onclick="history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
[#include "/admin/include/_footer.ftl"]