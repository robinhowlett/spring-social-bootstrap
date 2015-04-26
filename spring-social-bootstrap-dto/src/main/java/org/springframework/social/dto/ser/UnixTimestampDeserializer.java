/**
 * 
 */
package org.springframework.social.dto.ser;

import java.io.IOException;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserializes Unix timestamps to {@link DateTime} instances
 * 
 * @author robin
 *
 */
public class UnixTimestampDeserializer extends JsonDeserializer<DateTime> {

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		String timestamp = jp.getText().trim();
		TimeZone timeZone = ctxt.getTimeZone();
        DateTimeZone dateTimeZone = (timeZone == null) ? DateTimeZone.UTC : DateTimeZone.forTimeZone(timeZone);
		
		try {
			return new DateTime(Long.valueOf(timestamp + "000"), DateTimeZone.UTC).withZone(dateTimeZone);
		} catch (NumberFormatException e) {
			throw ctxt.weirdStringException(timestamp, DateTime.class, "Could not convert timestamp to a valid number");
		} catch (IllegalArgumentException e) {
			throw ctxt.weirdStringException(timestamp, DateTime.class, "Could not convert timestamp to a valid date");
		}
	}

}
