/**
 * 
 */
package org.springframework.social.dto.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.ser.SimplePageSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO that wraps {@link BaseApiResource} DTOs in an object array whose name is configurable
 * 
 * @author robin
 */
@JsonSerialize(using = SimplePageSerializer.class)
public class SimplePage<T extends BaseApiResource> implements BaseApiPage<T> {

	private static final long serialVersionUID = 1L;
	
	private final Collection<T> content;
	
	@JsonIgnore
	private final String contentArrayFieldName;
	
	public SimplePage() {
		this("content");
	}
	
	public SimplePage(String contentArrayFieldName) {
		this(Collections.<T> emptyList(), contentArrayFieldName);
	}
	
	public SimplePage(final Iterable<T> content, String contentArrayFieldName) {
		this.content = new ArrayList<T>();
		
		if (content != null) {
			for (T element : content) {
				this.content.add(element);
			}
		}
		
		this.contentArrayFieldName = contentArrayFieldName;
	}

	@Override
	public Object getUid() {
		return null;
	}

	@Override
	public Collection<T> getContent() {
		return content;
	}
	
	public String getContentArrayFieldName() {
		return contentArrayFieldName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimplePage [" + contentArrayFieldName + "=" + content + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime
				* result
				+ ((contentArrayFieldName == null) ? 0 : contentArrayFieldName
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePage other = (SimplePage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(content, other.content))
			return false;
		if (contentArrayFieldName == null) {
			if (other.contentArrayFieldName != null)
				return false;
		} else if (!contentArrayFieldName.equals(other.contentArrayFieldName))
			return false;
		return true;
	}

}
