[#escape x as x?html]
<div>
<ul class="pagination pagination-centered">
    <li><span class="nobg">(${message("admin.page.total", page.total)})</span></li>
</ul>
[#if totalPages > 1]
<ul class="pagination pagination-centered">
	[#list segment as segmentPageNumber]
		[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
			...
		[/#if]
		[#if segmentPageNumber != pageNumber]
			<li>
				<a href="javascript: select_page('${url}',${segmentPageNumber});" >${segmentPageNumber}</a>
			</li>
		[#else]
			<li class="active"><a href="javascript:;">${segmentPageNumber}</a></li>
		[/#if]
		[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
				...
		[/#if]
	[/#list]
	[#if hasNext]
		<li>
			<a href="javascript: select_page('${url}',${nextPageNumber});"  class="pager-nav">下一页»</a>
		</li>
	[/#if]
	[#if isLast]
		<li>
			<a href="javascript: select_page('${url}',${lastPageNumber});"  class="pager-nav">尾页</a>
		</li>
	[/#if]
</ul>
[/#if]
</div>
[/#escape]