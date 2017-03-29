package com.example.service;

import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.jpa.FileMetadataConverter;
import com.example.jpa.FileMetadataEntity;
import com.example.jpa.FileMetadataRepository;
import com.example.model.FileMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class FileServiceImpl implements FileService {
  public static final String FS_ROOT  = "filestore";
  public static final String METADATA = "metadata.json";

  private FileMetadataRepository repository;
  
  @Autowired
  public FileServiceImpl(FileMetadataRepository repository) {
	  this.repository = repository;
  }
  
  @Override
  public void store(FileMetadata metadata, InputStream stream) throws Exception {
    if ((metadata == null) || (stream == null) || StringUtils.isEmpty(metadata.getId()))
      throw new IllegalArgumentException("Invalid parameters to store");
    storeMetadata(metadata);
    Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), metadata.getFileName());
    Files.createDirectories(path.getParent());
    Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
  }

  private void storeMetadata(FileMetadata metadata) throws Exception {
    if ((metadata == null) || StringUtils.isEmpty(metadata.getId()))
      throw new IllegalArgumentException("Invalid parameters to storeMetadata");
    // persist to database
    repository.save(FileMetadataConverter.domainToEntity(metadata));
    
    // persist to file for off-line consistency check purposes
    storeFSMetadata(metadata);
  }

  @Override
  @Transactional(readOnly=true)
  public InputStream retrieveFile(String id) throws Exception {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Invalid parameter to retrieveFile");
    FileMetadata metadata = retrieveMetadata(id);
    Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), metadata.getFileName());
    return Files.newInputStream(path, StandardOpenOption.READ);
  }
  
  @Override
  @Transactional(readOnly=true)
  public FileMetadata retrieveMetadata(String id) throws Exception {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Invalid parameter to retrieveMetadata");
    return FileMetadataConverter.entityToDomain(repository.findOne(id));
  }
  
  @Override
  @Transactional(readOnly=true)
  public Collection<FileMetadata> retrieveAllMetadata() {
	Collection<FileMetadata> ret = new LinkedList<FileMetadata>();
	for (FileMetadataEntity entity:repository.findAll())
	  ret.add(FileMetadataConverter.entityToDomain(entity));
  	return ret;
  }
  
  //persist to file for off-line consistency check purposes
  private void storeFSMetadata(FileMetadata metadata) throws Exception {
	if ((metadata == null) || StringUtils.isEmpty(metadata.getId()))
	  throw new IllegalArgumentException("Invalid parameters to storeFSMetadata");
	Path path = FileSystems.getDefault().getPath(FS_ROOT, metadata.getId(), METADATA);
	Files.createDirectories(path.getParent());
	ObjectMapper mapper = new ObjectMapper();
	String json = mapper.writeValueAsString(metadata);
	Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,StandardOpenOption.CREATE);
	writer.write(json);
	writer.flush();
	writer.close();
  }

  // retrieve Metadata from file, used by off-line consistency checks
  public FileMetadata retrieveFSMetadata(String id) throws Exception {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Invalid parameter to retrieveFSMetadata");
    Path path = FileSystems.getDefault().getPath(FS_ROOT, id, METADATA);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(path.toFile(), FileMetadata.class);
  }
}
