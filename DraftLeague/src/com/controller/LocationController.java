package com.controller;

import com.common.CountryDTO;
import com.common.State;
import com.model.LocationDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocationController {
    
    @Autowired
    private LocationDAO locationDAO;
    
    @RequestMapping(value="/state-list.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<State> stateList(@RequestParam(defaultValue = "0") int countryId, @RequestParam(defaultValue =  "false") boolean includeAll) {
        return locationDAO.getStateList(countryId, includeAll);
    }
    
    @RequestMapping(value="/country-list.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CountryDTO> countryList(@RequestParam(defaultValue =  "false") boolean includeAll) {
        return locationDAO.getCountryList(includeAll);
    }
    
}
