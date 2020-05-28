package org.zxy.face.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.dataobject.FaceInfo;
import org.zxy.face.exceptions.ApiException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class ApiUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    private final String API_PREFIX = "api_";
    
    private final String PERSON_PREFIX = "per_";

    public void verifyApi(String api) {
        //redisTemplate.opsForSet().;
        String value = stringRedisTemplate.opsForValue().get(API_PREFIX + api);
        if (value == null) {
            throw new ApiException();
        }

        // 否则保存的数字加1，代表此人访问了多少次；
        stringRedisTemplate.opsForValue().set(API_PREFIX + api, MathUtil.add(value));
    }

    public void addApi(String api) {
        // 判断redis是否为空
        stringRedisTemplate.opsForValue().set(API_PREFIX + api, "");
    }

    public Map<String, List<FaceFeatureCorrespond>> readFaceRedis(String api) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        Map<String, String> entries = opsForHash.entries(PERSON_PREFIX + api);

        Map<String, List<FaceFeatureCorrespond>> result = new TreeMap<>();
        for(Map.Entry<String, String> entry : entries.entrySet()) {
            result.put(entry.getKey(),
                    gson.fromJson(entry.getValue(), new TypeToken<List<FaceFeatureCorrespond>>(){}.getType()));
        }
        return result;
    }

    public List<FaceFeatureCorrespond> readFaceRedis(String api, String personId) {
        String value = (String) stringRedisTemplate.opsForHash().get(PERSON_PREFIX + api, personId);

        // 为空表示没有过该person；
        List<FaceFeatureCorrespond> faceFeatureCorrespondList;
        if (StringUtils.isEmpty(value)) {
            faceFeatureCorrespondList = new ArrayList<>();
        }else {
            faceFeatureCorrespondList = gson.fromJson(value, new TypeToken<List<FaceFeatureCorrespond>>(){}.getType());
        }

        return faceFeatureCorrespondList;
    }

    public void writeFaceRedis(String api, String personId, FaceFeatureCorrespond faceFeatureCorrespond) {
        List<FaceFeatureCorrespond> faceFeatureCorrespondList = readFaceRedis(api, personId);

        if (faceFeatureCorrespond != null){
            faceFeatureCorrespondList.add(faceFeatureCorrespond);
        }

        stringRedisTemplate.opsForHash().put(PERSON_PREFIX + api, personId, gson.toJson(faceFeatureCorrespondList));

    }

    public void writeFaceRedisOverWrite(String api, String personId, List<FaceFeatureCorrespond> faceFeatureCorrespondList) {
        stringRedisTemplate.opsForHash().put(PERSON_PREFIX + api, personId, gson.toJson(faceFeatureCorrespondList));
    }

    public void deleteFaceRedis(String api, String faceId) {
        stringRedisTemplate.opsForHash().delete(PERSON_PREFIX + api, faceId);
    }

}
