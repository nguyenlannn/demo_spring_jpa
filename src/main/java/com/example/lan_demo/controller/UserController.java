package com.example.lan_demo.controller;

import com.example.lan_demo.base.BaseResponse;
import com.example.lan_demo.dto.res.PageRes;
import com.example.lan_demo.enums.UserEnum;
import com.example.lan_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService mUserService;

    @GetMapping("/DetailUser")
    public BaseResponse getDetailUser() {
        return BaseResponse.success(mUserService.getDetailUser());
    }

    @GetMapping("/listUser")
    public BaseResponse getListUser(@RequestParam (required = false) String name) {
        return BaseResponse.success(mUserService.getListUser(name));
    }

    @GetMapping("/paging")
    public PageRes PagingUser(@RequestParam Long pageNo,
                              @RequestParam Long pageSize,
                              @RequestParam(defaultValue = "", required = false) String name,
                              @RequestParam(defaultValue = "", required = false) String email,
                              @RequestParam(required = false) Integer id,
                              @RequestParam(required = false) UserEnum isActive) {
        return mUserService.getPageUser(pageNo, pageSize, name, email, id, isActive);
    }

    @GetMapping("/basic")
    public void test() {
        mUserService.testJoin();
    }

    @GetMapping("/")
    public BaseResponse getAllUser(@RequestParam Long pageNo,
                                   @RequestParam Long pageSize) {
        return BaseResponse.success(mUserService.getAllUser(pageNo,pageSize));
    }
}
