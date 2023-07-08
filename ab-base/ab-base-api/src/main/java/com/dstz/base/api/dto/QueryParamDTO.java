package com.dstz.base.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 查询条件的入参
 * @author Jeff
 *
 */
public class QueryParamDTO {
	
    /**
     * 每页显示条数，默认 10
     */
	@Max(1000)@Min(1)
    protected long pageSize = 10;

    /**
     * 当前页
     */
    protected long currentPage = 1;
	
    /**
     * 排序字段
     */
	private String sortColumn;
	
	/**
	 * 排序 DESC ASC
	 */
	private String sortOrder;
	
	/**
	 * 是否查询CountSQL
	 */
	private Boolean searchCount = true;

	/**
	 * 是否启用分页
	 */
	private Boolean enablePage = true;
	
	/**
	 * bootrap 默认用的类型
	 */
	private Integer offset;

	@Max(1000)@Min(1)
	private Integer limit;

	/**
	 * 列字段
	 */
	private Set<String> columnNames;
	
	private Map<String, Object> queryParam = new HashMap<>();

	public Map<String, Object> getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(Map<String, Object> queryParam) {
		this.queryParam = queryParam;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Boolean getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Boolean searchCount) {
		this.searchCount = searchCount;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Set<String> columnNames) {
		this.columnNames = columnNames;
	}

	public Boolean getEnablePage() {
		return enablePage;
	}

	public void setEnablePage(Boolean enablePage) {
		this.enablePage = enablePage;
	}

	@Override
	public QueryParamDTO clone() {
		QueryParamDTO queryParamDTO = new QueryParamDTO();
		queryParamDTO.setQueryParam(getQueryParam());
		queryParamDTO.setSortColumn(getSortColumn());
		queryParamDTO.setSortOrder(getSortOrder());
		queryParamDTO.setPageSize(getPageSize());
		queryParamDTO.setCurrentPage(getCurrentPage());
		queryParamDTO.setOffset(getOffset());
		queryParamDTO.setLimit(getLimit());
		queryParamDTO.setSearchCount(getSearchCount());
		queryParamDTO.setColumnNames(getColumnNames());
		queryParamDTO.setEnablePage(getEnablePage());
		return queryParamDTO;
	}
}
