package com.example.jpa;

import com.example.model.FileMetadata;

public class FileMetadataConverter {
	public static FileMetadata entityToDomain(FileMetadataEntity entity) {
		if (entity == null)
			return null;
		FileMetadata ret = new FileMetadata();
		ret.setId(entity.getId());
		ret.setAuthors(entity.getAuthors());
		ret.setFileName(entity.getFileName());
		ret.setTitle(entity.getTitle());
		ret.setTimestamp(entity.getTimestamp());
		return ret;
	}
	
	public static FileMetadataEntity domainToEntity(FileMetadata data) {
		if (data == null)
			return null;
		FileMetadataEntity ret = new FileMetadataEntity();
		ret.setId(data.getId());
		ret.setAuthors(data.getAuthors());
		ret.setFileName(data.getFileName());
		ret.setTitle(data.getTitle());
		ret.setTimestamp(data.getTimestamp());
		return ret;
	}
}
