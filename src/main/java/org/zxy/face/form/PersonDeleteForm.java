package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PersonDeleteForm {

    @NotEmpty
    private String api;

    @NotEmpty
    private String personId;
}
