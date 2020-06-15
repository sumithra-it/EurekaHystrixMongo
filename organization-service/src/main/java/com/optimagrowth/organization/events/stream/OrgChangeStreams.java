package com.optimagrowth.organization.events.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrgChangeStreams {
	String OUTPUT = "outputchannel";
		
	@Output(OUTPUT)
	MessageChannel sendoutboundMessage();
	//to write meesages to a Kafka topic
}
