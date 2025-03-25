package com.world.web.integration;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;

@Configuration
public class SpringIntegrationConfig {


    @Bean
    public MessageChannel errorChannel() {
       return new DirectChannel();
    }

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel greeting() {
        return new DirectChannel();
    }

    public static String text() {
        return Math.random() > 0.5 ? "Hello World@" + Instant.now() +"!" :
                "Hola todo el munde@ "+Instant.now() +"!";
    }

    public String getText() {
        return Math.random() > 0.5 ? "Hello World@" + Instant.now() +"!" :
                "Hola todo el munde@ "+Instant.now() +"!";
    }

    //@Bean
    ApplicationRunner applicationRunner() {
        return args -> {
           for( var i = 0; i<10; i++) {
               greeting().send(MessageBuilder.withPayload(text()).build());
           }
        };
    }

    public static class MyMessageSource implements MessageSource<String> {

        /**
         * @return Message
         */
        @Override
        public Message<String> receive() {
            return MessageBuilder.withPayload(text()).build();
        }
    }
    @Bean
    public IntegrationFlow inboundFlow() {
       return IntegrationFlow
               .fromSupplier(this::getText, pollerSpec -> pollerSpec.poller(pm->pm.fixedDelay(1000)))
               //.filter(String.class, source -> source.contains("Hola"))
               .transform((GenericTransformer<String, String>) String::toUpperCase)
               .handle((GenericHandler<String>) (payload,headers) -> {
                  System.out.println("input adapter data :: "+payload);
                  return payload;
               })
               .channel(inputChannel())
               .get();
    }

    @Bean
    public IntegrationFlow inputFlow() {
        return IntegrationFlow.from(inputChannel())
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println("input channel data :: "+payload);
                    return payload;
                })
                .channel(outputChannel())
                .get();
    }

    @Bean
    public IntegrationFlow outputFlow() {
        return IntegrationFlow.from(outputChannel())
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println("output channel data :: "+payload.toLowerCase());
                    return payload;
                })
                .get();
    }

    @Bean
    public IntegrationFlow errorFlow() {
        return IntegrationFlow.from(errorChannel())
                .handle((GenericHandler<Object>) (payload, headers) -> {
                    System.out.println("error channel data :: "+ payload);
                    return null;
                })
                .get();
    }
}

//Inbound and Outbound Adapter