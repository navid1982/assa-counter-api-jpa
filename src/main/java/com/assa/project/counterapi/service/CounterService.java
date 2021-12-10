package com.assa.project.counterapi.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assa.project.counterapi.entity.Counter;
import com.assa.project.counterapi.repository.CounterRepository;

@Transactional
@Service
public class CounterService {
	
	@Autowired
	CounterRepository counterRepository;
	
	public Iterable<Counter> findAll() {
		return counterRepository.findAll();
	}
	
	public Counter create(Counter counter) {
		return counterRepository.save(counter);
		
	}
	
	public Counter update(String counterName) {
		Counter counter = findByName(counterName);
		increment(counter);
		return counter;
	}
	
	private void increment(Counter counter) {
		counter.setValue(counter.getValue() + 1); 
                counterRepository.save(counter);
        }
	
	public Counter findByName(String name) {
		Counter counter = counterRepository.findByName(name);
		return counter;
	}
	
	public Counter save(Counter counter) {
		counterRepository.save(counter);
		return counter;
		
	}
}
