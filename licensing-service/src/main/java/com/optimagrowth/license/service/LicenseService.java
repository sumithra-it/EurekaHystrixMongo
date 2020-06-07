package com.optimagrowth.license.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import com.optimagrowth.license.utils.UserContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@DefaultProperties(
		commandProperties = {
				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", 
								 value="3000")
		})
public class LicenseService {

	@Autowired
	MessageSource messages;

	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	ServiceConfig config;

	@Autowired
	OrganizationFeignClient organizationFeignClient;

	@Autowired
	OrganizationRestTemplateClient organizationRestClient;

	@Autowired
	OrganizationDiscoveryClient organizationDiscoveryClient;

	private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
	
	public License getLicense(String licenseId, String organizationId, String clientType){
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		if (null == license) {
			throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null),licenseId, organizationId));	
		}

		Organization organization = retrieveOrganizationInfo(organizationId, clientType);
		if (null != organization) {
			license.setOrganizationName(organization.getName());
			license.setContactName(organization.getContactName());
			license.setContactEmail(organization.getContactEmail());
			license.setContactPhone(organization.getContactPhone());
		}

		return license.withComment(config.getExampleProperty());
	}

	private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
		Organization organization = null;

		switch (clientType) {
		case "feign":
			System.out.println("I am using the feign client");
			organization = organizationFeignClient.getOrganization(organizationId);
			break;
		case "rest":
			System.out.println("I am using the rest client");
			organization = organizationRestClient.getOrganization(organizationId);
			break;
		case "discovery":
			System.out.println("I am using the discovery client");
			organization = organizationDiscoveryClient.getOrganization(organizationId);
			break;
		default:
			organization = organizationRestClient.getOrganization(organizationId);
			break;
		}

		return organization;
	}

	public License createLicense(License license){
		license.setLicenseId(UUID.randomUUID().toString());
		licenseRepository.save(license);

		return license.withComment(config.getExampleProperty());
	}

	public License updateLicense(License license){
		licenseRepository.save(license);

		return license.withComment(config.getExampleProperty());
	}

	public String deleteLicense(String licenseId){
		String responseMessage = null;
		License license = new License();
		license.setLicenseId(licenseId);
		licenseRepository.delete(license);
		responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId);
		return responseMessage;

	}

/*	@HystrixCommand(fallbackMethod="buildFallbackLicenseList",
			threadPoolKey= "licesesByOrgThreadPool",
			threadPoolProperties= {
			@HystrixProperty(name="coreSize", value="30"),
			@HystrixProperty(name="maxQueueSize", value="10")
			},
			commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
			@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
			@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
			@HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")
			})*/
	@HystrixCommand(fallbackMethod="buildFallbackLicenseList",
			threadPoolKey= "getLicencesServiceThreadPool",
			commandKey="getLicencesCommand")
	public List<License> getLicensesByOrganization(String organizationId) {
		logger.debug("LicenseService Correlation Id: " + 
				UserContextHolder.getContext().getCorreleationId());
		runRandomlyLongCall();
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	private List<License> buildFallbackLicenseList(String organizationId) {
		List<License> licenseArr = new ArrayList<>();
		License lic = new License();
		lic.setLicenseId("0000000000");
		lic.setLicenseType("None");
		lic.setOrganizationId(organizationId);
		lic.setComment("No License found in the databse");
		licenseArr.add(lic);
		return licenseArr;
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
}
