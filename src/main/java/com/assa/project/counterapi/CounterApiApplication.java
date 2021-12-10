package com.assa.project.counterapi;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.assa.project.counterapi.entity.Counter;
import com.assa.project.counterapi.repository.CounterRepository;

@EnableJpaRepositories("com.assa.project.counterapi.repository") 
@EntityScan("com.assa.project.counterapi.entity")
@SpringBootApplication
public class CounterApiApplication implements CommandLineRunner {
	
	private static final Logger log = 
			LoggerFactory.getLogger(CounterApiApplication.class);
	
	@Autowired
	private CounterRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CounterApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Counter firstCounter = new Counter("counter1", 1);
		Counter secondCounter = new Counter("counter2", 1);
		
		repository.save(firstCounter);
		log.info("New Counter is created : " + firstCounter);
		
		repository.save(secondCounter);
		log.info("New Counter is created : " + secondCounter);
		
		Optional<Counter> counterWithIdOne = repository.findById(1L);
		log.info("New Counter is retrieved : " + counterWithIdOne);
		
		List<Counter> counters = repository.findAll();
		log.info("All Counters : " + counters);
		
		
	}

}
