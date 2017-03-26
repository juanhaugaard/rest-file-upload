package com.example.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FileMetadataEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Column
	private String fileName;
	@Column
	private String title;
	@ElementCollection 
	@Column
	private Collection<String> authors = new LinkedList<>();
	@Column
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<String> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<String> authors) {
		this.authors = authors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileMetadataEntity [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (fileName != null)
			builder.append("fileName=").append(fileName).append(", ");
		if (title != null)
			builder.append("title=").append(title).append(", ");
		if (authors != null)
			builder.append("authors=").append(authors).append(", ");
		if (timestamp != null)
			builder.append("timestamp=").append(timestamp);
		builder.append("]");
		return builder.toString();
	}
}
