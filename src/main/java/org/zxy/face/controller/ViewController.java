package org.zxy.face.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.VO.UserVO;
import org.zxy.face.consts.FaceConst;
import org.zxy.face.form.UserLoginForm;
import org.zxy.face.form.UserRegisterForm;
import org.zxy.face.service.Impl.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/viewer")
public class ViewController {

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseVO register(@Valid @RequestBody UserRegisterForm userRegisterForm) {
        return userService.register(userRegisterForm);
    }

    @PostMapping("/login")
    public ResponseVO login(@Valid @RequestBody UserLoginForm userLoginForm,
                            HttpSession session) {
        ResponseVO userResponse = userService.login(userLoginForm);
        // 成功才写入session；
        if (userResponse.getCode() == 0) {
            session.setAttribute(FaceConst.CURRENT_USER, userResponse.getData());
        }
        return userResponse;
    }

    @GetMapping("/user")
    public ResponseVO user(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute(FaceConst.CURRENT_USER);
        return ResponseVO.success(userVO);
    }


    @PostMapping("/logout")
    public ResponseVO logout(HttpSession session) {
        session.removeAttribute(FaceConst.CURRENT_USER);
        return ResponseVO.success();
    }
}
