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


import pe.edu.upc.trabajofinal.Service.CustomerService;
import pe.edu.upc.trabajofinal.model.entity.Bike;
import pe.edu.upc.trabajofinal.model.entity.Customer;

@Controller
@RequestMapping("/customers")
@SessionAttributes("/customerEdit")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public String list(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			List<Customer>customers=customerService.getAll();
			model.addAttribute("customers", customers);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "customers/list";
	}
	@GetMapping("{id}")
	public String findById (Model model, @PathVariable("id") Integer id) {
		try {
			Optional<Customer> optional = customerService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("customer", optional.get());
				return "customers/view";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "redirect:/customers";
	}
	//--------Edit -----------------------------
	@GetMapping("{id}/edit")
	public String editById (Model model,@PathVariable("id")Integer id) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {Optional<Customer> optional = customerService.findById(id);
		if (optional.isPresent()) {
			model.addAttribute("customerEdit", optional.get());
			return "customers/edit";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}		
		return "redirect:/customers";
		
	}
	@PostMapping("saveedit")
	public String saveEdit(Model model, @ModelAttribute("customerEdit") Customer customer) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			customerService.update(customer);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}	
			
		return "redirect:/customers";
	}
	// -----------------New----------------------
		@GetMapping("new")	// GET: /region/new
		public String newcustomer(Model model) {
			Bike bikeSearch = new Bike();
			model.addAttribute("bikeSearch",bikeSearch);
			Customer customer = new Customer();
			model.addAttribute("customerNew", customer);
			return "customers/new";
		}
		@PostMapping("savenew")	// POST: /region/savenew
		public String saveNew(Model model, @ModelAttribute("customerNew") Customer customer) {		
			try {
				customerService.create(customer);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}		
			return "redirect:/customers";
		}	
		//---------- Delete---------------
		@GetMapping("{id}/del")
		public String delcustomer(@PathVariable("id") Integer id ) {
			try {
				Optional<Customer> optional = customerService.findById(id);
				if (optional.isPresent()) {
					customerService.deleteById(id);
				}			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/customers";
		}

}
