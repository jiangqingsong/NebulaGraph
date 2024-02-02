package com.seres.kgserver.controller;

import com.seres.base.Resp;
import com.seres.kgserver.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/2 8:43
 */

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/test")
    public Resp<String> test()  {
        return new Resp<>(playerService.testPlayerApi());
    }
}
