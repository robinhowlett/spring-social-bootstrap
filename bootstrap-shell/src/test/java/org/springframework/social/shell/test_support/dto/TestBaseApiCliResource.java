package org.springframework.social.shell.test_support.dto;

import org.springframework.social.dto.BaseApiResource;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestBaseApiCliResource implements BaseApiResource {

	private static final long serialVersionUID = 1L;
	
	private String uid;
	
	public TestBaseApiCliResource(String uid) {
		this.uid = uid;
	}

	@Override
	@JsonIgnore(false)
	public String getUid() {
		return uid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestBaseApiCliResource [uid=" + uid + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		TestBaseApiCliResource other = (TestBaseApiCliResource) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}