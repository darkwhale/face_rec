package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FaceSingleMatchForm {

    @NotEmpty
    private String api;

    @NotEmpty
    private String personId;

    @NotEmpty
    private String image;
}
