package com.itheima.demo;

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
 * @Date: 2018/8/3 19:42
 * @Description:
 */

public class CreateIndex_ {

    @Test
    public void create()throws Exception{
        // 指定索引库磁盘存储路径
        String path = "D:\\develop\\Lucene\\index";
        // 创建目录对象,关联索引库磁盘存储路径
        FSDirectory directory = FSDirectory.open(new File(path));
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 核心配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建文档对象
        Document doc = new Document();
        // 添加域字段
        doc.add(new StringField("id","A001", Field.Store.NO));
        doc.add(new TextField("title","黑马程序员",Field.Store.YES));
        doc.add(new TextField("desc","此处省略......",Field.Store.YES));
        doc.add(new TextField("content","详情请登录:itheima.com",Field.Store.NO));
        // 创建写索引库核心对象
        IndexWriter writer = new IndexWriter(directory,config);
        // 将文档对象写入索引库
        writer.addDocument(doc);
        // 提交
        writer.commit();
        // 释放资源
        writer.close();


    }
}
