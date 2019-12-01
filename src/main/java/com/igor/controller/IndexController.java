package com.igor.controller;


import com.igor.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ModelService modelService;

    private ModelAndView view;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView Index() {

        view = new ModelAndView();

        view.addObject("modelList", modelService.findAll());

        view.setViewName("Padrao/Index");

        return view;
    }

    @RequestMapping(value = "/customSearch", method = RequestMethod.GET)
    public ModelAndView customSearch(@RequestParam(name = "searchParameter") String searchParameter) {

        view = new ModelAndView();

        view.addObject("modelList", modelService.findBySearch(searchParameter));

        view.setViewName("Padrao/Index");

        return view;
    }




}
