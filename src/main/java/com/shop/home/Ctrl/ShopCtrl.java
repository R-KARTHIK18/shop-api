package com.shop.home.Ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.home.Model.ShopModel;
import com.shop.home.Pojo.ShopEntity;
import com.shop.home.Service.ShopService;

@Controller
public class ShopCtrl {
	
	@Autowired
	private ShopService service;
	
	@GetMapping("/allshop")
	@ResponseBody
	public List<ShopEntity> allshops() {
		return service.allshops();
	}
	
	@GetMapping("/shop/{id}")
	@ResponseBody
	public ShopModel shop(@PathVariable int id) {
		return service.shop(id);
	}

	@PostMapping("/addshop")
	@ResponseBody
	public ShopEntity addshops(@RequestBody ShopEntity sp) {
		return service.addshop(sp);
	}
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String del(@PathVariable int id) {
		return service.del(id);
	}
	
	@PutMapping ("/update")
	@ResponseBody
	public ShopEntity update(@RequestBody ShopEntity s) {
		return service.update(s);
	}
	
	
	// UI JSP pages
	@GetMapping("/home")
	public String homepage() {
		return "home";
	}
	
	@GetMapping("/signin")
	public String sign() {
		return "signin";
	}
	

}
