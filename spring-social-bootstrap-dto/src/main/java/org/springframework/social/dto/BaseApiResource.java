/**
 * 
 */
package org.springframework.social.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Root interface for all resource DTOs. All Spring Social Bootstrap-based DTOs should inherit from this interface. 
 * 
 * @author robin
 */
public interface BaseApiResource extends Serializable {
	
	@JsonIgnore
	public Object getUid();

}
