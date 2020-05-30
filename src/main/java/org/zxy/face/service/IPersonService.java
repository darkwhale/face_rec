package org.zxy.face.service;

import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.PersonDeleteForm;
import org.zxy.face.form.PersonForm;
import org.zxy.face.form.PersonListForm;

import java.util.List;

public interface IPersonService {

    ResponseVO add(PersonForm personForm);

    ResponseVO delete(PersonDeleteForm personDeleteForm);

    ResponseVO list(PersonListForm personListForm);

}
