package org.springframework.social.dto.ser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page;
import org.springframework.social.dto.pagination.Page.PageMetadata;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

/**
 * Jackson deserializer for a {@link Page} of {@link BaseApiResource}s
 * 
 * @author robin
 *
 * @param <P> A {@link Page} of {@link BaseApiResource}
 * @param <R> A {@link BaseApiResource}
 */
public abstract class AbstractPageDeserializer<P extends Page<? extends BaseApiResource>, R extends BaseApiResource> extends JsonDeserializer<P> {

    private Class<? extends BaseApiResource> resourceClass;

	public AbstractPageDeserializer(Class<? extends BaseApiResource> resourceClass) {
        super();
		this.resourceClass = resourceClass;
    }

	@Override
    public P deserializeWithType(JsonParser jp,
    		DeserializationContext ctxt, TypeDeserializer typeDeserializer)
    		throws IOException, JsonProcessingException {
    	
    	final List<R> content = new ArrayList<>();
    	List<Sort> sorts = null;
    	Boolean firstPage = null;
    	Boolean lastPage = null;
        int number = 0;
        int totalElements = 0;
        int totalPages = 0;
        Integer size = null;

        while (jp.hasCurrentToken()) {
            String currentTokenName = jp.getCurrentName();
            JsonToken currentToken = jp.getCurrentToken();
            if ("content".equals(currentTokenName) && (currentToken == JsonToken.START_ARRAY)) {
                // read the contents
                do {
                    final R obj = readEmbeddedObject(jp);
                    content.add(obj);
                    jp.nextToken();
                } while (jp.getCurrentToken() != JsonToken.END_ARRAY);

            } else if ("sort".equals(currentTokenName) && (currentToken == JsonToken.START_ARRAY)) {
            	sorts = new ArrayList<>();
                do {
                    final Sort sort = readSortObject(jp);
                    sorts.add(sort);
                    jp.nextToken();
                } while (jp.getCurrentToken() != JsonToken.END_ARRAY);

            } else if (currentToken.isBoolean()) {
            	if ("firstPage".equals(currentTokenName)) {
            		firstPage = jp.getBooleanValue();
            	} else if ("lastPage".equals(currentTokenName)) {
            		lastPage = jp.getBooleanValue();
            	}
            } else if (currentToken.isNumeric()) {
                if ("number".equals(currentTokenName)) {
                    number = jp.getIntValue();
                } else if ("size".equals(currentTokenName)) {
                    size = jp.getIntValue();
                } else if ("totalElements".equals(currentTokenName)) {
                    totalElements = jp.getIntValue();
                } else if ("totalPages".equals(currentTokenName)) {
                    totalPages = jp.getIntValue();
                }
            }
            
            // iterate
            jp.nextToken();
        }
        
        PageMetadata metadata = new PageMetadata(number, content.size(), size, sorts, totalElements, totalPages);
        if (firstPage != null) {
        	metadata.setFirstPage(firstPage);
        }
        if (lastPage != null) {
        	metadata.setLastPage(lastPage);
        }
        
        return createPage((Iterable<R>) content, metadata);
    }

    @Override
    public P deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
    	return deserializeWithType(jp, ctxt, null);
    }

    @SuppressWarnings("unchecked")
	private R readEmbeddedObject(final JsonParser jp) throws JsonProcessingException, IOException {
        while (jp.hasCurrentToken()) {
            if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
                    return (R) jp.readValueAs(resourceClass);
            }
            jp.nextToken();
        }
        return null;
    }
    
    private Sort readSortObject(final JsonParser jp) throws JsonProcessingException, IOException {
        while (jp.hasCurrentToken()) {
            if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
                    return jp.readValueAs(Sort.class);
            }
            jp.nextToken();
        }
        return null;
    }
    
    protected abstract P createPage(final Iterable<R> content, final PageMetadata metadata);
    
    
}