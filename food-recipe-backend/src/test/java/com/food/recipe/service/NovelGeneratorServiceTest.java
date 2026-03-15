package com.food.recipe.service;

import com.food.recipe.dto.NovelGenerateRequest;
import com.food.recipe.service.impl.NovelGeneratorServiceImpl;
import com.food.recipe.vo.NovelGenerateResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NovelGeneratorServiceTest {

    private final NovelGeneratorService service = new NovelGeneratorServiceImpl();

    @Test
    void shouldGenerateFirstPersonNarrativeWithKeywords() {
        NovelGenerateRequest request = new NovelGenerateRequest();
        request.setBackground("废墟城市");
        request.setProtagonistName("阿黎");
        request.setProtagonistTrait("顽强且敏感");
        request.setGenre("悬疑");
        request.setPlotHint("我在追踪失踪朋友留下的线索");
        request.setKeywords(Arrays.asList("旧日记", "雾气", "远方的车站"));

        NovelGenerateResponse response = service.generateFirstPersonNovel(request);

        assertNotNull(response);
        assertNotNull(response.getContent());
        String content = response.getContent();

        assertTrue(content.contains("我"), "内容应使用第一人称叙述");
        assertTrue(content.contains("阿黎"), "应包含主角姓名");
        assertTrue(content.contains("废墟城市"), "应包含背景信息");
        assertTrue(content.contains("旧日记"), "应包含关键词");
        assertTrue(content.contains("雾气"), "应包含关键词");
        assertTrue(content.contains("远方的车站"), "应包含关键词");
        assertNotNull(response.getParagraphs(), "应返回分段内容");
        assertEquals(3, response.getParagraphs().size(), "默认应生成三个段落");
    }
}
