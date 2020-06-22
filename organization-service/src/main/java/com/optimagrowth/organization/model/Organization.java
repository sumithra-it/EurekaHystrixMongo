package com.optimagrowth.organization.model;

import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@RedisHash("org")
@Document(collection = "organizations")
public class Organization implements Serializable{
    @Id
    String id;

    @NotNull(message = "User's first name must not be null")
    String name;

    @Field(name = "contact_name")
    @NotNull
    String contactName;

    @Field(name = "contact_email")
    @NotNull
    String contactEmail;

    @Field(name =  "contact_phone")
    @NotNull
    String contactPhone;
    
    private static final long serialVersionUID = 1223232323L;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", contactName=" + contactName + ", contactEmail="
				+ contactEmail + ", contactPhone=" + contactPhone + "]";
	}

}
