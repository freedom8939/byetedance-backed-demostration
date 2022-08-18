package com.bytedance.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bytedance.project.common.BaseResponse;
import com.bytedance.project.common.ErrorCode;
import com.bytedance.project.common.ResultUtils;
import com.bytedance.project.exception.BusinessException;
import com.bytedance.project.model.entity.PageContent;
import com.bytedance.project.model.request.ArticleRequest;
import com.bytedance.project.service.PageContentService;
import com.bytedance.project.mapper.PageContentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【page_content】的数据库操作Service实现
* @createDate 2022-07-28 21:59:00
*/
@Service
public class PageContentServiceImpl extends ServiceImpl<PageContentMapper, PageContent>
    implements PageContentService{

    @Override
    public BaseResponse search(long page, long size) {
        //如果page 或 size 为空 或者 小于 0  参数错误
        if(page <= 0 || size <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        IPage<PageContent> p = new Page<>(page, size);
        IPage<PageContent> data = baseMapper.selectPage(p, null);
        return ResultUtils.success(data);
    }

    /**
     * 发布文章
     * @param article 文章请求体
     * @return
     */
    @Override
    public long postArticle(ArticleRequest article) {

        // 如果请求体是空
        if (article == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");

        }

        if (StringUtils.isAnyBlank(article.getUserName(), article.getContent(), article.getTitle())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        /**
         * TODO
         * 如果封面，简介， 头像为空 我们使用默认的头像
         */

        //数据库ctime, mtime存在遗留问题，我们第一次默认时间为当前时间
        //获取unix时间戳
        String time = Long.toString(System.currentTimeMillis()/1000L);

        article.setCtime(Integer.valueOf(time));
        article.setMtime(Integer.valueOf(time));

        //校验通过  存在用户名，存在文章列表 即可发表

        //给id一个值！随机 19位 雪花算法
        String id = IdWorker.getIdStr();
        article.setArticleId(id);

        long saveResult = baseMapper.postArticle(article);
        if(saveResult <= 0 ){
            throw new  BusinessException(ErrorCode.SAVE_ERROR,"新增失败");
        }
        //返回id
        return Long.parseLong(article.getArticleId());

    }
}




