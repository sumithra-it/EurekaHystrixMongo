package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {
	
    @Autowired
    private OrganizationRepository repository;
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    public Organization findById(String organizationId) {
    	logger.debug("Received call for fetch by Id");
    	//runRandomlyLongCall();
    	Optional<Organization> opt = repository.findById(organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }
    
	private void runRandomlyLongCall() {
		Random random = new Random();
		if ((random.nextInt(3) + 1) <= 2) {
			try{
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				logger.error("Thread interrupted in sleep:" + e.getMessage());
			}
		}
	}

    public Organization create(Organization organization){
    	organization.setId( UUID.randomUUID().toString());
        organization = repository.save(organization);
        return organization;

    }

    public void update(Organization organization){
    	repository.save(organization);
    }

    public void delete(Organization organization){
    	repository.deleteById(organization.getId());
    }
}