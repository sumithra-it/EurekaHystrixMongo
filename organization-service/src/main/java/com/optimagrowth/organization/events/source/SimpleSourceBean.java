package com.optimagrowth.organization.events.source;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.optimagrowth.organization.model.OrganizationChangeModel;
import com.optimagrowth.organization.utils.UserContext;
import com.optimagrowth.organization.events.stream.OrgChangeStreams;

@Slf4j
@Component
public class SimpleSourceBean {
	private final OrgChangeStreams orgChangeStreams;
	
	
	@Autowired
	public  SimpleSourceBean(OrgChangeStreams orgChangeStreams) {
		this.orgChangeStreams = orgChangeStreams;
	}
	
	@Autowired
	UserContext userContext;
	
	public void publishOrgChange(String message, String orgId) {
		log.info("Sending Kafka message {} for Organization Id {}", message, orgId);
		
		OrganizationChangeModel change = new OrganizationChangeModel(
												OrganizationChangeModel.class.getTypeName(),
												message,
												orgId,
												"corr-123");
											//	userContext.getCorreleationId());
		//sending message
		MessageChannel messageChannel = orgChangeStreams.sendoutboundMessage();
		boolean isSent = messageChannel.send(MessageBuilder.withPayload(change).build());
	//	boolean isSent = source.output().send(MessageBuilder.withPayload(change).build());
		if(isSent) 
			log.info("Message publishing was successfull from the producer");
		else log.info("Message publishing Failed in the producer");
	}
}
