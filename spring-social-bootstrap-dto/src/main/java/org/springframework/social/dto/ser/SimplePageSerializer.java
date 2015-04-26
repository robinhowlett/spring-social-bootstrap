/**
 * 
 */
package org.springframework.social.dto.ser;

import java.io.IOException;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.SimplePage;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Jackson serializer for a {@link SimplePage} of {@link BaseApiResource}s
 *
 * @author robin
 */
@SuppressWarnings("rawtypes")
public class SimplePageSerializer extends StdSerializer<SimplePage<? extends BaseApiResource>> {
	
	private static final boolean DUMMY = false;

    public SimplePageSerializer() {
        super(SimplePage.class, DUMMY);
    }

    @Override
    public void serialize(final SimplePage value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException, JsonGenerationException {
        serializeWithType(value, jgen, provider, null);
    }
    
    @Override
    public void serializeWithType(SimplePage value, JsonGenerator jgen,
    		SerializerProvider provider, TypeSerializer typeSer)
    		throws IOException, JsonProcessingException {
    	jgen.writeStartObject();
        if (value.getContent().size() > 0) {
            jgen.writeFieldName(value.getContentArrayFieldName());
            jgen.writeStartArray();
            for (final Object obj : value.getContent()) {
                jgen.writeObject(obj);
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }

}
