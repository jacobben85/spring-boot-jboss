package com.jbjohn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jbjohn.connectors.ElasticsearchWrapper;
import org.elasticsearch.client.Client;

import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;

/**
 */
@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class RestApiController {

    @RequestMapping("")
    public Map<String, Object> home() {

        Map<String, Object> model = new HashMap<>();
        model.put("title", "Spring gradle");

        return model;
    }
    
    @RequestMapping("/search")
    public String search() {
        Client client = ElasticsearchWrapper.getClient();
        SearchResponse response = client.prepareSearch("index1")
        .setTypes("type1")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("multi", "test"))
        .setFrom(0).setSize(60).setExplain(true)
        .execute()
        .actionGet();
        
        return response.getHits().toString();
    }
}
