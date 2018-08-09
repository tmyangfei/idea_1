package com.itheima.queryParser;

import com.itheima.utils.QueryParserUtils;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 16:06
 * @Description:        查询解析器
 */

public class QueryParser_1 {
    /**
        1.QueryParser : 每次只能查询匹配一个域字段
        QueryParser parser = new QueryParser(版本,"域字段名",分词器)
        缺点 : 无法同时检索多个域字段
     */
    @Test
    public void queryParser_()throws Exception{
        // 创建查询解析器
        QueryParser parser = new QueryParser(Version.LUCENE_4_10_3,"title",new IKAnalyzer());
        // 定义搜索关键词
        String keyword = "lucene";
        // 将关键词进行分词
        Query query = parser.parse(keyword);
        // 调用查询索引库工具类进行查询
        QueryParserUtils.indexSearch(query);
    }

    /**
     *   2.多字段匹配查询解析器   MultiFieldQueryParser
     *      查询时可以匹配多个域字段
     */
    @Test
    public void multiField_queryParser() throws Exception {
        // 创建多字段匹配查询解析器
        MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_4_10_3,
                new String[]{"title","desc","content"},
                new IKAnalyzer());
        String keyword = "引擎";
        // 将搜索关键字进行分词
        Query query = parser.parse(keyword);
        // 调用工具类进行查询匹配
        QueryParserUtils.indexSearch(query);
    }
}
