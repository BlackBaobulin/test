package cn.blackbao.spark.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

	@RequestMapping("index.html")
	public String index() {
		
		return "hello";
	}
}
