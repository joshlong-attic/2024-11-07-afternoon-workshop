package com.example.service.vet;

import com.example.service.adoptions.DogAdoptionEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class Dogtor {
    
    @ApplicationModuleListener 
    void checkup(DogAdoptionEvent dogAdoptionEvent) throws Exception {
        System.out.println("start: checking up on " + dogAdoptionEvent);
        Thread.sleep(5_000);
        System.out.println("stop: checking up on " + dogAdoptionEvent);
    }
}
