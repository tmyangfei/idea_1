package solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 17:29
 * @Description:
 */

public class SimpleQuery {
    // 需求: 查询索引库---简单查询
    public static void main(String[] args) throws SolrServerException {

        // solr远程服务器连接地址
        String url = "http://localhost:6080:/solr/item";
        // 创建SolrServer核心对象
        SolrServer solrServer = new HttpSolrServer(url);
        // 创建solrQuery对象 ,封装所有的查询参数
        SolrQuery solrQuery = new SolrQuery();
        // 设置简单条件
        solrQuery.setQuery("*:*");

        // 直接使用solr服务对象,查询索引库
        QueryResponse response = solrServer.query(solrQuery);

        // 从响应对象中回去文档集合
        SolrDocumentList results = response.getResults();

        //从文档集合中获取命中总记录数
        long numFound = results.getNumFound();
        System.out.println("命中总记录数:"+numFound);
        //循环文档集合，获取每一个文档对象
        for (SolrDocument solrDocument : results) {
            //根据域字段名称，获取域字段的值
            String id = (String) solrDocument.get("id");
            System.out.println("商品id:"+id);

            String product_catalog_name = (String)solrDocument.get("product_catalog_name");
            System.out.println("商品类别:"+product_catalog_name);

            Float product_price = (Float) solrDocument.get("product_price");
            System.out.println("商品价格:"+product_price);

            String product_name = (String)solrDocument.get("product_name");
            System.out.println("商品名称:"+product_name);

            String product_picture = (String)solrDocument.get("product_picture");
            System.out.println("商品图片:"+product_picture);
            System.out.println("=========================================");
        }
    }
}
