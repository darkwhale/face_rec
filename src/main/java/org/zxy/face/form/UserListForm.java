package org.zxy.face.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserListForm {

    @NotEmpty
    private String api;
}
