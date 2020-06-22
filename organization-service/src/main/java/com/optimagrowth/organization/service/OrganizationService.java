package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.events.source.SimpleSourceBean;

@Service
public class OrganizationService {
	
    @Autowired
    private OrganizationRepository repository;
    
    @Autowired
    SimpleSourceBean simpleSourceBean;
    
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    @Cacheable(cacheNames = "myCache")
    public String cacheThis(){
    	logger.debug("Returning NOT from cache!");
        return "this Is it";
    }
    
    //do not cache all. Cache needs to be refreshed, if there are update calls.
    public List<Organization> findAll(){
    	return repository.findAll();
    }
    
	@Cacheable(value="org", key="#id")
    public Organization findById(String id) {
    	logger.debug("Received call for fetch by Id");
    	//runRandomlyLongCall();
    	Optional<Organization> opt = repository.findById(id);
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

	@Cacheable(value="org")
    public Organization create(Organization organization){
    	organization.setId( UUID.randomUUID().toString());
        organization = repository.save(organization);
        
// add the kafka event published
        return organization;

    }

	@CachePut(value="org", key="#organization.id")
    public void update(Organization organization){
    	repository.save(organization);
    	
    	//adding the kafka event publisher
    	simpleSourceBean.publishOrgChange("UPDATE event", organization.getId());
    }

	@CacheEvict(value="org", key="#organization.id")
    public void delete(Organization organization){
    	repository.deleteById(organization.getId());
    }
}