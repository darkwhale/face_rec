package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginForm {

    @NotEmpty
    private String id;

    @NotEmpty
    private String password;
}
