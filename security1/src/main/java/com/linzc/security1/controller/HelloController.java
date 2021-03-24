package com.linzc.security1.controller;

import com.linzc.security1.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzc99
 * @create 2021-03-14 16:05
 */
@RequestMapping("/test")
@RestController
public class HelloController {
    //@Secured("ROLE_role") //有ROLE_role角色的用户才能访问此方法
    @PreAuthorize("hasAnyAuthority('admins','guest')") //有权限的用户才能访问此方法
    @GetMapping("hello")
    public String sayHello() {
        return "hello,spring security";
    }
    @GetMapping("index")
    public String index() {
        return "hello,index";
    }

    @RequestMapping("getAll")
    //@PreAuthorize("hasAnyAuthority('admins','guest')")
    @PostFilter("filterObject.username == 'admin1'")
    public List<Users> getAllUser(){
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(11,"admin1","6666"));
        list.add(new Users(22,"admin2","888"));
        return list;
    }
}
