package com.assa.project.counterapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.assa.project.counterapi.entity.Counter;
import com.assa.project.counterapi.exception.CounterNotFoundException;
import com.assa.project.counterapi.exception.IncrementWrongException;
import com.assa.project.counterapi.service.CounterService;

@RestController
@RequestMapping("/api/counters")
public class CounterController {
	
	@Autowired
	private CounterService counterService;
	
	
	/* List of all counters and their values */
	/* http://localhost:8080/api/counters */
	@GetMapping
	public Iterable<Counter> findAllCounters() {
		return counterService.findAll();
	}
	
	
	/* Find a counter and its value by it's name */
	/* http://localhost:8080/api/counters?name=... */
	@GetMapping(params="name")
	public Counter findByTitle(@RequestParam("name") String counterName){
		
		Counter counter = counterService.findByName(counterName);
		
		if(counter == null) {
			throw new CounterNotFoundException("name-" + counterName);
		}
		
		return counter;
	}
	
	
	/* increment a counter value by its name /*
	/* http://localhost:8080/api/counters?name=...&increment */
	//Or
	/* http://localhost:8080/api/counters?name=...&increment=true */
	@PutMapping
    public Counter updateCounter(@RequestParam(name="name") String counterName,
    		                     @RequestParam(name="increment", defaultValue = "true") String doIncrement) {
		Counter counter = counterService.findByName(counterName);
		
		if(counter == null) {
			throw new CounterNotFoundException("name-" + counterName);
		}
		
		if(doIncrement.equalsIgnoreCase("true")) {
			counter = counterService.update(counterName);
		} else {
			throw new IncrementWrongException("increment value-" + doIncrement);
		}
		
		return counter;
	}
	
	
	 /* Creates new counter (counter name is unique, duplication is not allowed) */
	 /* http://localhost:8080/api/counters */
	 @PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Counter create(@RequestBody Counter counter) {
		 return counterService.save(counter);
	 }

}
