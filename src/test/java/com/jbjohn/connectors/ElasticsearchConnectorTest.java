package com.jbjohn.connectors;

import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;

/**
 * Connector test
 */
public class ElasticsearchConnectorTest {

    private ElasticsearchConnector elasticsearchConnector;
    private Client client;
    
    public void setUp() throws Exception {
        elasticsearchConnector = new ElasticsearchConnector();
    }
    
    public void create() throws Exception {
        client = elasticsearchConnector.create();
    }

    public void destroy() throws Exception {
        elasticsearchConnector.destroy(client);
    }

}