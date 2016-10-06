package com.goeuro.test.service;


import com.goeuro.test.model.Location;

import java.util.List;

public interface LocationSuggesterService {
    List<Location> getSuggestionsForCity(String cityName) throws ServiceException;
}
