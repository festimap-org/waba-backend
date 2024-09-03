package com.halo.eventer.infra;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {

    @GetMapping("/health")
    public void healthCheck(){
        
    }
}
