package org.zxy.face.VO;

import lombok.Data;
import org.zxy.face.correspond.Rectangle;

@Data
public class MatchSingleVO {

    private Rectangle rectangle;

    private Integer matched;

    public MatchSingleVO(Rectangle rectangle, Integer matched) {
        this.rectangle = rectangle;
        this.matched = matched;
    }
}
