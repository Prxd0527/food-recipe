package com.food.recipe.service;

import com.food.recipe.dto.NovelGenerateRequest;
import com.food.recipe.vo.NovelGenerateResponse;

/**
 * 小说生成服务
 */
public interface NovelGeneratorService {

    /**
     * 生成第一人称视角的小说片段
     * @param request 用户提供的背景、角色信息、类型与关键词
     * @return 生成的小说文本
     */
    NovelGenerateResponse generateFirstPersonNovel(NovelGenerateRequest request);
}
