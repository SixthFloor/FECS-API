package th.in.nagi.fecs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.in.nagi.fecs.message.ErrorMessage;
import th.in.nagi.fecs.message.Message;

@RestController
public class HTTPErrorHandler {

	@ResponseBody
	@RequestMapping(value = "/400")
	public Message error400() {
		return new Message("Bad Request");
	}

	@ResponseBody
	@RequestMapping(value = "/404")
	public Message error404() {
		return new Message("Page Not Found");
	}

	@ResponseBody
	@RequestMapping(value = "/500")
	public Message error500() {
		return new Message("Internal Server Error");
	}

}
