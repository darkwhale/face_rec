package org.zxy.face.service;

import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.PersonForm;

public interface IPersonService {

    ResponseVO add(PersonForm personForm);

    ResponseVO delete(PersonForm personForm);
}
