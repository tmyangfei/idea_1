package com.itheima.crud;

import com.itheima.utils.QueryParserUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 19:09
 * @Description:    更新索引库
 */

public class Func_ {
    // 添加
    @Test
    public void addIndex()throws Exception{
        String path = "d:/develop/lucene/index";
        FSDirectory directory = FSDirectory.open(new File(path));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        Document document = new Document();
        document.add(new StringField("id","A_"+20, Field.Store.YES));
        TextField field = new TextField("title", "黄晓明在传智播客学习java,lucene经典教程", Field.Store.YES);
        document.add(field);
        document.add(new TextField("desc", " Lucene是apache软件基金会4 jakarta项目组的一个子项目," +
                "是一个开放源代码的全文检索引擎工具包," +
                "但它不是一个完整的全文检索引擎,而是一个全文检索引擎", Field.Store.YES));
        document.add(new TextField("content", "Lucene是一个非常优秀的开源的全文搜索引擎;" +
                " 我们可以在它的上面开发出各种全文搜索的应用来。" +
                "Lucene在国外有很高的知名度; 现在已经是Apache的顶级项目; 在国内", Field.Store.NO));
        //写索引库
        indexWriter.addDocument(document);
        indexWriter.commit();
    }

    // 查询
    @Test
    public void search()throws Exception{
        String keyword = "传智播客";
        QueryParser parser = new QueryParser(Version.LUCENE_4_10_3,"title",new IKAnalyzer());
        Query parse = parser.parse(keyword);
        QueryParserUtils.indexSearch(parse);
    }

    // 更新
    @Test
    public void updateIndex()throws Exception{
        String path = "d:/develop/lucene/index";
        FSDirectory directory = FSDirectory.open(new File(path));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        IndexWriter writer = new IndexWriter(directory,config);
        Document doc = new Document();

        doc.add(new StringField("id", "A_20" , Field.Store.YES));
        TextField field = new TextField("title", "黄晓明在传智播客学习java,lucene经典教程,凤姐在传智播客学习ui", Field.Store.YES);
        doc.add(field);
        //描述
        doc.add(new TextField("desc", " Lucene是apache软件基金会4 jakarta项目组的一个子项目," +
                "是一个开放源代码的全文检索引擎工具包," +
                "但它不是一个完整的全文检索引擎,而是一个全文检索引擎", Field.Store.YES));
        //内容
        //TextField： 分词，索引，Store.NO
        doc.add(new TextField("content", "Lucene是一个非常优秀的开源的全文搜索引擎;" +
                " 我们可以在它的上面开发出各种全文搜索的应用来。" +
                "Lucene在国外有很高的知名度; 现在已经是Apache的顶级项目; 在国内", Field.Store.NO));
        // 更新索引库
        /*
            更新原理 ;
                先根据id查询出其对应的文档对象,再将之删除,之后再将新的doc对象添加到索引库
         */
        writer.updateDocument(new Term("id","A_20"),doc);
        // 提交
        writer.commit();
    }

    // 删除
    @Test
    public void deleteIndex()throws Exception{
        String path = "d:/develop/lucene/index";
        FSDirectory directory = FSDirectory.open(new File(path));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        IndexWriter writer = new IndexWriter(directory,config);
        // 删除
        // writer.deleteDocuments(new Term("title","传智播客"));
        // 删除所有
        writer.deleteAll();
        // 提交
        writer.commit();
    }
}
