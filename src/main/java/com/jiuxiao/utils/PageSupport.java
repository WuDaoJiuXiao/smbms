package com.jiuxiao.utils;

/**
 * 分页支持工具类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/23 17:24
 * @since 1.0.0
 */
public class PageSupport {

    /**
     * 当前页码
     */
    private int currentPageNo;

    /**
     * 表总数量
     */
    private int totalCount;

    /**
     * 页面容量
     */
    private int pageSize;

    /**
     * 总页数 totalCount/pageSize
     */
    private int totalPageCount;

    public PageSupport() {
        currentPageNo = 1;
        totalCount = 0;
        pageSize = 1;
        totalPageCount = 1;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        if (currentPageNo > 0) {
            this.currentPageNo = currentPageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            this.setTotalPageCountByRs();   //设置总页数
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    private void setTotalPageCountByRs() {
        int over = this.totalCount % this.pageSize;
        if (over == 0) {
            this.totalPageCount = this.totalCount / this.pageSize;
        } else if (over > 0) {
            this.totalPageCount = this.totalCount / this.pageSize + 1;
        } else {
            this.totalPageCount = 0;
        }
    }
}
