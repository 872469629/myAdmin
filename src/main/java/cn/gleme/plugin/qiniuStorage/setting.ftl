[#include "/admin/include/_header.ftl"]
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		errorClass: "fieldError",
		ignoreTitle: true,
		rules: {
			endpoint: "required",
			accessId: "required",
			accessKey: "required",
			bucketName: "required",
			urlPrefix: "required",
			order: "digits"
		}
	});
	
});
</script>
	<div class="breadcrumb">
		<a href="${base}/admin/common/index.jhtml">${message("admin.breadcrumb.home")}</a> &raquo; 七牛云
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>secretKey:
				</th>
				<td>
					<input type="text" name="secretKey" class="text" value="${pluginConfig.getAttribute("secretKey")}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.plugin.ossStorage.accessKey")}:
				</th>
				<td>
					<input type="text" name="accessKey" class="text" value="${pluginConfig.getAttribute("accessKey")}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.plugin.ossStorage.bucketName")}:
				</th>
				<td>
					<input type="text" name="bucketName" class="text" value="${pluginConfig.getAttribute("bucketName")}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.plugin.ossStorage.urlPrefix")}:
				</th>
				<td>
					<input type="text" name="urlPrefix" class="text" value="${pluginConfig.getAttribute("urlPrefix")}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>url:
				</th>
				<td>
					<input type="text" name="url" class="text" value="${pluginConfig.getAttribute("url")}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" value="${pluginConfig.order}" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					${message("StoragePlugin.isEnabled")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isEnabled" value="true"[#if pluginConfig.isEnabled] checked[/#if] />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='../list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
[#include "/admin/include/_footer.ftl"]