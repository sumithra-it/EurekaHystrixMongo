package com.optimagrowth.organization.controller;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;

@RestController
@RequestMapping(value="v1/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService service;

    private static final Logger logger = LoggerFactory.getLogger("OrganizationController.class");
    
    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganization( @PathVariable("organizationId") String organizationId) {
        logger.info("Request received for fetch organization by Id in the controller");
    	
        return ResponseEntity.ok(service.findById(organizationId));
    }
    
    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization( @PathVariable("organizationId") String id, @RequestBody Organization organization) {
    	logger.info("Request received for update organization by Id in controller");
    	service.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization>  saveOrganization(@RequestBody Organization organization) {
    	logger.info("Request received for create new organization in controller");
    	return ResponseEntity.ok(service.create(organization));
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("id") String id,  @RequestBody Organization organization) {
    	logger.info("Request received to delete organization by Id in controller");
    	service.delete(organization);
    }
    
    @GetMapping("/organization")
    public List<Organization> getAllFruit() {
    	logger.info("Fetch all Organizations");
        return service.findAll();
    }
    
    @GetMapping("/cachetest")
    public String cachetest() {
    	logger.info("Request received to test caching");
        return service.cacheThis();
    }

}
