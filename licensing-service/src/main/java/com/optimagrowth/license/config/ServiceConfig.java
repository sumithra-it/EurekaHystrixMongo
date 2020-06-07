package com.optimagrowth.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig{

//comment for local builds
//	@Value("${example.property}")
  private String exampleProperty;
  
  public String getExampleProperty(){
    return exampleProperty;
  }
  
}