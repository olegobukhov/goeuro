package com.goeuro.test.service;

import java.io.File;

/**
 * Created by Olegdelone on 05.10.2016.
 */
public interface FileExporterService<T> {
    File export(T toBeExported) throws ServiceException;
}
