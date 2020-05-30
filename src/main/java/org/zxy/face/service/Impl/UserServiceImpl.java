package org.zxy.face.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zxy.face.VO.FaceImageVO;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.VO.UserVO;
import org.zxy.face.config.DestConfig;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.dataobject.FaceInfo;
import org.zxy.face.dataobject.PersonInfo;
import org.zxy.face.dataobject.UserInfo;
import org.zxy.face.enums.ResponseEnum;
import org.zxy.face.form.UserListForm;
import org.zxy.face.form.UserLoginForm;
import org.zxy.face.form.UserRegisterForm;
import org.zxy.face.repository.FaceRepository;
import org.zxy.face.repository.PersonRepository;
import org.zxy.face.repository.UserRepository;
import org.zxy.face.service.IUserService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.KeyUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ApiUtil apiUtil;

    @Resource
    private DestConfig destConfig;

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

    @Override
    public ResponseVO list(UserListForm userListForm) {
        apiUtil.verifyApi(userListForm.getApi());


        //List<FaceInfo> faceInfoList = faceRepository.findAllByApiAndPersonIdIn(userListForm.getApi(), personIdList);
        //
        //Map<String, List<FaceInfo>> faceInfoMap = faceInfoList.stream().collect(Collectors.groupingBy(FaceInfo::getPersonId));
        //Map<String, List<String>> faceResult = new TreeMap<>();
        //for (Map.Entry<String, List<FaceInfo>> entry : faceInfoMap.entrySet()) {
        //    faceResult.put(entry.getKey(), entry.getValue().stream().map(FaceInfo::getId).collect(Collectors.toList()));
        //}
        //
        //for (String personId : personIdList) {
        //    if (!faceResult.containsKey(personId)){
        //        faceResult.put(personId, new ArrayList<>());
        //    }
        //}

        //return ResponseVO.success(faceResult);

        // 调redis
        Map<String, List<FaceFeatureCorrespond>> FaceFeatureMap = apiUtil.readFaceRedis(userListForm.getApi());
        Map<String, List<String>> faceResult = new TreeMap<>();
        for (Map.Entry<String, List<FaceFeatureCorrespond>> entry : FaceFeatureMap.entrySet()) {
            faceResult.put(
                    entry.getKey(),
                    entry.getValue().stream().map(FaceFeatureCorrespond::getId).collect(Collectors.toList())
                    );
        }

        return ResponseVO.success(faceResult);
    }


    @Override
    public ResponseVO listForImage(UserListForm userListForm) {
        apiUtil.verifyApi(userListForm.getApi());

        Map<String, List<FaceFeatureCorrespond>> FaceFeatureMap = apiUtil.readFaceRedis(userListForm.getApi());
        Map<String, List<FaceImageVO>> faceResult = new TreeMap<>();
        for (Map.Entry<String, List<FaceFeatureCorrespond>> entry : FaceFeatureMap.entrySet()) {
            faceResult.put(
                    entry.getKey(),
                    entry.getValue().stream().map(e -> {
                        FaceImageVO faceImageVO = new FaceImageVO();
                        faceImageVO.setId(e.getId());
                        faceImageVO.setImageName(destConfig.getImageServer() + e.getImageName());
                        return faceImageVO;
                    }).collect(Collectors.toList())
            );
        }
        return ResponseVO.success(faceResult);
    }
}
