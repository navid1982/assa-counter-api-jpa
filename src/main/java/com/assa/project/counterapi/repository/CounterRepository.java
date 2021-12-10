package com.assa.project.counterapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.assa.project.counterapi.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {
	
	Counter findByName(String name);

}
