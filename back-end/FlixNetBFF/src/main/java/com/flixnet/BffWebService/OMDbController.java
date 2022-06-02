package com.flixnet.BffWebService;

import com.flixnet.BffFeign.OMDbClientService;
import com.flixnet.BffModel.OMDbSearchResult.SearchResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getTitles(@RequestParam(value = "search", required = false) String search) {
        if (search.length() > 2) {
            List<SearchResult> sr = clientSvc.searchTitle(search, OMDBKey).getSearch();
            if (sr == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(sr, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONTINUE);
    }
}
