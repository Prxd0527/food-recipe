package com.food.recipe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 美食食谱分享系统 - 启动类
 * @author prxd0527
 * @date 2025-12-07
 */
@SpringBootApplication
@MapperScan("com.food.recipe.mapper")
public class FoodRecipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodRecipeApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("美食食谱分享系统启动成功！");
        System.out.println("API文档地址：http://localhost:8080/doc.html");
        System.out.println("========================================\n");
    }

}
