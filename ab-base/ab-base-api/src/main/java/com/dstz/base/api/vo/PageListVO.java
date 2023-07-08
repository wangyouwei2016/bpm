package com.dstz.base.api.vo;

import java.util.Collections;
import java.util.List;

import com.dstz.base.api.dto.PageListDTO;

/**
 * 由于pageListDTO 为list 实现，导致他反序列化存在一些问题
 * @author Jeff
 *
 * @param <T>
 */
public class PageListVO<T> {
	/**
	 * 分页大小
	 */
    private long pageSize = 0;
    /**
     * 当前页
     */
    private long page = 1;
    /**
     * 总条数
     */
    private long total = 0L;
    /**
     * 分页列表数据
     */
    private List<T> rows = null;

	public PageListVO() {
	}

	public PageListVO(PageListDTO listDTO) {
		super();
		this.pageSize = listDTO.getPageSize();
		this.page =  listDTO.getPage();
		this.total =  listDTO.getTotal();
		this.rows =  listDTO.getRows();
		if(rows == null) {
			this.rows = Collections.emptyList();
		}
	}
	
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
