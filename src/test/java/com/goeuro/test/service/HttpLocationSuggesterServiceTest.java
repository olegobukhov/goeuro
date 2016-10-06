package com.goeuro.test.service;


import com.goeuro.test.model.Location;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HttpLocationSuggesterServiceTest {
    private static Logger log = LoggerFactory.getLogger(HttpLocationSuggesterServiceTest.class);

    private final HttpLocationSuggesterService httpLocationSuggesterService = new HttpLocationSuggesterService();

    @Test
    public void unParseTest() throws IOException {
        String input = "[\n" +
                "\n" +
                " {\n" +
                "\n" +
                " _id: 377078,\n" +
                " key: null,\n" +
                " name: \"Potsdam\",\n" +
                " fullName: \"Potsdam, Germany\",\n" +
                " iata_airport_code: null,\n" +
                " type: \"location\",\n" +
                " country: \"Germany\",\n" +
                "\n" +
                " geo_position: {\n" +
                " latitude: 52.39886,\n" +
                " longitude: 13.06566\n" +
                " },\n" +
                " location_id: 377078,\n" +
                " inEurope: true,\n" +
                " countryCode: \"DE\",\n" +
                " coreCountry: true,\n" +
                " distance: null\n" +
                " },\n" +
                "\n" +
                " {\n" +
                " _id: 410978,\n" +
                " key: null,\n" +
                " name: \"Potsdam\",\n" +
                " fullName: \"Potsdam, USA\",\n" +
                " iata_airport_code: null,\n" +
                " type: \"location\",\n" +
                " country: \"USA\",\n" +
                "\n" +
                " geo_position: {\n" +
                " latitude: 44.66978,\n" +
                " longitude: -74.98131\n" +
                " },\n" +
                "\n" +
                " location_id: 410978,\n" +
                " inEurope: false,\n" +
                " countryCode: \"US\",\n" +
                " coreCountry: false,\n" +
                " distance: null\n" +
                " }\n" +
                " ]";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        List<Location> locations = httpLocationSuggesterService.unParse(inputStream);
        log.info("locations: {}", locations);
        Assert.assertTrue(locations.size() == 2);
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);

        Assert.assertEquals(l1.getName(), "Potsdam");
        Assert.assertEquals(l1.getType(), "location");
        Assert.assertTrue(l1.get_id() == 377078);
        Assert.assertTrue(l1.getPositionCoords().getLatitude() == 52.39886);
        Assert.assertTrue(l1.getPositionCoords().getLongitude() == 13.06566);

        Assert.assertEquals(l2.getName(), "Potsdam");
        Assert.assertEquals(l2.getType(), "location");
        Assert.assertTrue(l2.get_id() == 410978);
        Assert.assertTrue(l2.getPositionCoords().getLatitude() == 44.66978);
        Assert.assertTrue(l2.getPositionCoords().getLongitude() == -74.98131);
    }
}
