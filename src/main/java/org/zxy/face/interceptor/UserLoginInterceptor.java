package org.zxy.face.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.zxy.face.VO.UserVO;
import org.zxy.face.consts.FaceConst;
import org.zxy.face.exceptions.UserLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserVO userVO = (UserVO) request.getSession().getAttribute(FaceConst.CURRENT_USER);

        if (userVO == null) {
            throw new UserLoginException();
        }

        return true;
    }
}
