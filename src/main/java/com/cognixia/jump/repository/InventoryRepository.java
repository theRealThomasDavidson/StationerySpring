package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
	@Query(value = "select * from inventory i"
			+ "	where i.inventory > 0", 
			nativeQuery = true)
	public List<Inventory> instockInventory();
	
	@Query(value = "select * from inventory i"
			+ "	where i.name = ?1"
			+ "	and i.inventory > ?2", 
			nativeQuery = true)
	public Optional<Inventory> enoughInventoryInstock(String name, Integer needed);
	public Optional<Inventory> findByName(String name);
}
