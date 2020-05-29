package org.zxy.face.enums;

import lombok.Getter;

@Getter
public enum MatchEnum {
    Match(0, "匹配成功"),

    NOT_MATCH(1, "匹配不成功"),
    ;


    private Integer code;

    private String message;

    MatchEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
