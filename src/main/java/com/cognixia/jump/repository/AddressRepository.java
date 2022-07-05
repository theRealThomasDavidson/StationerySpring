package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
