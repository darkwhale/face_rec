package org.zxy.face.service.Impl;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.config.HttpConfig;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.form.FaceDeleteForm;
import org.zxy.face.form.FaceMultiMatchForm;
import org.zxy.face.form.FaceSingleMatchForm;
import org.zxy.face.repository.FaceRepository;
import org.zxy.face.repository.PersonRepository;
import org.zxy.face.service.IFaceService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.HttpUtil;

import javax.annotation.Resource;

@Service
public class FaceServiceImpl implements IFaceService {

    @Resource
    private ApiUtil apiUtil;

    @Resource
    private HttpUtil httpUtil;

    @Resource
    private FaceRepository faceRepository;

    @Resource
    private PersonRepository personRepository;

    @Override
    public ResponseVO add(FaceAddForm faceAddForm) throws JSONException {
        // 验api
        apiUtil.verifyApi(faceAddForm.getApi());

        // 验person是否存在
        personRepository.findByApiAndPersonId(faceAddForm.getApi(), faceAddForm.getPersonId());

        // 调用接口获取人脸特征
        httpUtil.postForFaceAdd(faceAddForm.getImage());

        // 写redis

        // 写数据库
        return null;
    }

    @Override
    public ResponseVO delete(FaceDeleteForm faceDeleteForm) {
        return null;
    }

    @Override
    public ResponseVO singleMatch(FaceSingleMatchForm faceSingleMatchForm) {
        return null;
    }

    @Override
    public ResponseVO multiMatch(FaceMultiMatchForm faceMultiMatchForm) {
        return null;
    }
}
