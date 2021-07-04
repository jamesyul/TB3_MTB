package pe.edu.upc.trabajofinal.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



import pe.edu.upc.trabajofinal.Service.BikeService;
import pe.edu.upc.trabajofinal.model.entity.Bike;

@Controller
@RequestMapping("/rentals")
public class RentController {

	@Autowired
	private BikeService bikeService;
	
	@GetMapping
	public String list(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);		
		try {
			List<Bike> bikes = bikeService.getAll();
			model.addAttribute("bikes",bikes);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());			
		}
		
		return "rentals/list-bike";
	}
	
	
	@GetMapping("bike/{id}")
	public String viewBikeGet(Model model, @ModelAttribute("bikeSearch") Bike bikeSearch, 
			@PathVariable("id") Integer id) {			
		try {
			Optional<Bike> optional = bikeService.findById(id);
			if(optional.isPresent()) {
				model.addAttribute("bike", optional.get());
				model.addAttribute("bikeSearch", bikeSearch);
				return "rentals/list-bike";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());			
		}
		
		return "redirect:/";
	}
}

