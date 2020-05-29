package org.zxy.face.service.Impl;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.FaceVO;
import org.zxy.face.VO.MatchMultiVO;
import org.zxy.face.VO.MatchSingleVO;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.correspond.FaceAddFormat;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.correspond.FaceMatchFormat;
import org.zxy.face.correspond.MatchElement;
import org.zxy.face.enums.FaceAddEnum;
import org.zxy.face.enums.MatchEnum;
import org.zxy.face.enums.ResponseEnum;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.form.FaceDeleteForm;
import org.zxy.face.form.FaceMultiMatchForm;
import org.zxy.face.form.FaceSingleMatchForm;
import org.zxy.face.service.IFaceService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.HttpUtil;
import org.zxy.face.utils.KeyUtil;
import org.zxy.face.utils.MathUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaceServiceImpl implements IFaceService {

    @Resource
    private ApiUtil apiUtil;

    @Resource
    private HttpUtil httpUtil;

    @Override
    public ResponseVO add(FaceAddForm faceAddForm) throws JSONException {
        // 验api
        apiUtil.verifyApi(faceAddForm.getApi());

        if (!apiUtil.existPersonId(faceAddForm.getApi(), faceAddForm.getPersonId())) {
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

        // 写redis
        FaceFeatureCorrespond faceFeatureCorrespond = faceAddFormat.getData();
        faceFeatureCorrespond.setId(KeyUtil.getUniqueKey());

        apiUtil.writeFaceRedis(faceAddForm.getApi(), faceAddForm.getPersonId(), faceFeatureCorrespond);

        FaceVO faceVO = new FaceVO();
        faceVO.setId(faceFeatureCorrespond.getId());
        faceVO.setPersonId(faceAddForm.getPersonId());

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

        List<FaceFeatureCorrespond> faceFeatureCorrespondList = apiUtil.readFaceRedis(faceSingleMatchForm.getApi(), faceSingleMatchForm.getPersonId());
        if (faceFeatureCorrespondList.isEmpty()) {
            return ResponseVO.error(ResponseEnum.PERSON_OR_FACE_EMPTY);
        }

        FaceMatchFormat faceMatchFormat = httpUtil.postForFaceMatch(faceSingleMatchForm.getImage());

        if (!faceMatchFormat.getCode().equals(FaceAddEnum.SUCCESS.getCode())) {
            return ResponseVO.error(ResponseEnum.IMAGE_ERROR);
        }

        List<MatchElement> matchResult = MathUtil.getDistance(faceMatchFormat.getData(), faceFeatureCorrespondList);

        List<MatchSingleVO> matchSingleVOList = new ArrayList<>();
        for (MatchElement matchElement : matchResult) {
            matchSingleVOList.add(new MatchSingleVO(matchElement.getRectangle(), matchElement.getMatched()));
        }

        return ResponseVO.success(matchSingleVOList);

    }

    @Override
    public ResponseVO multiMatch(FaceMultiMatchForm faceMultiMatchForm) throws JSONException {
        // 验api
        apiUtil.verifyApi(faceMultiMatchForm.getApi());

        Map<String, List<FaceFeatureCorrespond>> personFaceMap = apiUtil.readFaceRedis(faceMultiMatchForm.getApi());

        // 构造从faceId到personId的map
        Map<String, String> face2PersonMap = new HashMap<>();

        for (Map.Entry<String, List<FaceFeatureCorrespond>> entry: personFaceMap.entrySet()) {
            List<String> collect = entry.getValue().stream().map(FaceFeatureCorrespond::getId).collect(Collectors.toList());
            for (String faceId : collect) {
                face2PersonMap.put(faceId, entry.getKey());
            }
        }

        // 将personFaceMap的列表合并起来；
        List<FaceFeatureCorrespond> totalFaceList = new ArrayList<>();
        for (Map.Entry<String, List<FaceFeatureCorrespond>> entry: personFaceMap.entrySet()) {
            totalFaceList.addAll(entry.getValue());
        }

        // 调接口获取图像特征；
        FaceMatchFormat faceMatchFormat = httpUtil.postForFaceMatch(faceMultiMatchForm.getImage());

        if (!faceMatchFormat.getCode().equals(FaceAddEnum.SUCCESS.getCode())) {
            return ResponseVO.error(ResponseEnum.IMAGE_ERROR);
        }

        List<MatchElement> matchResult = MathUtil.getDistance(faceMatchFormat.getData(), totalFaceList);

        List<MatchMultiVO> matchMultiVOList = new ArrayList<>();
        for (MatchElement matchElement : matchResult) {
            matchMultiVOList.add(new MatchMultiVO(matchElement.getRectangle(),
                    matchElement.getMatched().equals(MatchEnum.Match.getCode()) ?
                    face2PersonMap.get(matchElement.getFaceId()) : null));
        }

        return ResponseVO.success(matchMultiVOList);

    }
}
