package com.shop.home.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.home.Pojo.ShopEntity;

public interface ShopRepo extends JpaRepository<ShopEntity, Integer> {

	
	
}
