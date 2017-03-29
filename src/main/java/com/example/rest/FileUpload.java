package com.example.rest;

import com.example.model.FileMetadata;
import com.example.service.FileService;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@Service
@Scope("request")
@Path("/files")
public class FileUpload {
  private final Logger logger = LoggerFactory.getLogger(FileUpload.class);

  private FileService service;

  public FileUpload(@Autowired FileService service) {
    this.service = service;
    logger.debug("FileUpload endpoint constructed");
  }
  
  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPublicationMetadata() {
    logger.trace("getAllPublicationMetadata()");
    try {
      Collection<FileMetadata> ret = service.retrieveAllMetadata();
      return Response.ok().entity(ret).build();
    }
    catch (Exception e) {
      logger.error("Exception processing GET All Publications metadata", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  
  @POST
  @Path("/")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
      @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("title") String title,
      @FormDataParam("authors") String authors) {
    logger
        .trace("uploadFile({},{},{},{})",
            (uploadedInputStream == null) ? "null" : "not null",
            fileDetail.getFileName(),
            title, authors);
    String id = java.util.UUID.randomUUID().toString();
    FileMetadata metadata = new FileMetadata();
    metadata.setId(id);
    metadata.setFileName(fileDetail.getFileName());
    metadata.setTitle(title);
    metadata.addAuthors(authors);
    metadata.setTimestamp(new Date());
    try {
      service.store(metadata, uploadedInputStream);
      URI uri = new URI(String.format("http://rest/files/%s", id));
      String json = "{\"id\":\"" + id + "\"}";
      return Response.created(uri).entity(json).build();
    }
    catch (URISyntaxException e) {
      logger.error("URI syntax exception for id {}", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    catch (Exception e) {
      logger.error("Exception processing POST file", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getFileDefault(@PathParam("id") String id) {
	  return getFileMetadata(id);
  }
  
  @GET
  @Path("/{id}/metadata")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getFileMetadata(@PathParam("id") String id) {
    logger.trace("getFileMetadata({})", id);
    try {
      FileMetadata metadata = service.retrieveMetadata(id);
      if (metadata != null) {
        return Response.ok().entity(metadata).build();
      }
      else
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    catch (Exception e) {
      logger.error("Exception processing GET files/{}/metadata", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
  
  @GET
  @Path("/{id}/content")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getFileContent(@PathParam("id") String id) {
    logger.trace("getFileContent({})", id);
    StreamingOutput stream = new StreamingOutput() {
        @Override
        public void write(OutputStream os) throws IOException, WebApplicationException {
          try {
            InputStream contentStream = service.retrieveFile(id);
            byte[] buf = new byte[8* 1024];
            int n;
            while ((n = contentStream.read(buf)) > 0) {
            	os.write(buf, 0, n);
            }
            os.flush();
          }
          catch (Exception e) {
            logger.error("Exception processing GET files/{}/content", id, e);
          }
        }
      };
      return Response.ok(stream).build();
  }
}
