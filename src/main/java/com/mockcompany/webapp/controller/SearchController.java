/*
 * Java classes are grouped in "packages". This allows them to be referenced and used in other
 * classes using import statements.  Any class in this project is prefixed in the com.mockcompany.webapp
 * package.
 *
 *   https://www.w3schools.com/java/java_packages.asp
 *
 * For general help with Java, see the tutorialspoint tutorial:
 *
 *   https://www.tutorialspoint.com/java/index.htm
 */
package com.mockcompany.webapp.controller;

/*
 * An import statement allows the current class to use the class being imported
 */
import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
/* The springframework package allows us to take advantage of the spring capabilities */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* java.util package provides useful utilities */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class SearchController {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchController(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {

        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        boolean exactMatch = false;
        if (query.startsWith("\"") && query.endsWith("\"")) {
            exactMatch = true;
            // Extract the quotes
            query = query.substring(1, query.length() - 1);
        } else {
            // Handle case-insensitivity by converting to lowercase first
            query = query.toLowerCase();
        }

        // For each item... This is written for simplicity to be read/understood not necessarily maintain or extend
        itemList=SearchService.searchService(allItems,query,false,null).get("perfectCountList");

        return itemList;
    }
}
