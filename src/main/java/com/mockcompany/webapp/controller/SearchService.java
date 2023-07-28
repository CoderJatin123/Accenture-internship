package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.model.ProductItem;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class SearchService {

    @Autowired
     static Map<String,List<ProductItem>> searchService(Iterable<ProductItem> allItems, String query, Boolean exactMatch, Pattern kidPattern){

        Map<String,List<ProductItem>> result = new HashMap<>();
        List<ProductItem> itemList = new ArrayList<>();

        List<ProductItem> kidCountList = new ArrayList<>();


        for (ProductItem item : allItems) {
            boolean nameMatches;
            boolean descMatches;

            if (exactMatch) {
                // Check if name is an exact match
                nameMatches = query.equals(item.getName());
                // Check if description is an exact match
                descMatches = query.equals(item.getDescription());
            } else {
                // We are doing a contains ignoring case check, normalize everything to lowercase
                // Check if name contains query
                nameMatches = item.getName().toLowerCase().contains(query);
                // Check if description contains query
                descMatches = item.getDescription().toLowerCase().contains(query);
            }

            // If either one matches, add to our list
            if (nameMatches || descMatches) {
                itemList.add(item);
            }

            if(kidPattern!=null)
            if (kidPattern.matcher(item.getName()).matches() || kidPattern.matcher(item.getDescription()).matches()) {
                kidCountList.add(item);
            }
        }
        result.put("kidCountList",kidCountList);
        result.put("perfectCountList",itemList);
        return result;
    }
}
