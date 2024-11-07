package com.example.service.adoptions;

import com.example.service.adoptions.validation.Validation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
class DogAdoptionsController {

    private final DogAdoptionService service;

    DogAdoptionsController(DogAdoptionService service) {
        this.service = service;
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId, @RequestBody Map<String, String> owner) {
        this.service.adopt(dogId, owner.get("name"));
    }
}

@Transactional
@Service
class DogAdoptionService {

    private final ApplicationEventPublisher publisher;

    private final DogRepository repository;
    
    private final Validation validation;
    
    DogAdoptionService(ApplicationEventPublisher publisher, DogRepository repository, Validation validation) {
        this.publisher = publisher;
        this.repository = repository;
        this.validation = validation;
    }

    void adopt(int dogId, String owner) {
        this.repository.findById(dogId).ifPresent(dog -> {
            var newDog = this.repository.save(new Dog(dog.id(), dog.name(), dog.description(), owner));
            this.publisher.publishEvent(new DogAdoptionEvent(newDog.id()));
            System.out.println("adopted [" + newDog + "]");
        });
    }
}

interface DogRepository extends ListCrudRepository<Dog, Integer> {
}

// look mom, no Lombok!!!
record Dog(@Id int id, String name, String description, String owner) {
}

// DATA ORIENTED PROGRAMMING
// - records
// - smart switch 
// - sealed types
// - pattern matching

 


