package org.example.reggie.controller;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultStatus;
import org.example.reggie.common.ResultViewEntity;
import org.example.reggie.exceptions.SystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Slf4j
@Api("文件上传与下载")
@RestController
@RequestMapping("common")
public class CommonController {

    //获取配置路径
    @Value("${reggie.path}")
    private String basePath;

    @ApiOperation("文件上传接口")
    @PostMapping("/upload")
    public ResultViewEntity<String> upload(
            @ApiParam("文件上传") @RequestPart MultipartFile file
    ) {

        log.info("请求格式: {} ,地址: {}","POST","/common/upload");
        //获取图片全名称
        String photo = file.getOriginalFilename();
        //判断是否没有图片
        if (ObjectUtil.isNull(photo)) {
            return ResultViewEntity.error("您的照片为空");
        }
        //生成随机图片名称
        photo = UUID.randomUUID() + photo.substring(photo.lastIndexOf("."));
        //判断路径是否存在
        File filePath = new File(basePath);
        if (!filePath.exists()) {
            //创建多级目录
            filePath.mkdirs();
        }
        //路径拼接
        String realPath = basePath + photo;
        System.out.println("路径:" + realPath);

        //进行图片存储
        try {
            file.transferTo(new File(realPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResultViewEntity.successful(photo);
    }

    @ApiOperation("图片下载接口")
    @GetMapping("/download")
    public void download(
         @ApiParam("图片名称") String name,
         HttpServletResponse response
    ) {

        log.info("请求格式: {} ,地址: {}","GET","/common/download");
        //声明字符集
        response.setContentType("*/*;charset=utf-8");
        //判断当前图片是否存在,防止服务器出现问题
        File file = new File(basePath + name);
        if (!file.exists()) {
            throw new SystemException(
                    "抱歉您的图片不存在!!",
                    ResultStatus.Empty_VAR_ERROR
            );
        }
        //建立输出流管道
        try(
            //输入流
            InputStream is = new FileInputStream(basePath+name);
            BufferedInputStream bis = new BufferedInputStream(is);
            OutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os)
        ) {
            //数据读取
            int len;
            byte[] buffer = new byte[bis.available()];
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer,0,len);
            }

        } catch (Exception e) {
            throw new SystemException(
                    "系统IO异常🤗🤗",
                    ResultStatus.IO_ERROR
            );
        }
    }
}
