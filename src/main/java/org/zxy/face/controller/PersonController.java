package org.zxy.face.controller;


import org.springframework.web.bind.annotation.*;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.form.PersonDeleteForm;
import org.zxy.face.form.PersonForm;
import org.zxy.face.form.PersonListForm;
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
    public ResponseVO delete(@Valid @RequestBody PersonDeleteForm personDeleteForm) {
        return personService.delete(personDeleteForm);
    }

    @GetMapping("/list")
    public ResponseVO list(@Valid @RequestBody PersonListForm personListForm) {
        return personService.list(personListForm);
    }
}
