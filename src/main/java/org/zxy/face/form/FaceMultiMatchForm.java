package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FaceMultiMatchForm {

    @NotEmpty
    private String api;

    @NotEmpty
    private String image;
}
