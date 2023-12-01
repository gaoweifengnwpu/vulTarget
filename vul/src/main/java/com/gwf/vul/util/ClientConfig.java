package com.gwf.vul.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;


@Component
public class ClientConfig {
    @Value("${spring.elasticsearch.host}")
    private String host;
    @Value("${spring.elasticsearch.port}")
    private Integer port;
    @Value("${spring.elasticsearch.http}")
    private String http;
    @Value("${spring.elasticsearch.username}")
    private String esUsername;
    @Value("${spring.elasticsearch.password}")
    private String esPassword;
    @Value("${spring.elasticsearch.cafingerprint}")
    private String fingerprint;


    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        RestClient restClient = RestClient.builder(new HttpHost(host, port, http)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}


