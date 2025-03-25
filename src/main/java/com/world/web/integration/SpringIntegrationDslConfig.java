package com.world.web.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableIntegration
public class SpringIntegrationDslConfig {

    @Bean
    public AtomicInteger integerSource() {
        return new AtomicInteger();
    }

    //@Bean
    public IntegrationFlow integerDataFlow(AtomicInteger integerSource) {
        return IntegrationFlow.fromSupplier(integerSource::getAndIncrement,
                        c -> c.poller(Pollers.fixedRate(100)))
                .channel("integerInputChannel")
                .filter((Integer p) -> p > 0)
                .transform(Object::toString)
                .channel(MessageChannels.queue())
                .get();
    }

    //@Bean
    public IntegrationFlow integerCustomFlow() {
        return IntegrationFlow.from("integerInputChannel")
                .handle(data -> System.out.println("Message payload : "+ data.getPayload()))
                .get();
    }
}
