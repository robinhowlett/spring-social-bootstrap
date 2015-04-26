package org.springframework.social.dto.ser;

import java.io.IOException;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Jackson serializer for a {@link Page} of {@link BaseApiResource}s
 *
 * @author robin
 */
@SuppressWarnings("rawtypes")
public class PageSerializer extends StdSerializer<Page<? extends BaseApiResource>> {
	
	private static final boolean DUMMY = false;

    public PageSerializer() {
        super(Page.class, DUMMY);
    }
    
    @Override
    public void serialize(final Page value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException, JsonGenerationException {
        serializeWithType(value, jgen, provider, null);
    }
    
    @Override
    public void serializeWithType(Page value, JsonGenerator jgen,
    		SerializerProvider provider, TypeSerializer typeSer)
    		throws IOException, JsonProcessingException {
    	jgen.writeStartObject();
        if (value.getContent().size() > 0) {
            jgen.writeFieldName("content");
            jgen.writeStartArray();
            for (final Object obj : value.getContent()) {
                jgen.writeObject(obj);
            }
            jgen.writeEndArray();
        }
        
        PageMetadata metadata = value.getMetadata();
		jgen.writeBooleanField("firstPage", metadata.isFirstPage());
        jgen.writeBooleanField("lastPage", metadata.isLastPage());
        // make sure to write increment the page number by one as we support 1-indexing through the API, but
        // spring data Pageable is 0-indexed
        jgen.writeNumberField("number", metadata.getNumber() + 1);
        jgen.writeNumberField("numberOfElements", metadata.getNumberOfElements());
        jgen.writeNumberField("size", metadata.getSize());
        if (metadata.getSorts() != null && value.getContent().size() > 0) {
        	jgen.writeFieldName("sort");
        	jgen.writeStartArray();
        	for (final Sort sort : metadata.getSorts()) {
                jgen.writeObject(sort);
            }
            jgen.writeEndArray();
        }
        jgen.writeNumberField("totalElements", metadata.getTotalElements());
        jgen.writeNumberField("totalPages", metadata.getTotalPages());
        jgen.writeEndObject();
    }

}

