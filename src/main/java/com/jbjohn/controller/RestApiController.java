package com.jbjohn.controller;

import com.jbjohn.connectors.ElasticsearchConnector;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jbjohn.connectors.ElasticsearchWrapper;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 */
@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConnector.class);

    @RequestMapping("")
    public Map<String, Object> home() {

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Spring gradle");

        return model;
    }
    
    @RequestMapping("/search")
    public String search() {
        Client client = ElasticsearchWrapper.getClient();
        SearchResponse response = client.prepareSearch("index")
        .setTypes("type")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("multi", "1"))
        .setFrom(0).setSize(60).setExplain(true)
        .execute()
        .actionGet();
        
        return response.getHits().toString();
    }

    @RequestMapping("/get")
    public GetResponse get() {
        Client client = ElasticsearchWrapper.getClient();
        return client.prepareGet("index", "type", "1").get();
    }

    @RequestMapping("/put")
    public Boolean put() {
        Client client = ElasticsearchWrapper.getClient();
        try {

            IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                    .source(jsonBuilder()
                            .startObject()
                            .field("id", "1")
                            .field("name", "Joe Smith")
                            .field("description", "Joe Smith description")
                            .endObject());
            UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
                    .doc(jsonBuilder()
                            .startObject()
                            .field("id", "1")
                            .field("name", "Joe Smith")
                            .field("description", "Joe Smith description")
                            .endObject())
                    .upsert(indexRequest);

            UpdateResponse response = client.update(updateRequest).get();

            return response.isCreated();
        } catch (IOException | InterruptedException | ExecutionException e) {
            LOGGER.error("Exception sending data to ES", e);
        }
        return false;
    }
}
