/**
 * 
 */
package org.springframework.social.dto.ser;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Test that Unix timestamp values deserialized with 
 * {@link UnixTimestampDeserializer} return valid {@link DateTime} instances
 * 
 * @author robin
 */
public class UnixTimestampDeserializerTest {
	
	private static final DateTime JAN_1_2015_UTC_DATETIME = new DateTime(2015, 1, 1, 0, 0, DateTimeZone.UTC);
	private static final String JAN_1_2015_UTC_TIMESTAMP = "1420070400";
	private static final String NOT_A_NUMBER = "test";
	
	private ObjectMapper mapper;
	
	private static class TestObject {
		
		@JsonDeserialize(using = UnixTimestampDeserializer.class)
		private final DateTime dateTime = null;
		
		public DateTime getDateTime() {
			return dateTime;
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
	public void deserialize_WithJan1st2015UtcTimestamp_CorrectJodaDateTimeInstanceCreated() throws JsonParseException, IOException {
		String now = "\"" + JAN_1_2015_UTC_TIMESTAMP + "\"";
		DateTime result = deserializeJsonToTestObjectWithProvidedTimestamp(now);
		assertThat(result, equalTo(JAN_1_2015_UTC_DATETIME));
	}
	
	@Test(expected=JsonMappingException.class)
	public void deserialize_WithNonNumberTimestamp_ThrowsException() throws JsonParseException, IOException {
		String nan = "\"" + NOT_A_NUMBER + "\"";
		deserializeJsonToTestObjectWithProvidedTimestamp(nan);
	}

	private DateTime deserializeJsonToTestObjectWithProvidedTimestamp(String timestamp) throws IOException, JsonParseException, JsonProcessingException {
		TestObject testObject = mapper.readValue("{\"dateTime\":" + timestamp + "}", TestObject.class);
		
		return testObject.getDateTime();
	}

}
