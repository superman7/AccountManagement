package com.digitalchina.xa.it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.util.PushWeaverNotificationUtils;

@Controller
@RequestMapping(value = "/etl")
public class InsertTestController {
	@ResponseBody
	@GetMapping("/notification")
	public void insertTest(@RequestParam(name = "text", required = false)
	String text) {
		System.err.println(text);
		PushWeaverNotificationUtils pu = new PushWeaverNotificationUtils();
		pu.pushEtlNotification("renxlc,fannl", "ETL流程异常", text);
	}
	
}
