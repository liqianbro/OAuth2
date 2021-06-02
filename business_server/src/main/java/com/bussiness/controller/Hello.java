package com.bussiness.controller;

import com.bussiness.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lee
 * @date 2021/5/25 11:57 上午
 */
@RestController
public class Hello {

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/hello")
    public Result Hello() {
        Object result = "Hello~";
        return Result.ok(result);
    }
}
