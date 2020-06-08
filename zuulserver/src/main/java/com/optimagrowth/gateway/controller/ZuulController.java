package com.optimagrowth.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="zuultest")
public class ZuulController {
    
	@RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity<String> getOrganization() {
        return ResponseEntity.ok("Request received Successfully!");
    }

}
