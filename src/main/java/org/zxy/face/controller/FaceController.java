package org.zxy.face.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.FaceAddForm;
import org.zxy.face.service.Impl.FaceServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/face")
@Slf4j
public class FaceController {

    @Resource
    private FaceServiceImpl faceService;

    @GetMapping("/add")
    public ResponseVO add(@Valid @RequestBody FaceAddForm faceAddForm) throws JSONException {
        return faceService.add(faceAddForm);
    }
}
