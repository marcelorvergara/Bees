package com.flixnet.BffWebService;

import com.flixnet.BffFeign.OMDbClientService;
import com.flixnet.BffModel.OMDbSearchResult.SearchResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OMDbController {
    
    @Autowired
    public OMDbClientService clientSvc;
    
    @Value("${OMDBKey}")
    private String OMDBKey;
    
    // search result by typing a search
    @RequestMapping(path = "/all", method=RequestMethod.GET)
    public List<SearchResult> getTitles(@RequestParam(value="search", required = false) String search){    
        System.out.println("OMDBKey: " + OMDBKey);
        return clientSvc.searchTitle(search, OMDBKey).getSearch();        
    }
}
