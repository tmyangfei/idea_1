package com.itheima.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/3 17:02
 * @Description:    根据关键词查询索引库
 */

public class QueryIndex {

    @Test
    public void searchIndex()throws Exception{
        // 查询索引库原理 : 使用关键词匹配最小分词单元,进行搜索

        // 指定存储索引库路径
        String path = "D:\\develop\\Lucene\\index";
        // 读取索引库索引
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
        // 创建查询索引库核心对象
        IndexSearcher searcher = new IndexSearcher(reader);

        // 指定搜索关键词
        String qname = "渣渣辉";
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建查询解析器,对查询关键词进行分词 ,参数 : 版本,域字段名,分词器
        QueryParser parser = new QueryParser(Version.LUCENE_4_10_3,"title",analyzer);
        // 关键词分词
        Query query = parser.parse(qname);
        // 使用搜索核心对象进行搜索
        // topDocs:文档概要对象
        // 里面 : 文档得分(得分越高,排名越靠前),文档id(文档编号),文档命中总记录数
        TopDocs topDocs = searcher.search(query,10);
        
        // 获取文档命中总记录数
        int totalHits = topDocs.totalHits;
        System.out.println("文档命中总记录数:"+totalHits);

        // 获取文档得分
        float maxScore = topDocs.getMaxScore();
        System.out.println("文档maxScore:"+maxScore);

        // 获取文档得分数组对象
        ScoreDoc[] docs = topDocs.scoreDocs;
        // 遍历,得到文档id
        for (ScoreDoc doc : docs) {
            // 获取文档得分
            float score = doc.score;
            System.out.println("文档得分:"+score);
            // 文档id
            int docId = doc.doc;
            System.out.println("文档id:"+docId);
            // 根据文档id获取文档对象
            Document document = searcher.doc(docId);
            // 获取域字段id
            String id = document.get("id");
            System.out.println("文档域id:"+id);
            // 获取标题
            String title = document.get("title");
            System.out.println("文档标题:"+title);
            // 获取描述
            String desc = document.get("desc");
            System.out.println("文档标题:"+desc);
            // 获取文档内容
            String content = document.get("content");
            System.out.println("文档内容:"+content);
        }
    }
}
