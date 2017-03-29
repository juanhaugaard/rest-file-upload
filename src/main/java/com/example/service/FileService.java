package com.example.service;

import com.example.model.FileMetadata;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public interface FileService {
  void store(FileMetadata metadata, InputStream stream) throws Exception;
  InputStream retrieveFile(String id) throws Exception;
  FileMetadata retrieveMetadata(String id) throws Exception;
  Collection<FileMetadata> retrieveAllMetadata();
}
