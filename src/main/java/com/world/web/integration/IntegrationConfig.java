package com.world.web.integration;

import com.world.web.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class IntegrationConfig {

    @Autowired
    private Transformer transformer;

   // @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlow.from(fileReader(),
                        spec -> spec.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();
    }

    //@Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("destination"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.IGNORE);
        handler.setDeleteSourceFiles(true);
        return handler;
    }

    //@Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("source"));
        return source;
    }
}
