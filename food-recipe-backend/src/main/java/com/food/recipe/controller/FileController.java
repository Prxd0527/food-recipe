package com.food.recipe.controller;

import com.food.recipe.common.Constants;
import com.food.recipe.common.Result;
import com.food.recipe.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传控制器
 * @author prxd0527
 * @date 2025-12-07
 */
@Slf4j
@RestController
@RequestMapping("/api/upload")
@Api(tags = "文件上传")
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;

    @PostMapping("/image")
    @ApiOperation("上传图片")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("请选择要上传的文件");
        }

        // 获取原始文件名和扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);

        // 验证文件类型
        if (!isValidImageExtension(extension)) {
            throw new BusinessException("只支持jpg、jpeg、png、gif格式的图片");
        }

        // 验证文件大小
        if (file.getSize() > Constants.MAX_FILE_SIZE) {
            throw new BusinessException("文件大小不能超过10MB");
        }

        try {
            // 生成文件名：UUID + 扩展名
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + extension;

            // 按日期创建子目录：images/yyyy/MM/dd/
            LocalDate now = LocalDate.now();
            String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dirPath = uploadPath + Constants.IMAGE_DIR + "/" + datePath + "/";

            // 创建目录
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File destFile = new File(dirPath + filename);
            file.transferTo(destFile);

            // 返回访问URL
            String imageUrl = "/upload/" + Constants.IMAGE_DIR + "/" + datePath + "/" + filename;
            log.info("图片上传成功：{}", imageUrl);

            return Result.success("上传成功", imageUrl);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    /**
     * 验证图片扩展名
     */
    private boolean isValidImageExtension(String extension) {
        if (extension == null) {
            return false;
        }
        String lowerExtension = extension.toLowerCase();
        for (String allowed : Constants.ALLOWED_EXTENSIONS) {
            if (allowed.equals("." + lowerExtension)) {
                return true;
            }
        }
        return false;
    }

}
