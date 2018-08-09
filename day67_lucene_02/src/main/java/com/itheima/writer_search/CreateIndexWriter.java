package com.itheima.writer_search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: yangfei
 * @Date: 2018/8/4 15:28
 * @Description:
 */

public class CreateIndexWriter {

    @Test
    public void createIndex()throws Exception{
        String path = "D:/develop/lucene/index";
        // 创建目录对象,关联索引磁盘存储位置
        FSDirectory directory = FSDirectory.open(new File(path));
        // 创建第三方ik分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建写索引库核心配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建写索引库对象
        IndexWriter writer = new IndexWriter(directory,config);

        for (int i = 0; i < 30; i++) {
            // 创建文档对象
            Document document = new Document();
            // 添加域字段
            document.add(new LongField("id",i, Field.Store.YES));
            document.add(new TextField("title","XXX正在传智播客学习java,Lucene经典教程",Field.Store.YES));
            document.add(new TextField("desc","Lucene是apache下的一个开放源代码的全文检索引擎工具包", Field.Store.YES));
            document.add(new TextField("content","Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品",Field.Store.NO));
            writer.addDocument(document);
        }
        writer.commit();

    }
}
