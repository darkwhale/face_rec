package org.zxy.face.utils;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.zxy.face.correspond.FaceFeatureCorrespond;
import org.zxy.face.correspond.MatchElement;

import javax.validation.constraints.Max;
import java.util.List;

public class MathUtil {

    public static String add(String numStr){
        if (numStr.equals("")) {
            return "1";
        }
        return String.valueOf(Integer.parseInt(numStr) + 1);
    }

    public static MatchElement getDistance(List<FaceFeatureCorrespond> firstList, List<FaceFeatureCorrespond> secondList) {
        double distance = 1000000;
        FaceFeatureCorrespond faceFeatureCorrespond;

        for (FaceFeatureCorrespond first : firstList) {
            for (FaceFeatureCorrespond second : secondList) {
                double thisDistance = getDistanceFloat(first.getFeature(), second.getFeature());
                if (thisDistance < distance) {
                    distance = thisDistance;
                    faceFeatureCorrespond = second;
                }
            }
        }

        MatchElement matchElement = new MatchElement();
        matchElement.setDistance(distance);

        return null;

    }

    private static double getDistanceFloat(List<Float> first, List<Float> second) {

        double distance = 0;
        for (int i = 0; i != first.size(); i++) {
            distance += Math.pow(first.get(i) - second.get(i), 2);
        }

        return Math.sqrt(distance);
    }
}
