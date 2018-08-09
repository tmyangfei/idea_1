package solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 18:34
 * @Description:
 */

public class FuzhaQuery {

    public static void main(String[] args) throws SolrServerException {

        // 指定solr远程服务器连接地址
        String url = "http://localhost:6080/solr/item";
        // 创建solrServer 核心对象
        SolrServer solrServer = new HttpSolrServer(url);
        // 创建SolrQuery查询对象
        SolrQuery solrQuery = new SolrQuery();
        // 设置查询条件
        // 1. 主查询
        solrQuery.setQuery("发泄");
        //solrQuery.setQuery("*:*");
        // 2. filterQuery过滤条件
        solrQuery.addFilterQuery("product_price:[0 TO 20]");
        solrQuery.addFilterQuery("product_catalog_name:幽默杂货");
        // 3. sort 排序
        solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
        // 4. 分页 start,rows
        solrQuery.setStart(0);  // 从start开始,算法: 类似(page-1)*pageSize
        solrQuery.setRows(5);   // 显示20行
        // 5. 设置要显示的字段fl
        //solrQuery.setFields("product_price","product_name");
        // 或者
        solrQuery.setFields("product_price product_name id");
        // 6. 设置df默认查询的字段,使用复制域
        solrQuery.set("df","product_keywords");
        // 7. 设置hl高亮
        // 7.1 开启高亮
        solrQuery.setHighlight(true);
        // 7.2 设置高亮显示的字段
        solrQuery.addHighlightField("product_name");
        // 7.3 设置高亮前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        // 7.4 设置高亮后缀
        solrQuery.setHighlightSimplePost("</font>");

        // 执行查询,获取response对象
        QueryResponse response = solrServer.query(solrQuery);
        // 响应的结果集
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("命中总记录:"+numFound);

        // 遍历结果集,获取文档对象
        for (SolrDocument document : results) {
            // 获取商品数据
            String id = (String)document.get("id");
            System.out.println("id:"+id);

            String product_catalog_name = (String)document.get("product_catalog_name");
            System.out.println("product_catalog_name:"+product_catalog_name);

            Float product_price = (Float)document.get("product_price");
            System.out.println("product_price:"+product_price);

            // 给name值添加高亮
            String product_name = (String)document.get("product_name");
            // 获取高亮显示  "highlighting": {
            //     "23": {
            //       "product_name": [
            //         "<font color='red'>家天下</font>红阿狸音乐午睡枕"
            //       ]
            //     }
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            // highlighting的key是id
            Map<String,List<String>> map = highlighting.get(id);
            // map的key是product_name
            List<String> lightName = map.get("product_name");
            // 判断高亮是否存在
            if(lightName.size()>0 && lightName!=null){
                product_name = lightName.get(0);
            }
            System.out.println("product_name:"+product_name);

            String product_picture = (String)document.get("product_picture");
            System.out.println("product_picture:"+product_picture);
            System.out.println("=============================");
        }
    }
}
