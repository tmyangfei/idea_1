package com.itheima.controller;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: yangfei
 * @Date: 2018/8/5 11:21
 * @Description:
 *  使用solrj客户端连接solr远程服务器,更新索引库
 */

public class Solrj_Demo {

    // 添加 : 如果索引库文档id不存在，添加
    @Test
    public void addIndex()throws Exception{
        // 指定solr远程服务器地址
        // url = solr服务器地址+索引库名称(没修改可不指定)
        // http://localhost:6080/solr/#/~cores/products
        String url = "http://localhost:6080/solr/products";
        // 创建连接solr服务器核心对象
        SolrServer solrServer = new HttpSolrServer(url);

        // 创建文档对象
        SolrInputDocument doc = new SolrInputDocument();
        // 添加域字段
        doc.addField("id","A_22");
        doc.addField("name","雀巢咖啡");
        doc.addField("price","110.00");

        // 写入到索引库
        solrServer.add(doc);
        // t提交
        solrServer.commit();
    }

    // 更新 : 如果索引库文档id存在，表示更新
    @Test
    public void updateIndex() throws IOException, SolrServerException {
        // 指定solr远程服务器地址
        String url = "http://localhost:6080/solr/products";
        // 创建连接solr服务器核心对象
        SolrServer solrServer = new HttpSolrServer(url);
        //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 添加域字段
        document.addField("id","A_11");
        document.addField("name","黄瓜");
        document.addField("price","200.0");
        // 添加到索引库
        solrServer.add(document);
        solrServer.commit();
    }

    // 删除
    @Test
    public void deleteIndex()throws Exception{
        // 指定solr服务器的连接地址
        String url = "http://localhost:6080/solr/products";
        // 创建solrServer对象
        SolrServer solrServer = new HttpSolrServer(url);
        // 执行删除
        // 1. 根据id
        // solrServer.deleteById("A_11");
        // 2. 根据查询语法进行删除
        // solrServer.deleteByQuery("id:A_22");
        // 3. 删除全部
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
