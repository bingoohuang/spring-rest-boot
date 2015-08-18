package com.github.bingoohuang.springrest.boot.controller;

import com.github.bingoohuang.asmvalidator.annotations.AsmMessage;
import com.github.bingoohuang.asmvalidator.annotations.AsmRegex;
import com.github.bingoohuang.asmvalidator.annotations.AsmValid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DemoController {
    @AsmValid
    @RequestMapping(value = "/hello",
            method = GET,
            produces = "application/json")
    public String hello(@RequestParam("name") String name) {
        return "Hello " + name;
    }

    @AsmValid
    @RequestMapping(value = "/district", method = GET)
    String getAreaByDistrict(
            @RequestParam("districtCode")
            @AsmRegex("^(?:[1-9][0-9]{5})$") @AsmMessage("区县编码必须是6位数字")
            String districtCode) {

        return "area:" + districtCode;
    }
}
