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
import pe.edu.upc.trabajofinal.Service.TypeService;
import pe.edu.upc.trabajofinal.model.entity.Bike;
import pe.edu.upc.trabajofinal.model.entity.Type;

@Controller
@RequestMapping("/types")
@SessionAttributes("/typeEdit")
public class TypeController {
	@Autowired
	private TypeService typeService;
	
	@GetMapping
	public String list(Model model) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			List<Type>types=typeService.getAll();
			model.addAttribute("types", types);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "types/list";
	}
	@GetMapping("{id}")
	public String findById (Model model, @PathVariable("id") Integer id) {
		try {
			Optional<Type> optional = typeService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("type", optional.get());
				return "types/view";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "redirect:/types";
	}
	//--------Edit -----------------------------
	@GetMapping("{id}/edit")
	public String editById (Model model,@PathVariable("id")Integer id) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {Optional<Type> optional = typeService.findById(id);
		if (optional.isPresent()) {
			model.addAttribute("typeEdit", optional.get());
			return "types/edit";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}		
		return "redirect:/types";
		
	}
	@PostMapping("saveedit")
	public String saveEdit(Model model, @ModelAttribute("typeEdit") Type type) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);
		try {
			typeService.update(type);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}	
			
		return "redirect:/types";
	}
	// -----------------New----------------------
		@GetMapping("new")	// GET: /type/new
		public String newtype(Model model) {
			Bike bikeSearch = new Bike();
			model.addAttribute("bikeSearch",bikeSearch);
			Type type = new Type();
			model.addAttribute("typeNew", type);
			return "types/new";
		}
		@PostMapping("savenew")	// POST: /type/savenew
		public String saveNew(Model model, @ModelAttribute("typeNew") Type type) {		
			try {
				typeService.create(type);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}		
			return "redirect:/types";
		}	
		//---------- Delete---------------
		@GetMapping("{id}/del")
		public String deltype(@PathVariable("id") Integer id ) {
			try {
				Optional<Type> optional = typeService.findById(id);
				if (optional.isPresent()) {
					typeService.deleteById(id);
				}			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/types";
		}
}
