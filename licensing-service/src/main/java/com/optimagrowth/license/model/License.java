package com.optimagrowth.license.model;

import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@Document(collection ="licenses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class License { //extends RepresentationModel<License> {

	@Id
	@Field(name = "license_id")
	private String licenseId;
	
	private String description;
	
	@Field(name = "organization_id")
	@NotNull
	private String organizationId;
	
	@Field(name = "product_name")
	@NotNull
	private String productName;
	
	@Field(name = "license_type")
	@NotNull
	private String licenseType;
	
	@Field(name="comment")
	private String comment;
	
	@Transient
	private String organizationName;
	@Transient
	private String contactName;
	@Transient
	private String contactPhone;
	@Transient
	private String contactEmail;


	public License withComment(String comment){
		this.setComment(comment);
		return this;
	}
}