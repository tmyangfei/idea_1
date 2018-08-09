package com.itheima.domain;

import org.apache.lucene.index.IndexWriter;

import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 20:24
 * @Description:
 */

public class ResultModel {
    // 当前页
    private Integer curPage;
    // 总页数
    private Integer totalPages;
    // 总记录
    private Long totalCount ;
    // 分页数据
    private List<Item> list ;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
