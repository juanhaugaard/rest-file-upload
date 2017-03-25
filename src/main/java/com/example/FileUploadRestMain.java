package com.example;

import com.example.config.JerseyConfig;
import com.example.service.FileService;
import com.example.service.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@SpringBootApplication
public class FileUploadRestMain extends SpringBootServletInitializer {
  private final Logger logger = LoggerFactory.getLogger(FileUploadRestMain.class);
  public static void main(String[] args) {
    SpringApplication.run(FileUploadRestMain.class, args);
  }
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    logger.info("**** #### Main.configure called #### ****");
    return super.configure(builder);
  }
  /*****************
   * BootStrap environment report
   ****************/
  @Bean
  public String bootstrapReport(@Autowired ConfigurableEnvironment env) {
    StringBuilder msg = new StringBuilder("\n ***\n *** BootStrap environment report:");
    if (env == null) {
      msg.append("\n *** Configurable Environment is not available");
    }
    else {
      List<String> envKeys = Arrays.asList("spring.application.name",
          "spring.profiles.active",
          "spring.cloud.config.enabled",
          "spring.cloud.discovery.enabled",
          "spring.cloud.config.uri",
          "spring.cloud.config.label",
          "server.address",
          "server.port",
          "server.context-path",
          "management.address",
          "management.port",
          "management.context-path",
          "logging.file");
      envKeys.forEach(key -> {
        msg.append(MessageFormat.format("\n *** {0} : {1}", key, env.getProperty(key)));
      });
    }
    msg.append("\n ***\n");
    logger.info(msg.toString());
    return msg.toString();
  }

  @Bean
  public FileService makeFileService() {return new FileServiceImpl(); }

  @Bean
  public JerseyConfig configureJersey() {
    return new JerseyConfig();
  }

}
