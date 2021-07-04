package pe.edu.upc.trabajofinal.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.upc.trabajofinal.Service.BikeService;
import pe.edu.upc.trabajofinal.Service.BrandService;
import pe.edu.upc.trabajofinal.Service.CompanyService;
import pe.edu.upc.trabajofinal.Service.TypeService;
import pe.edu.upc.trabajofinal.model.entity.Bike;
import pe.edu.upc.trabajofinal.model.entity.Brand;
import pe.edu.upc.trabajofinal.model.entity.Company;
import pe.edu.upc.trabajofinal.model.entity.Type;


@Controller
@RequestMapping("/bikes")
@SessionAttributes("/bikeEdit")
public class BikeController {
	@Autowired
	private BikeService bikeService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private BrandService brandService;
	
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
		
		return "bikes/list";
	}
	
	@GetMapping("{id}")
	public String findById (Model model, @PathVariable("id") Integer id) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);		
		try {
			Optional<Bike> optional = bikeService.findById(id);
			
			if (optional.isPresent()) {
				model.addAttribute("bike", optional.get());
				return "bikes/view2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "redirect:/bikes";
	}
	//--------Edit -----------------------------
	@GetMapping("{id}/edit")
	public String editById (Model model,@PathVariable("id")Integer id) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);			
		
		try {Optional<Bike> optional = bikeService.findById(id);
		if (optional.isPresent()) {
			model.addAttribute("bikeEdit", optional.get());
			return "bikes/edit";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}		
		return "redirect:/bikes";
		
	}
	@PostMapping("saveedit")
	public String saveEdit(Model model, @ModelAttribute("bikeEdit") Bike bike) {
		Bike bikeSearch = new Bike();
		model.addAttribute("bikeSearch",bikeSearch);		
		try {
			bikeService.update(bike);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}	
			
		return "redirect:/bikes";
	}
	// -----------------New----------------------
		@GetMapping("new")	// GET: /bike/new
		public String newbike(Model model) {
			Bike bikeSearch = new Bike();
			model.addAttribute("bikeSearch",bikeSearch);		
			try {
			Bike bike = new Bike();
			model.addAttribute("bikeNew", bike);
			List<Type> types=typeService.getAll();
			model.addAttribute("types", types );
			
			List<Company> companys=companyService.getAll();
			model.addAttribute("companys", companys );
			
			List<Brand> brands=brandService.getAll();
			model.addAttribute("brands", brands );
			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			
			return "bikes/new";
		}
		@PostMapping("savenew")	// POST: /bike/savenew
		public String saveNew(Model model, @RequestParam("file") MultipartFile imagen, @ModelAttribute("bikeNew") Bike bike) {			
			
			if(!imagen.isEmpty()) {
				Path directorioimagenes= Paths.get("src//main//resources//static//img");
				String rutaAbsoluta = directorioimagenes.toFile().getAbsolutePath();
				try {
					byte [ ] bytesimg = imagen.getBytes();
					Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+  imagen.getOriginalFilename());
					Files.write(rutaCompleta, bytesimg);
					bike.setImagen(imagen.getOriginalFilename());
				} catch (Exception e) {
					// TODO: handle exception			
				
				}	
			}
					
			try {		
				
				bikeService.create(bike);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}		
			return "redirect:/bikes";
			
		
			}
		
			
		//---------- Delete---------------
		@GetMapping("{id}/del")
		public String delcustomer(@PathVariable("id") Integer id ) {
			try {
				Optional<Bike> optional = bikeService.findById(id);
				if (optional.isPresent()) {
					bikeService.deleteById(id);
				}			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/bikes";
		}

	

}
