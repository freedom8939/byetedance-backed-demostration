package com.bytedance.project.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleRequest implements Serializable {

    /**
     * id
     */
    @TableId(value = "article_id")
    private String articleId;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String brief;

    /**
     * 文章作者名字
     */
    @TableField("user_name")
    private String userName;

    /**
     * 封面
     */
    @TableField("cover_image")
    private String coverImage;

    /**
     * 头像
     */
    private String avatar;


    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Integer ctime;

    /**
     * 最近一次被修改
     */
    private Integer mtime;
    private static final long serialVersionUID = -1198665221054323023L;
}
