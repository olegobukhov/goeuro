package com.goeuro.test.controller;


import com.goeuro.test.model.Location;
import com.goeuro.test.service.FileExporterService;
import com.goeuro.test.service.LocationSuggesterService;
import com.goeuro.test.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("controller")
public class SuggestionsControllerImpl implements SuggestionsController {
    private static Logger log = LoggerFactory.getLogger(SuggestionsControllerImpl.class);

    @Autowired
    private LocationSuggesterService suggesterService;

    @Autowired
    private FileExporterService<Iterable<Location>> fileExporterService;

    public void suggestLocations(String cityName) throws ControllerException {
        List<Location> locations;
        File exportedFile;
        try {
            locations = suggesterService.getSuggestionsForCity(cityName);
            exportedFile = fileExporterService.export(locations);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        log.info("File exported: {}", exportedFile);
    }
}
