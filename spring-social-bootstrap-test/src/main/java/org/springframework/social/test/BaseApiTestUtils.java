/**
 * 
 */
package org.springframework.social.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Simple static utility methods to read text and JSON DTO resource files as Strings and Objects
 * 
 * @author robin
 */
public class BaseApiTestUtils {

	/**
	 * Read a file from a DTO resource folder into a String
	 *  
	 * @param resourceFolder Sub folder of DTO resource folder
	 * @param filename
	 * @return File contents as a String
	 * @throws IOException
	 */
	public static String readFileAsString(String resourceFolder, String filename) throws IOException {
		InputStream stream = BaseApiTestUtils.class.getResourceAsStream("/dto/resources/" + resourceFolder + "/" + filename);
		return FileCopyUtils.copyToString(new InputStreamReader(stream));
	}
	
	/**
	 * Read a file containing JSON data from a DTO resource folder into a String
	 * 
	 * @param objectMapper Jackson {@link ObjectMapper} to use to read file to a {@link JsonNode} 
	 * @param resourceFolder Sub folder of DTO resource folder
	 * @param filename File containing JSON data
	 * @return JSON file contents as a String
	 * @throws IOException
	 */
	public static String readFileAsJsonString(ObjectMapper objectMapper, String resourceFolder, String filename) throws IOException {
		JsonNode jsonContent = objectMapper.readValue(readFileAsString(resourceFolder, filename), JsonNode.class);
		return objectMapper.writeValueAsString(jsonContent);
	}
	
	/**
	 * 
	 * @param objectMapper Jackson {@link ObjectMapper} to use to read file to a {@link JsonNode} 
	 * @param resourceFolder Sub folder of DTO resource folder
	 * @param filename File containing JSON data
	 * @param returnAs The class type of the instance that will be created from the JSON data
	 * @return The instance of the specified type created from the JSON data
	 * @throws IOException
	 */
	public static Object readJsonIntoObject(ObjectMapper mapper, String resourceFolder, String fileName, Class<?> returnAs) throws IOException {
		mapper.registerModule(new JodaModule());
		String path = "/dto/resources/" + resourceFolder + "/" + fileName;
		
		try (InputStream is = BaseApiTestUtils.class.getResourceAsStream(path)) {
			return mapper.readValue(is, returnAs);
		}
	}
	
}
