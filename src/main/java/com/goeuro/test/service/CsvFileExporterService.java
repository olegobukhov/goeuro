package com.goeuro.test.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.test.model.Location;
import com.goeuro.test.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by Olegdelone on 05.10.2016.
 */
@Service
public class CsvFileExporterService implements FileExporterService<Iterable<Location>> {
    private static Logger log = LoggerFactory.getLogger(CsvFileExporterService.class);

    private File pathTo = new File(System.getProperty("user.home"), "csv_export");

    public CsvFileExporterService() {
        log.debug("Checking {} dir...", pathTo);
        if (!pathTo.exists()) {
            try {
                FileUtils.mkdirs(pathTo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (!pathTo.isDirectory()) {
            throw new RuntimeException("Given path: " + pathTo + " is not a directory.");
        }
    }

    @Override
    public File export(Iterable<Location> locations) throws ServiceException {
        if (locations == null) {
            throw new IllegalArgumentException("Arg [List<Location> locations] cannot be null.");
        }
        File file = prepareFile();
        log.debug("Start exporting csv...");
        try (CSVWriter writer = new CSVWriter(new FileWriter(file), ';')) {
            String[] chunks = new String[]{"id_", "name", "type", "latitude", "longitude"};
            writer.writeNext(chunks);
            for (Location location : locations) {
                chunks[0] = String.valueOf(location.get_id());
                chunks[1] = location.getName();
                chunks[2] = location.getType();
                chunks[3] = String.valueOf(location.getPositionCoords().getLatitude());
                chunks[4] = String.valueOf(location.getPositionCoords().getLongitude());
                writer.writeNext(chunks);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return file;
    }

    private File prepareFile() throws ServiceException {
        File file;
        try {
            log.debug("Creating new tmp file...");
            file = File.createTempFile("export_", ".csv", pathTo);
            log.debug("File {} created.", file);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return file;
    }
}
