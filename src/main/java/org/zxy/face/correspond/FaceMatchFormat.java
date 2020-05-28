package org.zxy.face.correspond;

import lombok.Data;

import java.util.List;

@Data
public class FaceMatchFormat {

    private Integer code;

    private List<FaceFeatureCorrespond> data;
}
