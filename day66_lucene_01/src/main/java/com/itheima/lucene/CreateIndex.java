package com.itheima.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
 * @Date: 2018/8/3 16:01
 * @Description:
 */

public class CreateIndex {

    // 创建索引
    @Test
    public void createIndex() throws Exception{
        // 指定索引库索引磁盘存储路径
        String path = "D:\\develop\\Lucene\\index";
        // 创建目录对象,关联索引磁盘存储位置
        FSDirectory directory = FSDirectory.open(new File(path));
        // 1.创建基本分词器对象
        // 特点 : 一个汉字一个词语,一个英文单词一个词语
        // 单词词典都是通过分词器进行切词,然后再保存的
        // Analyzer analyzer = new StandardAnalyzer();

        // 2. Lucene自带CJK分词器
        // 特点 : 汉字的两两组合是一个词语,两两组合后是否是一个词语不管; 一个英文单词一个词语
        // Analyzer analyzer = new CJKAnalyzer();

        // 3. 聪明的中国人分词器 : smartChinese
        // 特点 : 汉字正常分词,但是英文单词缺少字母
        // Analyzer analyzer = new SmartChineseAnalyzer();

        // 4. 创建第三方中文分词器: ik分词器
        Analyzer analyzer = new IKAnalyzer();

        // 创建写索引库核心配置对象: 参数 : Lucene版本,分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建写索引库核心对象
        IndexWriter indexWriter = new IndexWriter(directory,config);
        /*
            文档对象数据来源
                网页 : id,title,desc,content
                文件 : id,title,desc,content
                数据库中一条记录 : article : id,title,desc,content,createTime,updateTime
            抽取数据变成文档对象 :
                文档对象数据结构 : 文档对象存储数据是根据域字段来进行存储,类似于数据库存储数据
         */
        // 创建文档对象
        Document doc = new Document();
        // 添加id字段 : 文档编号
        // StringField:域字段类型,类似于数据库varchar
        // 特点 :
        //      不分词,类型是StringField的value值不会被分词器解析,
        //      有索引
        //Field.Store.NO存储原则 :  搜索的结果如果不在页面展示,那就不存储
        doc.add(new StringField("id","A001", Field.Store.NO));
        // 添加标题
        // TextField : 域字段类型
        // 特点 :
        //      分词 , TextField域类型的值一定会分词
        //      有索引
        doc.add(new TextField("title","渣渣辉来玩贪玩蓝月!,Lucene经典教程",Field.Store.YES));
        // 描述
        doc.add(new TextField("desc","Lucene是apache下的一个开放源代码的全文检索引擎工具包。" +
                "提供了完整的查询引擎和索引引擎，部分文本分析引擎。" +
                "Lucene的目的是为软件开发人员提供一个简单易用的工具包，" +
                "以方便的在目标系统中实现全文检索的功能。",Field.Store.YES));
        // 内容
        // 内容TextField类型: 内容必须分词,有索引,但是数据一般过大,不存储
        doc.add(new TextField("content","Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品",Field.Store.NO));
        // 写索引库
        indexWriter.addDocument(doc);
        // 提交
        indexWriter.commit();
        indexWriter.close();
    }
}
