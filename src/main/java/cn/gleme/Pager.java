package cn.gleme;

/**
 * 
 * <p>Title: 分页控件</p>
 *
 * @author cc
 * @date 2018年05月11日 下午2:37:10
 */
public class Pager {
	private int pageNumber = 1; // 当前页
	
	private int pageSize = 10; //每页条数
	
	private int totalPages; //多少页
	
	private int total; //所有条数
	
	private String sortField; //按哪个字段排序
	
	private String sortType; //顺序还是倒序
	
	public Pager()
	{
	    
	}
	
	public Pager(int pageSize)
	{
	    this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
