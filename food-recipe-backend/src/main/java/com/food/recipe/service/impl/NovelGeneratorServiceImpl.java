package com.food.recipe.service.impl;

import com.food.recipe.dto.NovelGenerateRequest;
import com.food.recipe.service.NovelGeneratorService;
import com.food.recipe.vo.NovelGenerateResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 基于模板的第一人称小说生成
 */
@Service
public class NovelGeneratorServiceImpl implements NovelGeneratorService {

    @Override
    public NovelGenerateResponse generateFirstPersonNovel(NovelGenerateRequest request) {
        String background = normalize(request.getBackground(), "一个未尽描绘的世界");
        String name = normalize(request.getProtagonistName(), "我");
        String trait = normalize(request.getProtagonistTrait(), "普通却执拗");
        String genre = normalize(request.getGenre(), "故事");
        String plotHint = StringUtils.defaultIfBlank(request.getPlotHint(), "我在寻找属于自己的答案");

        List<String> keywords = Optional.ofNullable(request.getKeywords())
                .orElse(Collections.emptyList())
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> paragraphs = new ArrayList<>();
        paragraphs.add(buildOpening(background, name, trait, genre));
        paragraphs.add(buildRisingAction(background, genre, plotHint, keywords));
        paragraphs.add(buildClimaxAndReflection(name, trait, keywords));

        NovelGenerateResponse response = new NovelGenerateResponse();
        response.setTitle(String.format("第一章 · %s的序幕", genre));
        response.setParagraphs(paragraphs);
        response.setContent(String.join("\n\n", paragraphs));
        return response;
    }

    private String buildOpening(String background, String name, String trait, String genre) {
        return String.format("在%s，我叫%s，这段时间我一直以%s的姿态生活着。这里的气息在空气里回旋，每一次呼吸都像是在为未知的%s做准备。",
                background, name, trait, genre);
    }

    private String buildRisingAction(String background, String genre, String plotHint, List<String> keywords) {
        StringBuilder sb = new StringBuilder();
        sb.append("我常常在心里反复描摹那些细节：").append(plotHint).append("。");
        if (!keywords.isEmpty()) {
            String joinedKeywords = String.join("、", keywords);
            sb.append("关于").append(joinedKeywords).append("的线索在").append(background).append("里若隐若现地游移，");
            sb.append("它们勾起了我内心深处的悸动，也让整个").append(genre).append("显得更真实。");
        } else {
            sb.append("我把零碎的情绪一点点缝合，让这个").append(genre).append("逐渐成形。");
        }
        sb.append("我能感觉到节奏在推进，仿佛每一步都把我推向某个无可回头的节点。");
        return sb.toString();
    }

    private String buildClimaxAndReflection(String name, String trait, List<String> keywords) {
        StringBuilder sb = new StringBuilder();
        sb.append("当高潮逼近时，我听见自己的心跳，与脚步声重合。");
        if (!keywords.isEmpty()) {
            sb.append("那些关于").append(String.join("、", keywords)).append("的暗示纷纷浮现，");
        }
        sb.append("让我不得不直面内心的恐惧与渴望。");
        sb.append("我提醒自己：我是").append(name).append("，也是那个拥有").append(trait).append("灵魂的人，");
        sb.append("无论前方是光还是深渊，我都要用第一人称的声音讲完这个故事。");
        return sb.toString();
    }

    private String normalize(String value, String fallback) {
        return StringUtils.defaultIfBlank(StringUtils.trim(value), fallback);
    }
}
