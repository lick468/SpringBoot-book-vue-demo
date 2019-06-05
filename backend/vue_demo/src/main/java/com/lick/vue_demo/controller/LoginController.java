package com.lick.vue_demo.controller;

import com.lick.vue_demo.domain.TbUser;
import com.lick.vue_demo.mapper.TbUserMapper;
import com.lick.vue_demo.result.AjaxResult;
import com.lick.vue_demo.result.ResultFactory;
import com.lick.vue_demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private TbUserMapper tbUserMapper;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public AjaxResult login(@Valid @RequestBody TbUser tbUser, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username",tbUser.getUsername());
        TbUser tbUser1 = tbUserMapper.selectOneByExample(example);
        if(StringUtils.isNotNull(tbUser1)) {
            if ( !tbUser.getPassword().equals(tbUser1.getPassword())) {
                String message = String.format("登陆失败，详细信息[密码不正确]。");
                return ResultFactory.buildFailResult(message);
            }else {
                session.setAttribute("currentUser",tbUser1);
                return ResultFactory.buildSuccessResult(tbUser1);
            }
        }else {
            String message = String.format("登陆失败，详细信息[用户名不正确]。");
            return ResultFactory.buildFailResult(message);
        }

    }
}
