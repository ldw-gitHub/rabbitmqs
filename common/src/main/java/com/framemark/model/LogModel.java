package com.framemark.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @description index： 索引；索引名称必须是小写，不能用下划线开头，不能包含逗号：product，website，blog
 * document： 类似的数据放在一个索引，非类似的数据放不同索引
 * type： 一个索引通常会划分为多个type，逻辑上对index中有些许不同的几类数据进行分类；type名称可以是大写或者小写，但是同时不能用下划线开头
 * id元数据解析：代表document的唯一标识，id与index和type一起，可以唯一标识和定位一个document
 * 我们可以手动指定document的id（put/index/type/id）。也可以不指定，由es自动为我们创建一个id
 * 手动：指通常我们导入已有数据，适合采用数据在数据库中已有的primary key
 * 自动：自动生长的id，长度为20个字符，URL安全，base64编码，GUID，分布式系统并行生成时不可能发生冲突
 * _source元数据：就是说，我们在创建一个document的时候，使用的那个放在request body中的json串（所有的field），默认情况下，在get的时候，会原封不动的返回
 * <p>
 * es7.X版本后，为什么要移除type？
 * 1、之前es将index、type类比于关系型数据库中database、table，这么考虑的目的是方便管理数据之间的关系
 * 2、为什么要移除type：在关系型数据库中table是独立的（独立存储），但在es中同一个index中不同type是存储在同一个索引中的（lucene的索引文件）
 * 因此不同type中相同名字的字段的定义（mapping）必须一致
 * 不同类型的记录存储在同一个index中，会影响lucene的压缩性能
 * 3、替换策略
 * 3.1：一个index只存储一种类型的记录；lucene索引中数据比较齐整，利用lucene进行压缩；文本相关性打分更加精确（tf、idf考虑idf中命中文档总数）
 * 3.2：用一个字段来存储type。优点es集群对分片数量有限制，这种方案可以减少index的数量
 * <p>
 * 4、迁移方案
 * 之前一个index上有多个type，如何迁移到3.1、3.2方案
 * 4.1：先针对实际情况创建新的index，3.1方案有多少个type就需要创建多少个新的index，3.2方案只需要创建一个新的index
 * 4.2：调用_reindex将之前index上的数据同步到新的索引上
 * @author: liudawei
 * @date: 2020/12/16 14:33
 */

@Document(indexName = "operate_log")
public class LogModel {
    @Id
    @Field(type = FieldType.Text)
    public String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    public String logType;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    public String operateName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    public String context;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public LogModel(String id, String logType, String operateName, String context, Date createTime) {
        this.id = id;
        this.logType = logType;
        this.operateName = operateName;
        this.context = context;
        this.createTime = createTime;
    }

    public LogModel(String logType) {
        this.logType = logType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
