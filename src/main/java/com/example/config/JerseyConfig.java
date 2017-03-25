package com.example.config;

import com.example.filters.DiagnosticContextFilter;
import com.example.rest.FileUpload;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

  private final Logger logger = LoggerFactory.getLogger(JerseyConfig.class);

  public JerseyConfig() {
    logger.info("Registering Jersey REST components");
    ApplicationPath annotation = JerseyConfig.class.getAnnotation(ApplicationPath.class);
    logger.info("Configured REST root path:'{}'", annotation.value());
    // register our REST endpoint(s)
    register(FileUpload.class);
    // register Filter(s)
    register(DiagnosticContextFilter.class);
    // enable Jackson for JSON & JAXB serialization
    register(JacksonFeature.class);
    // Enable multipart feature
    register(MultiPartFeature.class);
  }

}