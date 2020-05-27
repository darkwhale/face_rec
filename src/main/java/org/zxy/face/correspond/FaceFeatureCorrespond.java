package org.zxy.face.correspond;

import lombok.Data;

import java.util.List;

@Data
public class FaceFeatureCorrespond {

    private String imageName;

    private Rectangle rectangle;

    private List<Float> feature;

}
