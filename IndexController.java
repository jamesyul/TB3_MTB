package pe.edu.upc.trabajofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.trabajofinal.model.entity.Bike;



@Controller
@RequestMapping("/")
public class IndexController {
	
	@GetMapping
	public String indexGet(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch", bikeSearch);
		return "index";
	}
	@GetMapping("login")
	public String login() {
		return "login";
	}
}
