package com.cob.backend.controller.pk;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {

    @RequestMapping("getbotinfo/")
    public Map<Integer, String> getBotInfo() {
        Map<Integer, String> map = new HashMap<>();
        map.put(10086, "Jialem");
        map.put(10000, "Xul");
        return map;
    }
}