package org.zxy.face.service;

import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.PersonForm;
import org.zxy.face.form.PersonListForm;

import java.util.List;

public interface IPersonService {

    ResponseVO add(PersonForm personForm);

    ResponseVO delete(PersonForm personForm);

    ResponseVO list(PersonListForm personListForm);

}
