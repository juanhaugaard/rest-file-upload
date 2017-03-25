package com.example.service;

import com.example.model.FileMetadata;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public interface FileService {
  void store(FileMetadata metadata, InputStream stream) throws Exception;
  OutputStream retrieveFile(String id) throws Exception;
  FileMetadata retrieveMetadata(String id) throws Exception;
}
