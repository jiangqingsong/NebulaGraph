package com.seres.datapreprocess.controller;

import com.seres.datapreprocess.common.ResultUtil;
import com.seres.datapreprocess.enums.ResultEnum;
import com.seres.datapreprocess.service.DataPreprocessService;
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
    public ResponseEntity<String> mysql2csv(@RequestBody MysqlDataTransVO mysqlDataTransVO)  {
        boolean isOk = dataPreprocessService.mysql2csv(mysqlDataTransVO);
        if(isOk){
            return new ResponseEntity(ResultUtil.success("mysql转csv成功！"), HttpStatus.OK);
        }else {
            return new ResponseEntity(ResultUtil.error(ResultEnum._FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
