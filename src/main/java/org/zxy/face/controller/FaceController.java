package org.zxy.face.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.correspond.FaceMatchFormat;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.form.FaceDeleteForm;
import org.zxy.face.form.FaceSingleMatchForm;
import org.zxy.face.service.Impl.FaceServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/facial")
@Slf4j
public class FaceController {

    @Resource
    private FaceServiceImpl faceService;

    @PostMapping("/add")
    public ResponseVO add(@Valid @RequestBody FaceAddForm faceAddForm) throws JSONException {
        return faceService.add(faceAddForm);
    }

    @PostMapping("/delete")
    public ResponseVO delete(@Valid @RequestBody FaceDeleteForm faceDeleteForm) {
        return faceService.delete(faceDeleteForm);
    }

    @PostMapping("/single_match")
    public ResponseVO singleMatch(@Valid @RequestBody FaceSingleMatchForm faceSingleMatchForm) throws JSONException {
        return faceService.singleMatch(faceSingleMatchForm);
    }

}
