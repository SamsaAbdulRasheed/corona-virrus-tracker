package com.prj.corona.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prj.corona.model.LocationState;
import com.prj.corona.services.CoronaService;



@Controller
public class HomeController {
	
	@Autowired
	CoronaService service;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationState> state= service.getAllstate();
		
		/* Stream<LocationState> stream= state.stream();
		 IntStream st1= stream.mapToInt(stat -> stat.getLastTotalDeath());
		 st1.sum(); */
		int totalDeath= state.stream().mapToInt(stat -> stat.getLastTotalDeath()).sum();
		model.addAttribute("totaldeath",totalDeath);
		model.addAttribute("locationState",state);
		return "home";
	}

}
