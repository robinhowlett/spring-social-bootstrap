package org.springframework.social.dto.ser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.SimplePage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

/**
 * Jackson deserializer for a {@link SimplePage} of {@link BaseApiResource}s
 * 
 * @author robin
 *
 * @param <P> A {@link SimplePage} of {@link BaseApiResource}
 * @param <R> A {@link BaseApiResource}
 */
public abstract class AbstractSimplePageDeserializer<P extends SimplePage<BaseApiResource>> extends JsonDeserializer<P> {

    private final Class<? extends BaseApiResource> resourceClass;
    private final String contentArrayFieldName;

	public AbstractSimplePageDeserializer(Class<? extends BaseApiResource> resourceClass, String contentArrayFieldName) {
        super();
        
		this.resourceClass = resourceClass;
		this.contentArrayFieldName = contentArrayFieldName;
    }

	/**
	 * @return the contentArrayFieldName
	 */
	protected String getContentArrayFieldName() {
		return contentArrayFieldName;
	}

	@Override
    public P deserializeWithType(JsonParser jp,
    		DeserializationContext ctxt, TypeDeserializer typeDeserializer)
    		throws IOException, JsonProcessingException {
    	
    	final List<BaseApiResource> content = new ArrayList<>();

        while (jp.hasCurrentToken()) {
            String currentTokenName = jp.getCurrentName();
            JsonToken currentToken = jp.getCurrentToken();
            if (getContentArrayFieldName().equals(currentTokenName) && (currentToken == JsonToken.START_ARRAY)) {
                // read the contents
                do {
                    final BaseApiResource obj = readEmbeddedObject(jp);
                    content.add(obj);
                    jp.nextToken();
                } while (jp.getCurrentToken() != JsonToken.END_ARRAY);

            }
            
            // iterate
            jp.nextToken();
        }
        
        return createPage((Iterable<BaseApiResource>) content);
    }

    @Override
    public P deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
    	return deserializeWithType(jp, ctxt, null);
    }

	private BaseApiResource readEmbeddedObject(final JsonParser jp) throws JsonProcessingException, IOException {
        while (jp.hasCurrentToken()) {
            if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
                    return jp.readValueAs(resourceClass);
            }
            jp.nextToken();
        }
        return null;
    }
    
    protected abstract P createPage(final Iterable<BaseApiResource> content);
    
    
}