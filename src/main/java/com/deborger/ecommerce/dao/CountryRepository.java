package com.deborger.ecommerce.dao;

import com.deborger.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// @CrossOrigin - removed using now CorsRegistry cors from MyDataRestConfig
@RepositoryRestResource(collectionResourceRel = "countries",path = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {


}