package com.optimagrowth.organization.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
//mport org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optimagrowth.organization.model.Organization;

/* @Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    public Optional<Organization> findById(String organizationId);
} */

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    public Optional<Organization> findByName(String organizationId);
}