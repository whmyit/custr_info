package com.myd.controller;



import com.myd.domain.Param;
import com.myd.service.FindCardService;
import com.myd.utils.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/findCard")
public class FindCardController {

    @Autowired
    private FindCardService findCardService;


    @PostMapping("/xiaoYun")
    public ServiceResponse findCard(Param param) {
        return  findCardService.findCard(param);
    }


}
