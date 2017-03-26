package com.example.model;

import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@XmlRootElement
public class FileMetadata {
  private String id;
  private String fileName;
  private String title;
  private List<String> authors = new LinkedList<>();
  private Date timestamp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Collection<String> getAuthors() {
    return Collections.unmodifiableCollection(authors);
  }

  public void setAuthors(Collection<String> authors) {
    this.authors.clear();
    if (StringUtils.isEmpty(authors))
      return;
    this.authors.addAll(authors);
  }

  public void addAuthor(String author) {
    if (StringUtils.isEmpty(author))
      return;
    this.authors.add(author.trim());
  }

  public void addAuthors(String authorList) {
    if (StringUtils.isEmpty(authorList))
      return;
    String[] authors = authorList.split(";");
    if ((authors == null) || (authors.length == 0))
      return;
    for (int i = 0; i < authors.length; i++) {
      addAuthor(authors[i]);
    }
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("FileMetadata{");
    sb.append("fileName='").append(fileName).append('\'');
    sb.append(", title=").append(title);
    sb.append(", authors=").append(String.join(";", authors));
    sb.append(", timestamp=").append(timestamp);
    sb.append(", id=").append(id);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof FileMetadata))
      return false;

    FileMetadata that = (FileMetadata) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null)
      return false;
    if (title != null ? !title.equals(that.title) : that.title != null)
      return false;
    if (authors != null ? !authors.equals(that.authors) : that.authors != null)
      return false;
    return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (authors != null ? authors.hashCode() : 0);
    result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
    return result;
  }
}
