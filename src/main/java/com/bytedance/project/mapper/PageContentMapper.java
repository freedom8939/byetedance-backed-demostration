package com.bytedance.project.mapper;

import com.bytedance.project.common.BaseResponse;
import com.bytedance.project.model.entity.PageContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.project.model.request.ArticleRequest;
import org.apache.ibatis.annotations.Param;

/**
* @author 86155
* @description 针对表【page_content】的数据库操作Mapper
* @createDate 2022-07-28 21:59:00
* @Entity com.bytedance.project.model.entity.PageContent
*/
public interface PageContentMapper extends BaseMapper<PageContent> {
    long postArticle(@Param("article") ArticleRequest articleRequest);

}




