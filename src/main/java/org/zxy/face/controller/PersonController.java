package org.zxy.face.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.PersonForm;
import org.zxy.face.service.Impl.PersonServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Resource
    private PersonServiceImpl personService;

    @PostMapping("/add")
    public ResponseVO add(@Valid @RequestBody PersonForm personForm) {
        return personService.add(personForm);
    }

    @PostMapping("/delete")
    public ResponseVO delete(@Valid @RequestBody PersonForm personForm) {
        return personService.delete(personForm);
    }
}
