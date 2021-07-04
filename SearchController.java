package pe.edu.upc.trabajofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import pe.edu.upc.trabajofinal.Service.BikeService;
import pe.edu.upc.trabajofinal.model.entity.Bike;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private BikeService bikeService;
	
	/*@GetMapping("bikes")
	public String searchBikeGet(Model model, @ModelAttribute("bikeSearch") Bike bikeSearch) {
		System.out.println(bikeSearch.getName());
		try {
			List<Bike> bikesFound = bikeService.findByNameStartingWith(bikeSearch.getName());
			model.addAttribute("bikesFound",bikesFound);
			model.addAttribute("bikeSearch",bikeSearch);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "search/bikes";
	}*/
	
	@PostMapping("bikes")		// POST: /search/employees
	public String searchBike(Model model , @ModelAttribute("bikeSearch") Bike bikeSearch) {
		System.out.println("LLEGO");
		List<Bike> bikes = null;
		try {
			bikes = bikeService
					.findByNameStartingWith(bikeSearch.getName());					
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("bikes", bikes);
		model.addAttribute("bikeSearch", bikeSearch);
		return "search/bikes";
	}
	
	

}
