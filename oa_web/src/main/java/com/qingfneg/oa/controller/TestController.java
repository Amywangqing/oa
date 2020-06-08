package com.qingfneg.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/hello.do")
	@ResponseBody
	public String hello() {
		return "Hello";
	}

	@RequestMapping("/he")
	@ResponseBody
	public String he() {
		return "Hello";
	}
	
}
