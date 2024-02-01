package com.seres.datapreprocess.controller;

import com.seres.base.ErrResp;
import com.seres.base.Resp;
import com.seres.datapreprocess.common.ResultUtil;
import com.seres.datapreprocess.enums.ResultEnum;
import com.seres.datapreprocess.service.DataPreprocessService;
import com.seres.datapreprocess.vo.MongoDataTransVO;
import com.seres.datapreprocess.vo.MysqlDataTransVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 数据预处理controller类
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/25 10:00
 */
@RestController
@RequestMapping("/data-preprocess")
public class DataPreprocessController {

    @Autowired
    private DataPreprocessService dataPreprocessService;

    @PostMapping("/mysql2csv")
    @ResponseBody
    public Resp<String> mysql2csv(@RequestBody MysqlDataTransVO mysqlDataTransVO)  {
        boolean isOk = dataPreprocessService.mysql2csv(mysqlDataTransVO);
        if(isOk){


            return new Resp<>("mysql转csv成功！");

        }else {
            return new ErrResp("mysql转csv失败！");
        }
    }

    @GetMapping("/hello")
    public Resp<String> hello(){
        return new Resp<>("hello");
    }


    @PostMapping("/mongo2csv")
    @ResponseBody
    public ResponseEntity<String> mongo2csv(@RequestBody MongoDataTransVO vo)  {
        boolean isOk = dataPreprocessService.mongo2csv(vo);
        if(isOk){
            return new ResponseEntity(ResultUtil.success("mongo转csv成功！"), HttpStatus.OK);
        }else {
            return new ResponseEntity(ResultUtil.error(ResultEnum._FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
