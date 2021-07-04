package pe.edu.upc.trabajofinal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import pe.edu.upc.trabajofinal.Service.BrandService;
import pe.edu.upc.trabajofinal.model.entity.Bike;
import pe.edu.upc.trabajofinal.model.entity.Brand;

@Controller
@RequestMapping("/brands")
@SessionAttributes("/brandEdit")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping
	public String list(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			List<Brand>brands=brandService.getAll();
			model.addAttribute("brands", brands);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "brands/list";
	}
	@GetMapping("{id}")
	public String findById (Model model, @PathVariable("id") Integer id) {
		try {
			Optional<Brand> optional = brandService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("brand", optional.get());
				return "brands/view";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "redirect:/brands";
	}
	//--------Edit -----------------------------
	@GetMapping("{id}/edit")
	public String editById (Model model,@PathVariable("id")Integer id) {
		try {Optional<Brand> optional = brandService.findById(id);
		if (optional.isPresent()) {
			model.addAttribute("brandEdit", optional.get());
			return "brands/edit";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}		
		return "redirect:/brands";
		
	}
	@PostMapping("saveedit")
	public String saveEdit(Model model, @ModelAttribute("brandEdit") Brand brand) {
		try {
			brandService.update(brand);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}	
			
		return "redirect:/brands";
	}
	// -----------------New----------------------
		@GetMapping("new")	// GET: /region/new
		public String newbrand(Model model) {
			Bike bikeSearch = new Bike();
			model.addAttribute("bikeSearch",bikeSearch);
			
			
			Brand brand = new Brand();
			model.addAttribute("brandNew", brand);
			return "brands/new";
		}
		@PostMapping("savenew")	// POST: /region/savenew
		public String saveNew(Model model, @ModelAttribute("brandNew") Brand brand) {		
			try {
				brandService.create(brand);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}		
			return "redirect:/brands";
		}	
		//---------- Delete---------------
		@GetMapping("{id}/del")
		public String delbrand(@PathVariable("id") Integer id ) {
			try {
				Optional<Brand> optional = brandService.findById(id);
				if (optional.isPresent()) {
					brandService.deleteById(id);
				}			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/brands";
		}

}
