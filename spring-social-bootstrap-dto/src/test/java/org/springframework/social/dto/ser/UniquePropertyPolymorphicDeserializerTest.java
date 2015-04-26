/**
 * 
 */
package org.springframework.social.dto.ser;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Test that documents without a specific field designated for Polymorphic Type
 * identification can be deserialized with {@link UniquePropertyPolymorphicDeserializer}, 
 * when the document contains a field registered to be unique to that type
 * 
 * @author robin
 */
@SuppressWarnings("unused")
public class UniquePropertyPolymorphicDeserializerTest {
	
	private static final String ONE = "one";
	private static final String TWO = "two";
	private static final String THREE = "three";
	
	private ObjectMapper mapper;

	/*
	 * Example extension of UniquePropertyPolymorphicDeserializer
	 */
	private class TestObjectDeserializer extends UniquePropertyPolymorphicDeserializer<AbstractTestObject> {
		private static final long serialVersionUID = 1L;
		
		// Register the abstract class that doesn't provide type information for child implementations
		public TestObjectDeserializer() {
			super(AbstractTestObject.class);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mapper = new ObjectMapper();
		
		/*
		 * Register unique field names to TestObject types 
		 */
		TestObjectDeserializer deserializer = new TestObjectDeserializer();
		deserializer.register(ONE, TestObjectOne.class); // if "one" field is present, then it's a TestObjectOne
		deserializer.register(TWO, TestObjectTwo.class); // if "two" field is present, then it's a TestObjectTwo
		
		// Add and register the UniquePropertyPolymorphicDeserializer to the Jackson module
		SimpleModule module = new SimpleModule("PolymorphicTestObjectDeserializer", 
				new Version(1, 0, 0, null, "com.sportslabs.amp", "spring-social-bootstrap"));	
		module.addDeserializer(AbstractTestObject.class, deserializer);
		mapper.registerModule(module);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mapper = null;
	}

	@Test
	public void deserialize_WithRegisteredFieldNameOne_CreatesATestObjectOne() throws JsonParseException, IOException {
		AbstractTestObject testObject = deserializeJsonToTestObjectWithProvidedFieldValueName(ONE);
		
		Assert.assertTrue(testObject.getClass().isAssignableFrom(TestObjectOne.class));
	}
	
	@Test
	public void deserialize_WithRegisteredFieldNameTwo_CreatesATestObjectTwo() throws JsonParseException, IOException {
		AbstractTestObject testObject = deserializeJsonToTestObjectWithProvidedFieldValueName(TWO);
		
		Assert.assertTrue(testObject.getClass().isAssignableFrom(TestObjectTwo.class));
	}

	@Test(expected=JsonMappingException.class)
	public void deserialize_WithUnregisteredFieldName_ThrowsException() throws JsonParseException, IOException {
		deserializeJsonToTestObjectWithProvidedFieldValueName(THREE);
	}

	private AbstractTestObject deserializeJsonToTestObjectWithProvidedFieldValueName(String field) throws IOException, JsonParseException, JsonProcessingException {
		return mapper.readValue("{\"common\": \"value\", \"" + field + "\": \"value\"}", AbstractTestObject.class);
	}
	
	/*
	 * The abstract class that doesn't provide type information for its child implementations
	 */
	private static abstract class AbstractTestObject {
		private final String common = "value";
		
		public String getCommon() {
			return common;
		}
	}
	
	private static class TestObjectOne extends AbstractTestObject {
		private final String one = "value";

		public String getOne() {
			return one;
		}
	}
	
	private static class TestObjectTwo extends AbstractTestObject {
		private final String two = "value";

		public String getTwo() {
			return two;
		}
	}

}
