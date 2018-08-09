package com.itheima.dao.impl;

import com.itheima.dao.ItemDao;
import com.itheima.domain.Item;
import com.itheima.domain.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 20:25
 * @Description: 执行查询,访问远程solr服务器的索引库
 */
@Repository
public class ItemDaoImpl implements ItemDao {

    // 注入SolrServer
    @Autowired
    private SolrServer solrServer;

    /*
        根据查询需求查询索引库
     */
    @Override
    public ResultModel queryIndex(SolrQuery solrQuery) {
        try {
            // 执行查询,获取响应的对象
            QueryResponse response = solrServer.query(solrQuery);
            // 获取响应的结果集
            SolrDocumentList results = response.getResults();
            // 创建ResultModel对象,封装所有分页数据
            ResultModel resultModel = new ResultModel();

            // 创建list集合封装Item
            List<Item> list = new ArrayList<>();
            if(results.size()>0) {
                // 总记录封装
                Long numFound = results.getNumFound();
                resultModel.setTotalCount(numFound);

                // 遍历results
                for (SolrDocument document : results) {
                    // 创建Item对象,封装商品数据
                    Item item = new Item();
                    String id = (String)document.get("id");
                    item.setPid(id);

                    String picture = (String)document.get("product_picture");
                    item.setPicture(picture);

                    String name = (String)document.get("product_name");
                    // 给name添加高亮
                    Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                    Map<String, List<String>> listMap = highlighting.get(id);
                    List<String> productName = listMap.get("product_name");
                    // 判断高亮是否存在
                    if(productName!=null && productName.size()>0){
                        name = productName.get(0);
                    }
                    item.setName(name);

                    Float price = (Float)document.get("product_price");
                    item.setPrice(price);

                    list.add(item);
                }
                resultModel.setList(list);
                return resultModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
