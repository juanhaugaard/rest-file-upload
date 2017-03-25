package com.example.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public class FileMetadataTest {
  private FileMetadata metadata;
  @Before
  public void setup(){
    metadata = new FileMetadata();
  }

  @Test
  public void testAddAuthorNull() {
    metadata.addAuthor(null);
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should be empty",0, metadata.getAuthors().size());
  }

  @Test
  public void testSetAuthorsNull() {
    metadata.setAuthors(null);
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should be empty",0, metadata.getAuthors().size());
  }

  @Test
  public void testAddAuthorEmpty() {
    metadata.addAuthor("");
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should be empty",0, metadata.getAuthors().size());
  }

  @Test
  public void testSetAuthorsEmpty() {
    metadata.setAuthors(new ArrayList<String>());
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should be empty",0, metadata.getAuthors().size());
  }

  @Test
  public void testAddAuthorOne() {
    String[] authors = {"First Author"};
    metadata.addAuthor(authors[0]);
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should have one author",1, metadata.getAuthors().size());
    Assert.assertEquals("Wrong first author",authors[0], metadata.getAuthors().toArray()[0]);
  }

  @Test
  public void testSetAuthorsOne() {
    String[] authors = {"First Author"};
    metadata.setAuthors(Arrays.asList(authors));
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should have one author",1, metadata.getAuthors().size());
    Assert.assertEquals("Wrong first author",authors[0], metadata.getAuthors().toArray()[0]);
  }

  @Test
  public void testAddAuthorThree() {
    String[] authors = {"First Author","Second Author","Third Author"};
    metadata.addAuthor(authors[0]);
    metadata.addAuthor(authors[1]);
    metadata.addAuthor(authors[2]);
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should have three authors",3, metadata.getAuthors().size());
    Assert.assertEquals("Wrong first author",authors[0], metadata.getAuthors().toArray()[0]);
    Assert.assertEquals("Wrong second author",authors[1], metadata.getAuthors().toArray()[1]);
    Assert.assertEquals("Wrong third author",authors[2], metadata.getAuthors().toArray()[2]);
  }

  @Test
  public void testSetAuthorsThree() {
    String[] authors = {"First Author","Second Author","Third Author"};
    metadata.setAuthors(Arrays.asList(authors));
    Assert.assertNotNull(metadata.getAuthors());
    Assert.assertEquals("Authors should have three authors",3, metadata.getAuthors().size());
    Assert.assertEquals("Wrong first author",authors[0], metadata.getAuthors().toArray()[0]);
    Assert.assertEquals("Wrong second author",authors[1], metadata.getAuthors().toArray()[1]);
    Assert.assertEquals("Wrong third author",authors[2], metadata.getAuthors().toArray()[2]);
  }
}
