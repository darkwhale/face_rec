package org.zxy.face.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.VO.UserVO;
import org.zxy.face.dataobject.UserInfo;
import org.zxy.face.enums.ResponseEnum;
import org.zxy.face.form.UserLoginForm;
import org.zxy.face.form.UserRegisterForm;
import org.zxy.face.repository.UserRepository;
import org.zxy.face.service.IUserService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.KeyUtil;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;

    @Autowired
    private ApiUtil apiUtil;

    @Override
    public ResponseVO register(UserRegisterForm userRegisterForm) {

        // 用户已存在
        if (userRepository.findById(userRegisterForm.getId()).orElse(null) != null) {
            return ResponseVO.error(ResponseEnum.USER_EXIST);
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userRegisterForm, userInfo);

        // 设置api
        userInfo.setApi(KeyUtil.getUniqueKey());
        UserInfo result = userRepository.save(userInfo);
        if (result == null) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        // api写入到redis中;
        apiUtil.addApi(userInfo.getApi());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);
        return ResponseVO.success(userVO);
    }

    @Override
    public ResponseVO login(UserLoginForm userLoginForm) {
        UserInfo userInfo = userRepository.findById(userLoginForm.getId()).orElse(null);

        if (userInfo == null) {
            return ResponseVO.error(ResponseEnum.USER_NOT_EXIST);
        }

        if (!userInfo.getPassword().equals(userLoginForm.getPassword())) {
            return ResponseVO.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);
        return ResponseVO.success(userVO);
    }
}
