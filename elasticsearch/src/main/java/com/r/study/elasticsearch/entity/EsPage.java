package com.r.study.elasticsearch.entity;

import java.io.Serializable;
import java.util.List;

/**
 * elastic search page
 * date 2021-04-27 17:08
 *
 * @author HeYiHui
 **/
public class EsPage<T> implements Serializable {

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Integer totals;

    /**
     * 总页数
     */
    private Integer totalPage;

    private List<T> data;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
        if (pageSize != null) {
            this.totalPage = totals % pageSize == 0 ? totals / pageSize : totals / pageSize + 1;
        }
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
