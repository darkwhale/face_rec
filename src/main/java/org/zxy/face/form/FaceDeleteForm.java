package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FaceDeleteForm {

    @NotEmpty
    private String api;

    @NotEmpty
    private String personId;

    @NotEmpty
    private String faceId;
}
