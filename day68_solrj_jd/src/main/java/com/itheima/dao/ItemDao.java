package com.itheima.dao;

import com.itheima.domain.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 20:16
 * @Description:
 *    执行查询,访问远程solr服务器的索引库
 */

public interface ItemDao {
    ResultModel queryIndex(SolrQuery solrQuery);
}
