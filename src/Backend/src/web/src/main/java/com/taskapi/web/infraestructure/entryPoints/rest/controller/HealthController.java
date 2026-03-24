package com.taskapi.web.infraestructure.entryPoints.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
public class HealthController {

    @GetMapping("/health")
    public BodyBuilder healthCheck() {
        return ResponseEntity.ok();
    }
    
    
}
