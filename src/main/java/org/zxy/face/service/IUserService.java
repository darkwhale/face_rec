package org.zxy.face.service;

import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.UserListForm;
import org.zxy.face.form.UserLoginForm;
import org.zxy.face.form.UserRegisterForm;

public interface IUserService {

    ResponseVO register(UserRegisterForm userRegisterForm);

    ResponseVO login(UserLoginForm userLoginForm);

    ResponseVO list(UserListForm userListForm);
}
