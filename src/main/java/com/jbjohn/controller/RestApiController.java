package com.jbjohn.controller;

import com.jbjohn.connectors.ElasticsearchConnector;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jbjohn.connectors.ElasticsearchWrapper;
import com.jbjohn.properties.Configurations;
import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.beans.factory.annotation.Autowired;

/**
 */
@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConnector.class);

    @Autowired
    private Configurations config;

    @RequestMapping("")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<>();
        return model;
    }

    @RequestMapping("/partSearch")
    public Map<String, Object> partSearch(@RequestParam(required = false)
                                      final String q) {

        /**
         * @TODO Improve this part
         * If there are no results or if the query is incomplete add AI.
         */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("count", 0);

        if (q != null) {
            Client client = ElasticsearchWrapper.getClient(config);
            SearchResponse response = client.prepareSearch()
                    .setQuery(QueryBuilders.queryStringQuery(q))
                    .execute()
                    .actionGet();

            SearchHit[] results = response.getHits().getHits();
            ArrayList<Map<String, Object>> resultsArray = new ArrayList<>();
            for(SearchHit hit : results){
                Map<String, Object> resultMap = hit.sourceAsMap();
                if (resultMap != null) {
                    resultsArray.add(resultMap);
                }
            }
            responseMap.put("count", response.getHits().getTotalHits());
            responseMap.put("time", response.getTook().toString());
            responseMap.put("maxScore", response.getHits().getMaxScore());
            responseMap.put("results", resultsArray);
        }

        return responseMap;
    }
}
