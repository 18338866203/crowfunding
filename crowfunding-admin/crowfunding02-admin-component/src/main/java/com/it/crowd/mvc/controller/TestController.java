package com.it.crowd.mvc.controller;

import com.it.crowd.entity.Admin;
import com.it.crowd.service.api.AdminService;
import com.it.crowd.util.CrowdUtil;
import com.it.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-05
 */
@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AdminService service;

//    @ResponseBody
//    @RequestMapping("/send/complex/object.json")
//    public ResultEntity<Student> testReceiveComplexObject(@RequestBody Student student) {
//        logger.info(student.toString());
//        return ResultEntity.successWithData(student);
//    }
//
    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        Logger logger = LoggerFactory.getLogger(TestController.class);
        for (Integer number: array)
            logger.info("number = " + number);
        return "success";
    }
//
//    @ResponseBody
//    @RequestMapping("/send/array/two.html")
//    public String testReceiveArrayTwo(ParamData paramData) {
//        List<Integer> array = paramData.getArray();
//        array.forEach(System.out::println);
//        return "success";
//    }

    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        array.forEach(System.out::println);
        return "success";
    }

    @RequestMapping("/test/ssm.html")
    public String testSSM(Model model, HttpServletRequest request){

        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        logger.info("judgeRequestType: " + judgeRequestType);
        List<Admin> list = service.getAll();
        model.addAttribute("list",list);

        System.out.println(10 / 0);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/test/ajax/async.html")
    public String testAsync(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
