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


import pe.edu.upc.trabajofinal.Service.CompanyService;
import pe.edu.upc.trabajofinal.model.entity.Bike;
import pe.edu.upc.trabajofinal.model.entity.Company;

@Controller
@RequestMapping("/companys")
@SessionAttributes("/companyEdit")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public String list(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			List<Company>companys=companyService.getAll();
			model.addAttribute("companys", companys);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "companys/list";
	}
	@GetMapping("{id}")
	public String findById (Model model, @PathVariable("id") Integer id) {
		try {
			Optional<Company> optional = companyService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("company", optional.get());
				return "companys/view";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "redirect:/companys";
	}
	//--------Edit -----------------------------
	@GetMapping("{id}/edit")
	public String editById (Model model,@PathVariable("id")Integer id) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {Optional<Company> optional = companyService.findById(id);
		if (optional.isPresent()) {
			model.addAttribute("companyEdit", optional.get());
			return "companys/edit";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}		
		return "redirect:/companys";
		
	}
	@PostMapping("saveedit")
	public String saveEdit(Model model, @ModelAttribute("companyEdit") Company company) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			companyService.update(company);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}	
			
		return "redirect:/companys";
	}
	// -----------------New----------------------
		@GetMapping("new")	// GET: /region/new
		public String newcompany(Model model) {
			Bike bikeSearch = new Bike();
			model.addAttribute("bikeSearch",bikeSearch);
			Company company = new Company();
			model.addAttribute("companyNew", company);
			return "companys/new";
		}
		@PostMapping("savenew")	// POST: /region/savenew
		public String saveNew(Model model, @ModelAttribute("companyNew") Company company) {		
			try {
				companyService.create(company);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}		
			return "redirect:/companys";
		}	
		//---------- Delete---------------
		@GetMapping("{id}/del")
		public String delcompany(@PathVariable("id") Integer id ) {
			try {
				Optional<Company> optional = companyService.findById(id);
				if (optional.isPresent()) {
					companyService.deleteById(id);
				}			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/companys";
		}

}
