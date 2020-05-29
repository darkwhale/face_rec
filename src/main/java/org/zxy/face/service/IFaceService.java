package org.zxy.face.service;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.form.FaceDeleteForm;
import org.zxy.face.form.FaceMultiMatchForm;
import org.zxy.face.form.FaceSingleMatchForm;

import java.util.List;

public interface IFaceService {

    ResponseVO add(FaceAddForm faceAddForm) throws JSONException;

    ResponseVO delete(FaceDeleteForm faceDeleteForm);

    ResponseVO singleMatch(FaceSingleMatchForm faceSingleMatchForm) throws JSONException;

    ResponseVO multiMatch(FaceMultiMatchForm faceMultiMatchForm) throws JSONException;

}
