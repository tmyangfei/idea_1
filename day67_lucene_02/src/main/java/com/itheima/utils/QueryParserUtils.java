package com.itheima.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 16:20
 * @Description:    查询索引库工具类
 *  查询原则 : 使用关键词匹配索引库最小索引单元
 */

public class QueryParserUtils {

    public static void indexSearch(Query query)throws Exception{
        String path = "d:/develop/lucene/index";
        FSDirectory fsDirectory = FSDirectory.open(new File(path));
        // 读取索引库索引
        DirectoryReader reader = DirectoryReader.open(fsDirectory);
        // 创建查询索引库核心对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 创建文档概要对象
        TopDocs topDocs = searcher.search(query,Integer.MAX_VALUE);
        // 获取文档命中总记录数
        int totalHits = topDocs.totalHits;
        System.out.println("文档命中总记录数为:"+totalHits);
        // 获取文档得分数组
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc scoreDoc : scoreDocs){
            // 获取文档得分
            float score = scoreDoc.score;
            System.out.println("文档得分:"+score);
            // 获取文档id
            int id = scoreDoc.doc;
            System.out.println("文档id:"+id);
            // 根据文档id获取文档对象
            Document document = searcher.doc(id);
            // 获取域字段
            String fieldId = document.get("id");
            System.out.println("文档域字段id:"+fieldId);
            String title = document.get("title");
            System.out.println("文档标题:"+title);
            String desc = document.get("desc");
            System.out.println("文档描述:"+desc);
            String content = document.get("content");
            System.out.println("文档内容:"+content);
        }

    }
}
