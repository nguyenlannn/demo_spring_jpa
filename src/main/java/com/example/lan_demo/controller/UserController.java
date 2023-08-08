package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.dto.res.PageRes;
import com.example.lan_demo.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService mUserService;

    @GetMapping("/DetailUser")
    public BaseResponse getDetailUser(){
        return BaseResponse.success(mUserService.getDetailUser());
    }

    @GetMapping("/listUser")
    public BaseResponse getListUser(@RequestParam String name){
        return BaseResponse.success(mUserService.getListUser(name));
    }

    @GetMapping("/pagingByName")
    public PageRes PagingUserByName(@RequestParam Long pageNo,
                                    @RequestParam Long pageSize,
                                    @RequestParam String name){
        return mUserService.getPageUserByName(pageNo,pageSize,name);
    }

    @GetMapping("/paging")
    public PageRes Paging(@RequestParam Long pageNo,
                                    @RequestParam Long pageSize){
        return mUserService.getPage(pageNo,pageSize);
    }

    @GetMapping("/basic")
    public void test(){
        mUserService.testJoin();
    }
}
