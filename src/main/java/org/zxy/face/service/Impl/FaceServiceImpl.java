package org.zxy.face.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.FaceVO;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.correspond.FaceAddFormat;
import org.zxy.face.dataobject.FaceInfo;
import org.zxy.face.dataobject.PersonInfo;
import org.zxy.face.enums.FaceAddEnum;
import org.zxy.face.enums.ResponseEnum;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.form.FaceDeleteForm;
import org.zxy.face.form.FaceMultiMatchForm;
import org.zxy.face.form.FaceSingleMatchForm;
import org.zxy.face.repository.FaceRepository;
import org.zxy.face.repository.PersonRepository;
import org.zxy.face.service.IFaceService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.HttpUtil;
import org.zxy.face.utils.KeyUtil;

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
        PersonInfo personInfo = personRepository.findByApiAndPersonId(faceAddForm.getApi(), faceAddForm.getPersonId());
        if (personInfo == null) {
            return ResponseVO.error(ResponseEnum.PERSON_NOT_EXIST);
        }

        // 调用接口获取人脸特征
        FaceAddFormat faceAddFormat = httpUtil.postForFaceAdd(faceAddForm.getImage());

        if (faceAddFormat.getCode().equals(FaceAddEnum.IMAGE_ERROR.getCode())) {
            return ResponseVO.error(ResponseEnum.IMAGE_ERROR);
        }

        if (faceAddFormat.getCode().equals(FaceAddEnum.REQUEST_ERROR.getCode())) {
            return ResponseVO.error(ResponseEnum.REQUEST_ERROR);
        }

        if (!faceAddFormat.getCode().equals(FaceAddEnum.SUCCESS.getCode())) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        // todo 写redis

        // 写数据库
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setId(KeyUtil.getUniqueKey());
        faceInfo.setApi(faceAddForm.getApi());
        faceInfo.setPersonId(faceAddForm.getPersonId());
        faceInfo.setImageName(faceAddFormat.getData().getImageName());

        faceRepository.save(faceInfo);

        FaceVO faceVO = new FaceVO();
        BeanUtils.copyProperties(faceInfo, faceVO);

        return ResponseVO.success(faceVO);
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
