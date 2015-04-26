/**
 * 
 */
package org.springframework.social.impl.test_support.dto;

import org.joda.time.DateTime;
import org.springframework.social.dto.BaseApiResource;

/**
 * Example DTO implementing {@link BaseApiResource}
 *
 * @author robin
 */
public class TestBaseApiResource implements BaseApiResource {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private DateTime createDate;
	private Boolean active;
	
	public TestBaseApiResource() { }
	
	public TestBaseApiResource(String id, String name, DateTime createDate, Boolean active) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.active = active;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the createDate
	 */
	public DateTime getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.dto.BaseApiResource#getUid()
	 */
	@Override
	public Object getUid() {
		return getId();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestBaseApiResource [id=" + id + ", name=" + name
				+ ", createDate=" + createDate + ", active=" + active + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestBaseApiResource other = (TestBaseApiResource) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
