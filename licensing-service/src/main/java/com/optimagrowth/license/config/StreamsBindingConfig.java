package com.optimagrowth.license.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

import com.optimagrowth.license.events.stream.OrgChangeStreams;

@Configuration
@EnableBinding(OrgChangeStreams.class)
public class StreamsBindingConfig {
	
}
