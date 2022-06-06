package com.venson.eduservice.controller;

import com.venson.commonutils.RMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/eduservice/user")
@RestController
@Slf4j
//@CrossOrigin
public class EduLoginController {
    @PostMapping("login")
    public RMessage login(){
        log.info("login");
       return RMessage.ok().data("token", "admin");
    }



    @GetMapping("info")
    public RMessage info(){
        return RMessage.ok().data("roles", "admin")
                .data("name","admin")
                .data("avatar", "https://online1.tingclass.net/fy/img/s/spitfire1.jpg?v0208");
    }
}
