package com.sample.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import uk.co.jemos.podam.api.PodamFactory;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Data
	static class SomeClass {
		private int id;
		private String name;
		private SomeEnum someEnum;
	}
	
	static enum SomeEnum {
		ONE, TWO
	}
	
	@RestController
	@RequestMapping("/someClass")
	class SomeController {
		
		@Autowired
		PodamFactory podamFactory;
		
		@RequestMapping(path="/{id}", method=RequestMethod.GET)
		SomeClass get(@PathVariable int id) {
			return podamFactory.manufacturePojo(SomeClass.class);
		}
		
		@RequestMapping(path="/", method=RequestMethod.PUT)
		SomeClass create(@RequestBody SomeClass someClass) {
			return podamFactory.manufacturePojo(SomeClass.class);
		}
		
		@RequestMapping(path="/", method=RequestMethod.POST)
		SomeClass update(@RequestBody SomeClass someClass) {
			return podamFactory.manufacturePojo(SomeClass.class);
		}
		
		@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
		SomeClass delete(@PathVariable int id) {
			return podamFactory.manufacturePojo(SomeClass.class);
		}
	}

}
