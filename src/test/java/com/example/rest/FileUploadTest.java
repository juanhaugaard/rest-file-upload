package com.example.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.FileUploadRestMain;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileUploadRestMain.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileUploadTest {
  @Autowired
  private TestRestTemplate template;

  @Test
  public void testPost() {
	  String message; 
	  String url = "/rest/files";
	  HttpHeaders headers = postHeaders();
	  HttpEntity<String> entity = new HttpEntity<String>(payload,headers);
	  ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
	  message = response.getBody(); 
      Assert.assertEquals("POST failed", HttpStatus.CREATED.value(), response.getStatusCodeValue());
  }
  
  @Test
  public void testGet() {
	  String message; 
	  String url = "/rest/files";
	  HttpHeaders headers = new HttpHeaders();
	  headers.add("Accept","application/json");
	  HttpEntity<String> entity = new HttpEntity<String>(headers);
	  ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
	  message = response.getBody(); 
      Assert.assertEquals("GET failed", HttpStatus.OK.value(), response.getStatusCodeValue());
  }
  
  private HttpHeaders postHeaders() {
	  HttpHeaders headers = new HttpHeaders();
	  headers.add("Content-Length", String.format("%d", payload.length()));
	  headers.add("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryOfiFLpE0oAydRZBt");
	  headers.add("Accept","application/json");
	  return headers;
  }
  
  private String payload = 
		"------WebKitFormBoundaryOfiFLpE0oAydRZBt"+"\n"+
		"Content-Disposition: form-data; name=\"file\"; filename=\"Haugaard Juan CV 2015.doc\""+"\n"+
		"Content-Type: application/msword"+"\n"+
		"\n"+
		"\n"+
		"------WebKitFormBoundaryOfiFLpE0oAydRZBt"+"\n"+
		"Content-Disposition: form-data; name=\"title\""+"\n"+
		"\n"+
		"Title"+"\n"+
		"------WebKitFormBoundaryOfiFLpE0oAydRZBt"+"\n"+
		"Content-Disposition: form-data; name=\"authors\""+"\n"+
		"\n"+
		"Author"+"\n"+
		"------WebKitFormBoundaryOfiFLpE0oAydRZBt-"+"\n";
}
