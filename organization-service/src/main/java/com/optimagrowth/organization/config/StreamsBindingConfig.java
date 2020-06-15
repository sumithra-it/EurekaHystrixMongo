package com.optimagrowth.organization.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import com.optimagrowth.organization.events.stream.OrgChangeStreams;

@Configuration
@EnableBinding(OrgChangeStreams.class)
public class StreamsBindingConfig {
	
}
