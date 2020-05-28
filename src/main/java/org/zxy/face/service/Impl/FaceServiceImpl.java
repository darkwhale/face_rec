package org.zxy.face.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.FaceVO;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.correspond.FaceAddFormat;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.correspond.FaceMatchFormat;
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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaceServiceImpl implements IFaceService {

    @Resource
    private ApiUtil apiUtil;

    @Resource
    private HttpUtil httpUtil;

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

        if (faceAddFormat.getCode().equals(FaceAddEnum.OTHER_ERROR.getCode())) {
            return ResponseVO.error(ResponseEnum.OTHER_ERROR);
        }

        if (!faceAddFormat.getCode().equals(FaceAddEnum.SUCCESS.getCode())) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        // 构造faceInfo;
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setId(KeyUtil.getUniqueKey());
        faceInfo.setApi(faceAddForm.getApi());
        faceInfo.setPersonId(faceAddForm.getPersonId());
        faceInfo.setImageName(faceAddFormat.getData().getImageName());

        // 写redis
        FaceFeatureCorrespond faceFeatureCorrespond = faceAddFormat.getData();
        faceFeatureCorrespond.setId(faceInfo.getId());

        apiUtil.writeFaceRedis(faceAddForm.getApi(), faceInfo.getPersonId(), faceFeatureCorrespond);

        FaceVO faceVO = new FaceVO();
        BeanUtils.copyProperties(faceInfo, faceVO);

        return ResponseVO.success(faceVO);
    }

    @Override
    public ResponseVO delete(FaceDeleteForm faceDeleteForm) {
        // 验api
        apiUtil.verifyApi(faceDeleteForm.getApi());

        List<FaceFeatureCorrespond> faceFeatureCorrespondList = apiUtil.readFaceRedis(faceDeleteForm.getApi(), faceDeleteForm.getPersonId());
        int before_size = faceFeatureCorrespondList.size();

        faceFeatureCorrespondList.removeIf(faceFeatureCorrespond -> faceFeatureCorrespond.getId().equals(faceDeleteForm.getFaceId()));

        if (before_size == faceFeatureCorrespondList.size()) {
            return ResponseVO.error(ResponseEnum.FACE_NOT_EXIST);
        }
        apiUtil.writeFaceRedisOverWrite(faceDeleteForm.getApi(), faceDeleteForm.getPersonId(), faceFeatureCorrespondList);

        return ResponseVO.success();
    }

    @Override
    public ResponseVO singleMatch(FaceSingleMatchForm faceSingleMatchForm) throws JSONException {
        // 验api
        apiUtil.verifyApi(faceSingleMatchForm.getApi());

        // 查人脸
        //List<FaceInfo> faceInfoList = faceRepository.findAllByApiAndPersonId(faceSingleMatchForm.getApi(), faceSingleMatchForm.getPersonId());
        //if (faceInfoList.isEmpty()) {
        //    return ResponseVO.error(ResponseEnum.PERSON_OR_FACE_EMPTY);
        //}
        //// 人脸id获取
        //List<String> faceIdList = faceInfoList.stream().map(FaceInfo::getId).collect(Collectors.toList());
        //List<FaceFeatureCorrespond> faceFeatureListRedis = apiUtil.getFaceFeatureByFaceIdList(faceSingleMatchForm.getApi(), faceIdList);
        //
        //// 调django获取人脸特征；
        //FaceMatchFormat faceMatchFormat = httpUtil.postForFaceMatch(faceSingleMatchForm.getImage());
        //
        //List<FaceFeatureCorrespond> faceFeatureList = faceMatchFormat.getData();

        List<FaceFeatureCorrespond> faceFeatureCorrespondList = apiUtil.readFaceRedis(faceSingleMatchForm.getApi(), faceSingleMatchForm.getPersonId());
        if (faceFeatureCorrespondList.isEmpty()) {
            return ResponseVO.error(ResponseEnum.PERSON_OR_FACE_EMPTY);
        }

        FaceMatchFormat faceMatchFormat = httpUtil.postForFaceMatch(faceSingleMatchForm.getImage());

        if (!faceMatchFormat.getCode().equals(FaceAddEnum.SUCCESS.getCode())) {
            return ResponseVO.error(ResponseEnum.IMAGE_ERROR);
        }

        return null;

    }

    @Override
    public ResponseVO multiMatch(FaceMultiMatchForm faceMultiMatchForm) {
        return null;
    }


}
