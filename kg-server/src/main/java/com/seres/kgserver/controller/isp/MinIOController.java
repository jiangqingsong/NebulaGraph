package com.seres.kgserver.controller.isp;

import com.seres.base.Resp;
import com.seres.kgserver.service.isp.FileService;
import com.seres.kgserver.vo.isp.BatchDownloadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/2 8:43
 */

@RestController
@RequestMapping("/minio")
public class MinIOController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Resp<String> upload(MultipartFile file) {
        fileService.upload(file);
        return new Resp<>("文件上传成功");
    }

    @GetMapping("/download")
    public void download(@RequestParam String fileName, HttpServletResponse response) {
        fileService.download(fileName, response);
    }

    @PostMapping("/batchDownload")
    public void batchDownload(@RequestBody() BatchDownloadVO batchDownload, HttpServletResponse res, HttpServletRequest req) {
        fileService.batchDownload(batchDownload.getFileNames(), batchDownload.getZipName(), res, req);
    }
}
