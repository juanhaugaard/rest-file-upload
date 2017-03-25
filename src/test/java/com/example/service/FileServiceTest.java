package com.example.service;

import com.example.model.FileMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public class FileServiceTest {
  private FileService service;
  private FileMetadata metadata;
  private String[] fileContents ={"Line One","Line Two","Line Three","Line Four"};

  @Before
  public void setup() {
    metadata = makeMetadata("Publication Title","publication.doc","Author One", "Author Two");
    service = new FileServiceImpl();
  }

  @Test
  public void storeFileTest() throws  Exception{
    String contents = String.join("\n", fileContents);
    service.store(metadata, makeInputStream(contents));
  }

  @Test
  public void readMetadataTest() throws  Exception{
    String contents = String.join("\n", fileContents);
    service.store(metadata, makeInputStream(contents));
    FileMetadata retrieved = service.retrieveMetadata(metadata.getId());
    Assert.assertEquals("Metadata should be the same",metadata,retrieved);
  }

  @Test
  public void readFileContentsTest() throws  Exception{
    String contents = String.join("\n", fileContents);
    service.store(metadata, makeInputStream(contents));
    ByteArrayOutputStream stream = (ByteArrayOutputStream)service.retrieveFile(metadata.getId());
    String retrieved = new String(stream.toByteArray(), StandardCharsets.UTF_8);
    Assert.assertEquals("Contents should be the same",contents,retrieved);
  }

  private FileMetadata makeMetadata(String title, String fileName, String... authors){
    FileMetadata ret = new FileMetadata();
    ret.setId("86f1dc77-68bc-4fc8-8e2a-5a627d4e3b78");
    ret.setTimestamp(new Date(1490420165514L));
    ret.setTitle(title);
    ret.setFileName(fileName);
    ret.setAuthors(Arrays.asList(authors));
    return ret;
  }
  private InputStream makeInputStream(String contents) {
    return new ByteArrayInputStream(contents.getBytes());
  }
}