package org.zxy.face.correspond;

import lombok.Data;

@Data
public class MatchElement {

    private Rectangle rectangle;

    private String faceId;

    private Integer matched;

    public MatchElement() {
    }

    public MatchElement(Rectangle rectangle, String faceId, Integer matched) {
        this.rectangle = rectangle;
        this.faceId = faceId;
        this.matched = matched;
    }
}
