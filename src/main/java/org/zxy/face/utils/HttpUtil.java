package org.zxy.face.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zxy.face.config.DestConfig;
import org.zxy.face.correspond.FaceAddFormat;

import javax.annotation.Resource;

@Slf4j
public class HttpUtil {

    @Resource
    private DestConfig destConfig;

    private Gson gson = new Gson();

    public FaceAddFormat postForFaceAdd(String imageBase64) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("save_symbol", "save");
        json.put("image_json", imageBase64);

        HttpEntity<String> entity = new HttpEntity<>(json.toString());

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> result = template.postForEntity(destConfig.getUrl(), entity, String.class);

        return gson.fromJson(result.getBody(), FaceAddFormat.class);
    }
}
