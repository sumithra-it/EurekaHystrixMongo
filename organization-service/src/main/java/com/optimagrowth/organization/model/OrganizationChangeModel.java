package com.optimagrowth.organization.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString 
public class OrganizationChangeModel {
	String action;
	String organizationId;
	String correlationId;
	
	public OrganizationChangeModel(String type, String message, String orgId, String correlationId) {
		this.action = message; this.organizationId = orgId; this.correlationId = correlationId;
	}
}
