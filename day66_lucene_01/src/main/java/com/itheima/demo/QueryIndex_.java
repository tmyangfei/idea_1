package com.itheima.demo;

import org.apache.lucene.analysis.Analyzer;
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
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/3 19:57
 * @Description:
 */

public class QueryIndex_ {

    @Test
    public void searchIndex()throws Exception{
        String path = "D:\\develop\\Lucene\\index";
        // 创建文档目录对象,关联索引库
        FSDirectory directory = FSDirectory.open(new File(path));
        // 读取索引库索引
        DirectoryReader reader = DirectoryReader.open(directory);
        // 查询索引库核心对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 搜索关键词
        String qname = "黑马程序员";
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建查询解析器
        QueryParser parser =  new QueryParser(Version.LUCENE_4_10_3, "title", new StandardAnalyzer());
        // 关键词分词
        Query query = parser.parse(qname);
        // 文档概要对象
        TopDocs topDocs = searcher.search(query,10);
        // 命中总记录
        int totalHits = topDocs.totalHits;
        System.out.println("命中总记录:"+totalHits);

        // 文档得分
        float maxScore = topDocs.getMaxScore();
        System.out.println("文档最高得分:"+maxScore);

        // 文档得分数组
        ScoreDoc[] docs = topDocs.scoreDocs;
        for(ScoreDoc doc : docs){
            float score = doc.score;
            System.out.println("文档得分:"+score);
            int docId = doc.doc;
            System.out.println("文档id:"+docId);
            // 根据文档id获取文档对象
            Document document = searcher.doc(docId);
            // 获取域字段
            String id = document.get("id");
            System.out.println("文档域id:"+id);
            System.out.println("文档标题:"+document.get("title"));
            System.out.println("文档描述:"+document.get("desc"));
            System.out.println("文档内容:"+document.get("content"));
        }

    }
}
