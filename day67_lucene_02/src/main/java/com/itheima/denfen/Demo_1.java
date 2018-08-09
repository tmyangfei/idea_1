package com.itheima.denfen;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 18:54
 * @Description:    需求：搜索引擎开发时候，
 *              有些文档需求手动设置得分，以便于此文档排名更靠前。
 */

public class Demo_1 {

    @Test
    public void addIndex()throws Exception{
        String path = "d:/develop/lucene/index";
        FSDirectory directory = FSDirectory.open(new File(path));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        for (int i = 0; i < 30; i++) {
            Document document = new Document();
            document.add(new StringField("id","A_"+i, Field.Store.YES));
            TextField field = new TextField("title", "黄晓明在传智播客学习java,lucene经典教程", Field.Store.YES);
            if(i==20){
                field.setBoost(1000);
            }
            document.add(field);
            document.add(new TextField("desc", " Lucene是apache软件基金会4 jakarta项目组的一个子项目," +
                    "是一个开放源代码的全文检索引擎工具包," +
                    "但它不是一个完整的全文检索引擎,而是一个全文检索引擎", Field.Store.YES));
            document.add(new TextField("content", "Lucene是一个非常优秀的开源的全文搜索引擎;" +
                    " 我们可以在它的上面开发出各种全文搜索的应用来。" +
                    "Lucene在国外有很高的知名度; 现在已经是Apache的顶级项目; 在国内", Field.Store.NO));
            //写索引库
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
    }
}
