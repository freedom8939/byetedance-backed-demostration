package com.bytedance.project.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.project.common.BaseResponse;
import com.bytedance.project.common.ErrorCode;
import com.bytedance.project.common.ResultUtils;
import com.bytedance.project.model.entity.PageContent;
import com.bytedance.project.model.request.ArticleRequest;
import com.bytedance.project.service.PageContentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/content")
public class PageContentController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PageContentService pageContentService;

    /**
     * 通过id获取文章详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse getDetailedArticleById(@PathVariable("id") Long id){
        //如果小于等于 0 非法数据
        if(id <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(),"非法请求","");
        }
        PageContent content = pageContentService.getById(id);
        //如果未查询到数据
        if(content == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(),
                    "未获取到数据","");
        }
        //查询到数据
        return ResultUtils.success(content);
    }

    /**
     * 分页获取文章列表
     */
    @GetMapping("/list/search/{page}/{size}")
    public BaseResponse search(@PathVariable("page")long page,
                                @PathVariable("size")long size){
        logger.info("page={},size={}",page,size);
        return pageContentService.search(page,size);
    }

    /**
     * 发布文章
     */
    @PostMapping
    public BaseResponse postArticle(@RequestBody ArticleRequest articleRequest){
//        return pageContentService.postArticle(articleRequest);
        return ResultUtils.success( pageContentService.postArticle(articleRequest)+"");
    }

    /**
     * 根据id删除文章
     */

    @DeleteMapping("/{id}")
    public BaseResponse deleteArticleById(@PathVariable("id")long id){
        if(id < 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"请求参数非法");
        }
        boolean b = pageContentService.removeById(id);
        if(!b){
            //删除失败
            return ResultUtils.error(ErrorCode.DELETE_ERROR);
        }
        return ResultUtils.success(b);
    }

    /**
     * 传入多个id  查询出文章
     * @return
     */
    @PostMapping("/getArticlesByIds")
    public BaseResponse getArticlesByIds(@RequestBody String searchParams){
        // 只匹配数字
        String regex = "[^0-9]";
        // 只有数字的数组
        String[] searchList = searchParams.split(regex);
        List<String> collect = Arrays.stream(searchList).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList());
//        System.out.println(collect);
        // 查询数据库
        List<PageContent> pageContents = pageContentService.getBaseMapper().selectBatchIds(collect);
        //查看返回值
//        System.out.println(pageContents);
        //如果为0说明未查询到
        if(pageContents.size() == 0){
            return ResultUtils.error(ErrorCode.NULL_ERROR,"未查询到数据");
        }
        //查询成功 返回
        return ResultUtils.success(pageContents);
    }

    /**
     * 更新文章
     */
    @PutMapping
    public BaseResponse update(@RequestBody PageContent pageContent){
        //如果ctime被更改 我们直接返回报错
        if(pageContent.getCtime() != null){
            return ResultUtils.error(ErrorCode.UPDATE_ERROR,"创建时间禁止更改");
        }
        // 无需传入mtime参数
        if(pageContent.getMtime() != null){
            return ResultUtils.error(ErrorCode.UPDATE_ERROR,"无需修改mtime字段");
        }
        //获取当前时间戳
        String time = Long.toString(System.currentTimeMillis()/1000L);
        pageContent.setMtime(Integer.valueOf(time));
        boolean b = pageContentService.updateById(pageContent);
        // false condition
        if(!b){
            return ResultUtils.error(ErrorCode.UPDATE_ERROR);
        }
        return ResultUtils.success(b);
    }
}
