package by.vsu.controller;

import by.vsu.model.UserInfo;
import by.vsu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable("id") Integer id) {
        return userInfoService.getById(id);
    }

    @PostMapping
    public UserInfo create(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }

    @PutMapping
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }
}
