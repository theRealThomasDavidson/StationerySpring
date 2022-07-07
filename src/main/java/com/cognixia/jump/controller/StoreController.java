package com.cognixia.jump.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Inventory;
import com.cognixia.jump.model.Item;
import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Order.Status;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.InventoryRepository;
import com.cognixia.jump.repository.ItemRepository;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	InventoryRepository invRepo;
	
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	UserRepository userRepo;
	
	
	@GetMapping("/inventory")
	public ResponseEntity<?> instock(){
		return ResponseEntity.status(200)
				.body(invRepo.instockInventory());
	}
	@PostMapping("/inventory")
	public ResponseEntity<?> newInventory(@Valid @RequestBody Inventory item){
		item.setId(null);
		Inventory created = invRepo.save(item);
		return ResponseEntity.status(201).body(created);
	}
	@GetMapping("/inventory/{name}/")
	public ResponseEntity<?> enoughInstock(@PathVariable String name) throws ResourceNotFoundException{
		Optional<Inventory> found = invRepo.findByName(name);
		if(found.isEmpty()) {
			return ResponseEntity.status(404).body("We don't have an item called "+name+".");
		}
		return ResponseEntity.status(200).body(found);
	}
	@GetMapping("/inventory/{name}/{amount}")
	public ResponseEntity<?> enoughInstock(@PathVariable String name, @PathVariable int amount) throws ResourceNotFoundException{
		Optional<Inventory> found = invRepo.enoughInventoryInstock(name, amount);
		if(found.isEmpty()) {
			return ResponseEntity.status(404).body("We don't have "+amount+" "+name+".");
		}
		return ResponseEntity.status(200).body(found);
	}
	@PutMapping("/inventory")
	public ResponseEntity<?> updateInventory(@Valid @RequestBody Inventory item) throws ResourceNotFoundException{
		Optional<Inventory> updated = invRepo.findById(item.getId());
		if(updated.isEmpty()) {
			throw new ResourceNotFoundException(item.getName() );
		}
		Inventory created = invRepo.save(item);
		return ResponseEntity.status(201).body(created);
	}
	@PostMapping("/order")
	public ResponseEntity<?> newOrder(@Valid @RequestBody Order order) throws ResourceNotFoundException{
		order.setId(null);
		Optional<User> user = userRepo.findByUsername(
				order.getUser()
				.getUsername());
		if(user.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		order.setUser(user.get());
		List<Item> items = new ArrayList<Item>();
		for(Item i: order.getItems()) {

			
			i.setId(null);
			
			Optional<Inventory> inv = invRepo.findByName(
					i.getInventory().getName());
			
			if(inv.isEmpty()) {
				throw new ResourceNotFoundException(i.getInventory().getName());
			}
			i.setInventory(inv.get());
			
			i.setOrder(order);
			items.add(i);
			
		}
		
		order.setItems(items);
		
		Order created = orderRepo.save(order);
		for(Item i: order.getItems()) {
			itemRepo.save(i);
		}
		order.setItems(items);
		return ResponseEntity.status(201).body(created);
	}
	private List<Item> orderItems(Order order){
		return itemRepo.findAllByOrder(order);
	}
	@GetMapping("/order/all")
	public ResponseEntity<?> allOrders(){

		for(Order o: orderRepo.findAll()) {
			o.setItems(orderItems(o));
		}
		return ResponseEntity.status(200)
				.body(orderRepo.findAll());
	}
	@GetMapping("/order/user/{id}")
	public ResponseEntity<?> orderByUser(@PathVariable Integer id) throws ResourceNotFoundException{
		for(Order o: orderRepo.haveSameUser(id)) {
			o.setItems(orderItems(o));
		}
		return ResponseEntity
				.status(200)
				.body(orderRepo.haveSameUser(id));
	}
	@GetMapping("/order/")
	public ResponseEntity<?> orderByid(Authentication authentication, 
			Principal principal) throws ResourceNotFoundException{
		
		Optional<User> user = 
				userRepo.findByUsername(authentication.getName());
		if(user.isEmpty()) {
			return ResponseEntity.status(404).body("User Not Found.");
		}
		Integer id = user.get().getId();
		List<Order> found = orderRepo.haveSameUser(id);
		for(Order o: orderRepo.haveSameUser(id)) {
			o.setItems(orderItems(o));
		}
		return ResponseEntity.status(200).body(found);
		
	}
	@PostMapping("/item")
	public ResponseEntity<?> newInventory(@Valid @RequestBody Item item){
		item.setId(null);
		Optional<Order> order =orderRepo.findById(item.getOrder().getId());
		if(order.isEmpty()) {
			return ResponseEntity.status(405).body("We don't have order with id = "+ item.getOrder().getId() +".");
		}
		Optional<Inventory> inv = invRepo.findByName(item.getInventory().getName());
		if(inv.isEmpty()) {
			return ResponseEntity.status(405).body("We don't have Inventory with id = "+ item.getInventory().getName() +".");
		}
		item.setOrder(order.get());
		item.setInventory(inv.get());
		Item created = itemRepo.save(item);
		return ResponseEntity.status(201).body(created);
	}
	@PutMapping("/inventory/ship")
	public ResponseEntity<?> markAsShipped(@Valid @RequestBody Order order) throws ResourceNotFoundException{
		Optional<Order> found = orderRepo.findById(order.getId());
		if(found.isEmpty()) {
			return ResponseEntity.status(404).body("We don't have order with id = "+ order.getId() +".");
		}
		found.get().setStatus(Status.IN_MAIL);
		Order updated = orderRepo.save(found.get());
		return ResponseEntity.status(201).body(updated);
	}
}