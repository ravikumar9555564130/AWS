package com.sapient.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class OOOSwagerTest {

	private static final String O3SOURCE = "o3source";
	private static final Logger logger = LoggerFactory.getLogger(OOOSwagerTest.class);

	@ApiOperation(value = "Upload Excel file", notes = "This API is used to upload file in OOO Application", response = String.class)
	@PutMapping("uploadExcel")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ResponseEntity<?> uploadExcelFile(
			@ApiParam(value = "Upload Excel file", required = true) @RequestParam("file") MultipartFile uploadfile) {

		try {

			if (uploadfile != null && uploadfile.isEmpty()) {
				new ResponseEntity("Please provide valid file - ", new HttpHeaders(), HttpStatus.OK);
			}

			if (uploadfile != null) {

				return sendfiletoApiGateway(uploadfile.getOriginalFilename(), O3SOURCE, uploadfile);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<?> sendfiletoApiGateway(String item, String folder, MultipartFile uploadfile) {
		try {

			String url = "https://vsp3fz1kwi.execute-api.us-east-1.amazonaws.com/rest/" + item + "/" + folder;
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource
					.type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
					.put(ClientResponse.class, uploadfile.getInputStream());

			logger.info("response ::" + response);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			logger.info("output::" + output);

		} catch (Exception e) {
			logger.info("Exception::" + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Successfully uploaded - " + uploadfile.getName(), new HttpHeaders(), HttpStatus.OK);

	}

}
