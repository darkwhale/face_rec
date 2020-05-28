package org.zxy.face.controller;

import org.springframework.web.bind.annotation.*;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.VO.UserVO;
import org.zxy.face.consts.FaceConst;
import org.zxy.face.form.UserListForm;
import org.zxy.face.form.UserLoginForm;
import org.zxy.face.form.UserRegisterForm;
import org.zxy.face.service.Impl.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @GetMapping("/list")
    public ResponseVO list(@Valid @RequestBody UserListForm userListForm) {
        return userService.list(userListForm);
    }

}
