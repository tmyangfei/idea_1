package com.itheima.service.impl;

import com.itheima.dao.ItemDao;
import com.itheima.domain.ResultModel;
import com.itheima.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 21:00
 * @Description:
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    // 注入dao
    @Autowired
    private ItemDao itemDao;

    @Override
    public ResultModel queryIndex(String queryString, String catelog_name, String price, String sort,
                                  Integer page, Integer rows) {
        // 创建SolrQuery对象,封装搜索条件
        SolrQuery solrQuery = new SolrQuery();
        // 判断条件的有效性
        if(StringUtils.isEmpty(queryString)){
            // 条件为空,查询所有
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery(queryString);
        }
        // 设置默认域
        solrQuery.set("df","product_keywords");
        // 设置过滤条件
        if(!StringUtils.isEmpty(catelog_name)){
            solrQuery.addFilterQuery("product_catalog_name:"+catelog_name);
        }
        if(!StringUtils.isEmpty(price)){
            // 价格不为空
            String[] split = price.split("-");
            solrQuery.addFilterQuery("product_price:["+split[0]+" TO "+split[1]+"]");
        }
        // 排序方式
        if("1".equals(sort)){
            solrQuery.setSort("product_price", SolrQuery.ORDER.asc);
        }else{
            solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
        }
        // 设置分页
        // 计算开始位置
        int start = (page-1)*rows;
        solrQuery.setStart(start);
        solrQuery.setRows(rows);

        // 给搜索关键词设置高亮
        // 开启高亮
        solrQuery.setHighlight(true);
        // 设置高亮显示的字段
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        // 调用dao, 查询索引库
        ResultModel resultModel = itemDao.queryIndex(solrQuery);
        // 设置当前页
        resultModel.setCurPage(page);
        // 设置总页码
        if(resultModel.getTotalCount()%rows==0){
            resultModel.setTotalPages((int) (resultModel.getTotalCount()/rows));
        }else{
            resultModel.setTotalPages((int) (resultModel.getTotalCount()/rows)+1);
        }
        return resultModel;
    }
}
