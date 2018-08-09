package com.itheima.queryParser;

import com.itheima.utils.QueryParserUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 16:55
 * @Description:
 */

public class TermQuery_ {
    /*
        1. 最小分词单元检索 : TermQuery
            场景 : 查询关键词必须是最小分词单元单词
     */
    @Test
    public void termQuery() throws Exception {
        String keyword = "传智播客";
        // 创建TermQuery对象,进行查询
        TermQuery termQuery = new TermQuery(new Term("title",keyword));
        // 调用工具类进行查询
        QueryParserUtils.indexSearch(termQuery);
    }
    /*
        2. 模糊检索 : WildCardQuery
     */
    @Test
    public void wildCardQuery() throws Exception {
        String keyword = "经";
        WildcardQuery query = new WildcardQuery(new Term("title",keyword+"*"));
        QueryParserUtils.indexSearch(query);
    }
    /*
        3. 相似度检索 : FuzzyQuery
     */
    @Test
    public void fuzzyQuery() throws Exception {
        // 搜索关键词为英文 : 关键词最多经过2次变化能变回原单词,就认为是相似
        // String keyword_en = "lucene";
        // 搜索关键词为汉字 : 关键词最多只能经过1次变化能变回原单词
        String keyword = "传播客";
        // 创建相似度检索对象
        FuzzyQuery query = new FuzzyQuery(new Term("title",keyword));
        QueryParserUtils.indexSearch(query);
    }
    /*
        4. 范围搜索 : NumericRangeQuery
     */
    @Test
    public void numericRangeQuery() throws Exception {
        // 创建范围搜索对象
        // 参数 : 1. 指定搜索的域字段 ; 2,起始查询的位置, 3.结束查询的位置 ;
        //       4. 是否包含起始位置 ; 5. 是否包含结束位置
        NumericRangeQuery query = NumericRangeQuery.newLongRange("id",10L,20L,true,true);
        QueryParserUtils.indexSearch(query);
    }
    /*
        5. 查询所有: MatchAllDocsQuery
     */
    @Test
    public void matchAllDocsQuery() throws Exception {
        // 创建查询所有单词的对象:
        MatchAllDocsQuery matchAll = new MatchAllDocsQuery();
        QueryParserUtils.indexSearch(matchAll);
    }
    /*
        6.组合查询 : BooleanQuery
     */
    @Test
    public void booleanQuery() throws Exception {
        // 需求: 将查询所有,范围查询进行组合,查询交集
        // 匹配所有
        MatchAllDocsQuery matchAll = new MatchAllDocsQuery();
        // 查询指定条记录数
        NumericRangeQuery rangeQuery = NumericRangeQuery.newLongRange("id",10L,20L,true,true);
        // 创建组合查询对象
        BooleanQuery booleanQuery = new BooleanQuery();
        //  BooleanClause.Occur.MUST : matchAll,必须发生
        booleanQuery.add(matchAll, BooleanClause.Occur.MUST);   // 查询所有
        // BooleanClause.Occur.MUST_NOT: 求补集,rangeQuery,结果的补集
        booleanQuery.add(rangeQuery, BooleanClause.Occur.MUST_NOT);
        QueryParserUtils.indexSearch(booleanQuery);
    }
}
