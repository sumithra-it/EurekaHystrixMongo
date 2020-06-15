package com.optimagrowth.license.events.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrgChangeStreams {
	String INPUT = "inputchannel";
		
	@Input(INPUT)
	SubscribableChannel receiveInboundMessage();
	//to read meesages from a Kafka topic
}
