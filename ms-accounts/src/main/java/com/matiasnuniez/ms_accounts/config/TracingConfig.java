package com.matiasnuniez.ms_accounts.config;

import brave.Tracing;
import brave.sampler.Sampler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import io.micrometer.tracing.handler.DefaultTracingObservationHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

@Configuration
public class TracingConfig {

    @Value("${management.zipkin.tracing.endpoint:http://localhost:9411/api/v2/spans}")
    private String zipkinEndpoint;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${management.tracing.sampling.probability:1.0}")
    private float samplingProbability;

    @Bean
    public URLConnectionSender zipkinSender() {
        return URLConnectionSender.create(zipkinEndpoint);
    }

    @Bean(destroyMethod = "close")
    public AsyncZipkinSpanHandler spanHandler(URLConnectionSender sender) {
        return AsyncZipkinSpanHandler.create(sender);
    }

    @Bean(destroyMethod = "close")
    public Tracing tracing(AsyncZipkinSpanHandler spanHandler) {
        return Tracing.newBuilder()
                .localServiceName(applicationName)
                .addSpanHandler(spanHandler)
                .sampler(Sampler.create(samplingProbability))
                .build();
    }

    @Bean
    public BraveTracer braveTracer(Tracing tracing) {
        BraveCurrentTraceContext context = new BraveCurrentTraceContext(tracing.currentTraceContext());
        return new BraveTracer(tracing.tracer(), context, new BraveBaggageManager());
    }

    @Bean
    public ObservationRegistryCustomizer<ObservationRegistry> tracingObservationCustomizer(BraveTracer tracer) {
        return registry -> registry.observationConfig()
                .observationHandler(new DefaultTracingObservationHandler(tracer));
    }
}
