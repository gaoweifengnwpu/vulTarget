package com.gwf.vul.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//https://cloud.tencent.com/developer/article/2321287?areaId=106005
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "blogs", createIndex = true)
@Data
//@JsonIgnoreProperties(value = {"id"}, allowSetters = true)
public class BlogIndex {
    // org.springframework.data.annotation.Id
    private String id;          // 文章ID

    // 指定字段对应的ES类型是Text，analyzer指定分词器为ik_max_word
    @Field(type = FieldType.Text)
    private String author;       // 文章作者

    @Field(type = FieldType.Text)
    private String title;        // 文章标题

    @Field(type = FieldType.Text)
    private String content;      // 文章正文内容
    //
//    // 指定字段对应ES中的类型是Date，使用自定义的日期格式化，pattern指定格式化
//    // 规则是“日期时间”或“日期”或“时间毫秒”
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;     // 文章创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;     // 文章更新时间
}