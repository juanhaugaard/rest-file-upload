/**
 * This computer program is protected by United States copyright law and
 * international treaties. Unauthorized distribution or reproduction of this
 * computer program, or any portion of it, is strictly prohibited, may result in
 * severe civil penalties and criminal penalties, and will be prosecuted to the
 * maximum extent of the law. In addition, this computer program, portions of
 * this program, and the associated functionality may be considered a trade
 * secret. Unauthorized disclosure, reproduction, or distribution of the
 * computer program, or any portion of it, will be prosecuted to the maximum
 * extent of the law.
 * <p>
 * Copyright 2017 S&C Electric Company.
 */
package com.example.service;

import com.example.model.FileMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public class FileServiceImpl implements FileService {
  public static final String FS_ROOT  = "filestore";
  public static final String METADATA = "metadata.json";

  @Override
  public void store(FileMetadata metadata, InputStream stream) throws Exception {
    if ((metadata == null) || (stream == null) || StringUtils.isEmpty(metadata.getId()))
      throw new IllegalArgumentException("Invalid parameters to store");
    storeMetadata(metadata);
    Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), metadata.getFileName());
    Files.createDirectories(path.getParent());
    Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
  }

  @Override
  public OutputStream retrieveFile(String id) throws Exception {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Invalid parameter to retrieveFile");
    FileMetadata metadata = retrieveMetadata(id);
    Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), metadata.getFileName());
    ByteArrayOutputStream ret = new ByteArrayOutputStream();
    Files.copy(path, ret);
    return ret;
  }

  private void storeMetadata(FileMetadata metadata) throws Exception {
    if ((metadata == null) || StringUtils.isEmpty(metadata.getId()))
      throw new IllegalArgumentException("Invalid parameters to storeMetadata");
    Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), METADATA);
    Files.createDirectories(path.getParent());
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(metadata);
    Writer writer = Files.newBufferedWriter(path);
    writer.write(json);
    writer.flush();
    writer.close();
  }

  @Override
  public FileMetadata retrieveMetadata(String id) throws Exception {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Invalid parameter to retrieveMetadata");
    Path path = FileSystems.getDefault().getPath(FS_ROOT, id, METADATA);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(path.toFile(), FileMetadata.class);
  }
}
