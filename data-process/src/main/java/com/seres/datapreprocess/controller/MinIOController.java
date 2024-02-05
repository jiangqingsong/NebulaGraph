package com.seres.datapreprocess.controller;

import com.seres.base.Resp;
import com.seres.datapreprocess.service.MinIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/2 8:43
 */

@RestController
@RequestMapping("/file")
public class MinIOController {

    @Autowired
    private MinIOService minIOService;

    @PostMapping("/uploadtest")
    @ResponseBody
    public Resp<String> uploadtest()  {
        minIOService.uploadFile();
        return new Resp<>("upload file ok!");
    }
}
