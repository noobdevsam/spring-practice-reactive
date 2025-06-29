package com.example.springpracticereactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

/**
 * LogbookConfig is a Spring configuration class that sets up logging
 * using Zalando Logbook with Logstash integration.
 */
@Configuration
public class LogbookConfig {

    /**
     * Creates a Sink bean for Logbook that uses Logstash for logging.
     * The Sink is configured with a JSON HTTP log formatter.
     *
     * @return A configured LogstashLogbackSink instance.
     */
    @Bean
    public Sink logbookLogStash() {
        // Create a JSON HTTP log formatter for structured logging.
        HttpLogFormatter formatter = new JsonHttpLogFormatter();

        // Return a Logstash sink configured with the JSON formatter.
        return new LogstashLogbackSink(formatter);
    }
}