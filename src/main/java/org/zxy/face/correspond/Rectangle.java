package org.zxy.face.correspond;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class Rectangle {

    private Integer left;

    private Integer right;

    private Integer top;

    private Integer bottom;
}
