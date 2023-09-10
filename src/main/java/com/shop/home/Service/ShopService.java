package com.shop.home.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.home.Model.ShopModel;
import com.shop.home.Pojo.ShopEntity;
import com.shop.home.Repo.ShopRepo;

@Service
public class ShopService {
	
	@Autowired
	private ShopRepo sRepo;
	
	public List<ShopEntity> allshops() {
		return sRepo.findAll();
	}
	
	
	 public ShopEntity addshop (ShopEntity s) {
		 return sRepo.save(s);
	 }
	 
	 public ShopModel shop(int id) {
		 ModelMapper m=new ModelMapper();
		 ShopEntity sEntity=sRepo.findById(id).get();
		 ShopModel sModel=m.map(sEntity, ShopModel.class);
		 return sModel;
	 }
	 public String del(int id) {
		 sRepo.deleteById(id);
		 return id+" is Deleted";
	}
	 
	 public ShopEntity update(ShopEntity e) {
		 
		 ShopEntity id=sRepo.findById(e.getId()).orElse(null);
		 if(id!=null) {
			 id.setCity(e.getCity());
			 id.setEmail(e.getEmail());
			 id.setMobile(e.getMobile());
			 id.setName(e.getName());
			 id.setState(e.getState());
			 return sRepo.save(id);
		 }
		 return null;
	}
	 
}

