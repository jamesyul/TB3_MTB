package pe.edu.upc.trabajofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	
	//public String response() {
		//return "index";
	@GetMapping()
	public String layout() {
		return "layout/layout";
	}


}
