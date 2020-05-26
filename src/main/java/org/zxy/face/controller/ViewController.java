package org.zxy.face.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/viewer")
public class ViewController {

    @GetMapping("/index")
    public ModelAndView getIndex() {

        return new ModelAndView("common/index");
    }
}
