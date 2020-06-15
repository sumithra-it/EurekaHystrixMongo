package com.optimagrowth.license.events.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.optimagrowth.license.model.OrganizationChangeModel;
import com.optimagrowth.license.events.stream.OrgChangeStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrgChangeTopicListener {
	
	@StreamListener(OrgChangeStreams.INPUT)
	public void handleOrgChangeMsg(@Payload OrganizationChangeModel orgChange) {
		log.info("Received an event from kafka topic for org {} with orgId:{}", orgChange, orgChange.getOrganizationId());
	}
}
