package com.bytedance.project.service;

import com.bytedance.project.common.BaseResponse;
import com.bytedance.project.model.entity.PageContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bytedance.project.model.request.ArticleRequest;

/**
* @author 86155
* @description 针对表【page_content】的数据库操作Service
* @createDate 2022-07-28 21:59:00
*/
public interface PageContentService extends IService<PageContent> {

    /**
     * 分页
     * @param page 页码
     * @param size  数量
     * @return
     */
    BaseResponse search(long page, long size);

    /**
     * 发布文章
     */
    long postArticle(ArticleRequest articleRequest);
}
