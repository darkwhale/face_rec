package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterForm {

    @NotEmpty
    private String id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
