package com.personal.seckill.controller;

import com.personal.seckill.domain.User;
import com.personal.seckill.result.CodeMsg;
import com.personal.seckill.result.CommonResult;
import com.personal.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("demo")
public class DemoController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("hello")
    CommonResult<String> hello(){

        return CommonResult.success("hello~it's success");
    }

    @ResponseBody
    @RequestMapping("helloError")
    CommonResult<String> helloError(){

        return CommonResult.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("thymeleaf1")
    ModelAndView thymeleafTest(ModelAndView mav){
        mav.addObject("name","testThymeleaf1");
        mav.setViewName("hello");
        return mav;
    }

    @RequestMapping("thymeleaf2")
    String thymeleafTest2(Model model){
        model.addAttribute("name","testThymeleaf2");
        return "hello";
    }

    @RequestMapping("thymeleaf3")
    String thymeleafTest(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","testThymeleaf3");
        return "hello";
    }

    @RequestMapping("mybatis1")
    @ResponseBody
    CommonResult<User> getUserById(int id) {
        User uesr = userService.getUserById(id);
        return CommonResult.success(uesr);
    }

    //事务测试
    @RequestMapping("mybatis2")
    @ResponseBody
    CommonResult<Boolean> txTest() {

        return CommonResult.success(userService.txTest());
    }
}
