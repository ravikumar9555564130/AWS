package com.sapient.lambda;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Test {

	private static final Logger logger = LoggerFactory.getLogger(OOOSwagerTest.class);

	public static void main(String[] args) {

		try {
			// logger.info("uploadfile :: " + uploadfile);
			// logger.info("uploadfile file name :: " + uploadfile.getOriginalFilename());

			String item = "EmployeeDetails";
			String folder = "o3source";

			String url = "https://vsp3fz1kwi.execute-api.us-east-1.amazonaws.com/rest/" + item + "/" + folder;

			File excelFile = new File("C:\\Users\\ravkumar17\\Desktop\\OO3\\EmployeeDetails.xlsx");

			if (!excelFile.isFile()) {
				return;
			}

			logger.info("url ::" + url);
			Client client = Client.create();

			WebResource webResource = client.resource(url);
			ClientResponse response = webResource
					.type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
					.put(ClientResponse.class, excelFile);

			logger.info("response ::" + response);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);

			logger.info("output::" + output);

		} catch (Exception e) {
			logger.info("Exception::" + e);

		}

	}

}
