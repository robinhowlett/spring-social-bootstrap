/**
 * 
 */
package org.springframework.social.dto.ser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.social.dto.ser.YesNoBooleanDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Test that "yes", "no" and null values that are deserialized
 * with {@link YesNoBooleanDeserializer} return true, false, and
 * false respectively. {@link YesNoBooleanDeserializer} is not 
 * case sensitive 
 * 
 * @author robin
 */
public class YesNoBooleanDeserializerTest {
	
	private ObjectMapper mapper;
	
	private static class TestObject {
		
		@JsonDeserialize(using = YesNoBooleanDeserializer.class)
		private final Boolean exists = null;
		
		public Boolean getExists() {
			return exists;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mapper = new ObjectMapper();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mapper = null;
	}

	@Test
	public void deserialize_WithUpperAndLowercaseYesValues_CreatesATrueBoolean() throws JsonParseException, IOException {
		String yes = "\"" + YesNoBooleanDeserializer.YES + "\"";
		Boolean result = deserializeJsonToTestObjectWithProvidedYesOrNoString(yes);
		assertTrue(result);
		
		result = deserializeJsonToTestObjectWithProvidedYesOrNoString(yes.toUpperCase());
		assertTrue(result);
	}
	
	@Test
	public void deserialize_WithUpperAndLowercaseNoValues_CreatesAFalseBoolean() throws JsonParseException, IOException {
		String no = "\"" + YesNoBooleanDeserializer.NO + "\"";
		Boolean result = deserializeJsonToTestObjectWithProvidedYesOrNoString(no);
		assertFalse(result);
		
		result = deserializeJsonToTestObjectWithProvidedYesOrNoString(no.toUpperCase());
		assertFalse(result);
	}
	
	@Test
	public void deserialize_WithNullValue_CreatesAFalseBoolean() throws JsonParseException, IOException {
		// null values must deserialize as Boolean.FALSE to support primitives
		Boolean result = deserializeJsonToTestObjectWithProvidedYesOrNoString(null);		
		assertFalse(result);
	}
	
	@Test(expected=JsonMappingException.class)
	public void deserialize_WithNeitherAYesOrNoValue_ThrowsException() throws JsonParseException, IOException {
		deserializeJsonToTestObjectWithProvidedYesOrNoString("\"never\"");
	}

	private Boolean deserializeJsonToTestObjectWithProvidedYesOrNoString(String yesOrNo) throws 
			IOException, JsonParseException, JsonProcessingException {
		TestObject testObject = mapper.readValue("{\"exists\":" + yesOrNo + "}", TestObject.class);
		
		return testObject.getExists();
	}

}
