package com.example.tool;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 封装分页类PageInfo（剔除不需要的字段）
 * @param <T>
 */
@Data
public class PageBen<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private long total;
    private int pages;
    private List<T> list;

    public PageBen(){

    }

    public PageBen(int pageNum, int pageSize, int size, long total, int pages, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }

    public PageBen(PageInfo pageInfo) {
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.size = pageInfo.getSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.list = pageInfo.getList();
    }

}
