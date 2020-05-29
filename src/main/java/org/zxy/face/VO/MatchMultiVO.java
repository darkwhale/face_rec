package org.zxy.face.VO;

import lombok.Data;
import org.zxy.face.correspond.Rectangle;

@Data
public class MatchMultiVO {

    private Rectangle rectangle;

    private String personId;

    public MatchMultiVO(Rectangle rectangle, String personId) {
        this.rectangle = rectangle;
        this.personId = personId;
    }
}
