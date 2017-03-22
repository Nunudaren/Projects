package cn.itcast.goods.pager;

import java.util.List;

/**
 * 分页Bean,它会在各层之间传递
 * @author Andy
 *
 * @param <T>
 */
public class PageBean<T> {
	private int currPage;//当前页
	private int totalCount;//总记录数
	private int pageSize;//每页显示的记录数
	private String url;//请求的路径和参数，例如：/BookServlet?method=findXxx&cid=1&bname=2
	private List<T> beanList;//每页显示的数据
	
	@SuppressWarnings("unused")
	private int totalPage;//总页数
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	//计算总共页数
	public int getTotalPage() {
		int tp = totalCount / pageSize;
		return totalCount % pageSize == 0 ? tp : tp + 1;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<T> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
}
