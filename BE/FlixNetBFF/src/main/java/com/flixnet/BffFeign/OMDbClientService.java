package com.flixnet.BffFeign;

import com.flixnet.BffModel.OMDbSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://www.omdbapi.com/", name = "OMDbClientService")
public interface OMDbClientService {
    
    @GetMapping("?apikey={OMDBKey}&s={search}")
    OMDbSearchResult searchTitle(@PathVariable("search") String search, @PathVariable("OMDBKey") String OMDBKey);
    
}
