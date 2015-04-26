package com.sportslabs.amp.har.dto.entries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This object describes details about response content (embedded in {@link HarResponse} object)
 * 
 * <p>Before setting the text field, the HTTP response is decoded (decompressed & unchunked), 
 * then trans-coded from its original character set into UTF-8. 
 * 
 * Additionally, it can be encoded using e.g. base64. 
 * 
 * Ideally, the application should be able to unencode a base64 blob and get a byte-for-byte 
 * identical resource to what the browser operated on.
 * 
 * <p>Encoding field is useful for including binary responses (e.g. images) into the HAR file.
 *
 * @author robin
 */
public class HarContent {
	
	private Integer size;
	private Integer compression;	// optional
	private String 	mimeType;
	private String 	text;			// optional
	private String 	encoding;		// optional
	private String 	comment;		// optional
	
	/**
	 * @param size			Length of the returned content in bytes. Should be equal to response.bodySize if 
	 * 						there is no compression and bigger when the content has been compressed
	 * @param compression	(Optional) Number of bytes saved. Leave out this field if the information is not available
	 * @param mimeType		MIME type of the response text (value of the Content-Type response header). 
	 * 						The charset attribute of the MIME type is included (if available)
	 * @param text			(Optional) Response body sent from the server or loaded from the browser/client cache. 
	 * 						This field is populated with textual content only. 
	 * 						The text field is either HTTP decoded text or a encoded (e.g. "base64") representation of 
	 * 						the response body. 
	 * 						Leave out this field if the information is not available
	 * @param encoding		(Optional) Encoding used for response text field e.g "base64". 
	 * 						Leave out this field if the text field is HTTP decoded (decompressed & unchunked) and then 
	 * 						trans-coded from its original character set into UTF-8
	 * @param comment		(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarContent(
			@JsonProperty("size") 			Integer size, 
			@JsonProperty("compression") 	Integer compression, 
			@JsonProperty("mimeType") 		String mimeType,
			@JsonProperty("text") 			String text, 
			@JsonProperty("encoding") 		String encoding, 
			@JsonProperty("comment") 		String comment) {
		super();
		this.size = size;
		this.compression = compression;
		this.mimeType = mimeType;
		this.text = text;
		this.encoding = encoding;
		this.comment = comment;
	}
	
	public HarContent() { }

	/**
	 * @return Length of the returned content in bytes
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @return Number of bytes saved
	 */
	public Integer getCompression() {
		return compression;
	}

	/**
	 * @return MIME type of the response text (value of the Content-Type response header)
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return Response body sent from the server or loaded from the browser/client cache
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return Encoding used for response text field
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @param compression the compression to set
	 */
	public void setCompression(Integer compression) {
		this.compression = compression;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HarContent [size=" + size + ", compression=" + compression
				+ ", mimeType=" + mimeType + ", text=" + text + ", encoding="
				+ encoding + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((compression == null) ? 0 : compression.hashCode());
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		HarContent other = (HarContent) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (compression == null) {
			if (other.compression != null)
				return false;
		} else if (!compression.equals(other.compression))
			return false;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
