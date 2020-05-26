package org.zxy.face.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.face.VO.PersonVO;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.dataobject.PersonInfo;
import org.zxy.face.enums.ResponseEnum;
import org.zxy.face.form.PersonForm;
import org.zxy.face.repository.PersonRepository;
import org.zxy.face.service.IPersonService;
import org.zxy.face.utils.ApiUtil;
import org.zxy.face.utils.KeyUtil;

import javax.annotation.Resource;

@Service
public class PersonServiceImpl implements IPersonService {

    @Resource
    private PersonRepository personRepository;

    @Resource
    private ApiUtil apiUtil;

    @Override
    public ResponseVO add(PersonForm personForm) {

        // 查找api是否合法；通过redis
        apiUtil.verifyApi(personForm.getApi());

        // 查找数据库是否有该person
        // 如果已经有，则错误
        if (personRepository.findByApiAndPersonId(personForm.getApi(), personForm.getPersonId()) != null) {
            return ResponseVO.error(ResponseEnum.PERSON_EXIST);
        }

        // 没有，则写入数据库
        PersonInfo personInfo = new PersonInfo();
        BeanUtils.copyProperties(personForm, personInfo);
        personInfo.setId(KeyUtil.getUniqueKey());

        personRepository.save(personInfo);

        // 返回数据
        PersonVO personVO = new PersonVO();
        BeanUtils.copyProperties(personInfo, personVO);

        return ResponseVO.success(personVO);
    }

    @Override
    public ResponseVO delete(PersonForm personForm) {
        // 查找api是否合法；通过redis
        apiUtil.verifyApi(personForm.getApi());

        PersonInfo personInfo = personRepository.findByApiAndPersonIdAndPersonName(personForm.getApi(),
                personForm.getPersonId(),
                personForm.getPersonName());
        if (personInfo == null) {
            return ResponseVO.error(ResponseEnum.PERSON_ID_OR_NAME_ERROR);
        }

        personRepository.delete(personInfo);

        PersonVO personVO = new PersonVO();
        BeanUtils.copyProperties(personInfo, personVO);

        return ResponseVO.success(personVO);
    }
}
