package org.zxy.face.utils;

import org.zxy.face.consts.FaceConst;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.correspond.MatchElement;
import org.zxy.face.enums.MatchEnum;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {


    static String add(String numStr){
        if (numStr.equals("")) {
            return "1";
        }
        return String.valueOf(Integer.parseInt(numStr) + 1);
    }

    /**
     *
     * @param firstList: 用户提交的图片中的人脸特征
     * @param secondList： 数据库中的人脸特征
     * @return 最小的距离和索引
     */
    public static List<MatchElement> getDistance(List<FaceFeatureCorrespond> firstList, List<FaceFeatureCorrespond> secondList) {

        List<MatchElement> matchElementList = new ArrayList<>();

        for (FaceFeatureCorrespond first : firstList) {

            double distance = 100000000;
            String faceId = null;

            for (FaceFeatureCorrespond second : secondList) {
                double thisDistance = getDistanceFloat(first.getFeature(), second.getFeature());
                if (thisDistance < distance) {
                    distance = thisDistance;
                    faceId = second.getId();
                }
            }

            Integer matched = distance < FaceConst.threshold?
                    MatchEnum.Match.getCode(): MatchEnum.NOT_MATCH.getCode();

            MatchElement matchElement = new MatchElement(first.getRectangle(), faceId, matched);
            matchElementList.add(matchElement);
        }

        return matchElementList;

    }

    private static double getDistanceFloat(List<Float> first, List<Float> second) {

        double distance = 0;
        for (int i = 0; i != first.size(); i++) {
            distance += Math.pow(first.get(i) - second.get(i), 2);
        }

        return Math.sqrt(distance);
    }
}
