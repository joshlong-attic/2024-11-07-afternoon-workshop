package com.example.service.adoptions;

import org.springframework.modulith.events.Externalized;

// spring for apache kafka
// spring amqp 
// spring integration
// ...
@Externalized(target = "channelName")
public record DogAdoptionEvent(int dogId) {
}
