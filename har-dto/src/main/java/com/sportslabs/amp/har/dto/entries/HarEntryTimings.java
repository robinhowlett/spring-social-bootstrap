/**
 * 
 */
package com.sportslabs.amp.har.dto.entries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object describes various phases within request-response round trip. All times are specified in milliseconds
 * 
 * <p>The send, wait and receive timings are not optional and must have non-negative values
 * 
 * <p>An exporting tool can omit the blocked, dns, connect and ssl, timings on every request if it is unable to provide them. 
 * Tools that can provide these timings can set their values to -1 if they don’t apply. 
 * For example, connect would be -1 for requests which re-use an existing connection.
 * 
 * <p>The time value for the request must be equal to the sum of the timings supplied in this section (excluding any -1 values).
 * 
 * <p>Following must be true in case there are no -1 values (entry is an object in log.entries):
 * 
 * <p>entry.time == (entry.timings.blocked + entry.timings.dns +
 * entry.timings.connect + entry.timings.send + entry.timings.wait +
 * entry.timings.receive);
 *
 * @author robin
 */
public class HarEntryTimings {
	
	private Integer blocked;	// optional
	private Integer dns;		// optional
	private Integer connect;	// optional
	private Integer send;
	private Integer wait;
	private Integer receive;
	private Integer ssl;		// optional
	private String 	comment;	// optional
	
	/**
	 * @param blocked	(Optional) Time spent in a queue waiting for a network connection. 
	 * 					Use -1 if the timing does not apply to the current request
	 * @param dns		(Optional) DNS resolution time. The time required to resolve a host name. 
	 * 					Use -1 if the timing does not apply to the current request
	 * @param connect	(Optional) Time required to create TCP connection. 
	 * 					Use -1 if the timing does not apply to the current request
	 * @param send		Time required to send HTTP request to the server
	 * @param wait		Waiting for a response from the server
	 * @param receive	Time required to read entire response from the server (or cache)
	 * @param ssl		(Optional) Time required for SSL/TLS negotiation. 
	 * 					If this field is defined then the time is also included in the connect 
	 * 					field (to ensure backward compatibility with HAR 1.1). 
	 * 					Use -1 if the timing does not apply to the current request
	 * @param comment	(Optional) A comment provided by the user or the application
	 */
	@JsonCreator
	public HarEntryTimings(
			@JsonProperty("blocked") 	Integer blocked, 
			@JsonProperty("dns") 		Integer dns, 
			@JsonProperty("connect")	Integer connect,
			@JsonProperty("send") 		Integer send, 
			@JsonProperty("wait") 		Integer wait, 
			@JsonProperty("receive") 	Integer receive, 
			@JsonProperty("ssl") 		Integer ssl,
			@JsonProperty("comment") 	String comment) {
		super();
		this.blocked = blocked;
		this.dns = dns;
		this.connect = connect;
		this.send = send;
		this.wait = wait;
		this.receive = receive;
		this.ssl = ssl;
		this.comment = comment;
	}

	/**
	 * @return Time spent in a queue waiting for a network connection
	 */
	public Integer getBlocked() {
		return blocked;
	}

	/**
	 * @return DNS resolution time
	 */
	public Integer getDns() {
		return dns;
	}

	/**
	 * @return Time required to create TCP connection
	 */
	public Integer getConnect() {
		return connect;
	}

	/**
	 * @return Time required to send HTTP request to the server
	 */
	public Integer getSend() {
		return send;
	}

	/**
	 * @return Waiting for a response from the server
	 */
	public Integer getWait() {
		return wait;
	}

	/**
	 * @return Time required to read entire response from the server (or cache)
	 */
	public Integer getReceive() {
		return receive;
	}

	/**
	 * @return Time required for SSL/TLS negotiation
	 */
	public Integer getSsl() {
		return ssl;
	}

	/**
	 * @return A comment provided by the user or the application
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
	}

	/**
	 * @param dns the dns to set
	 */
	public void setDns(Integer dns) {
		this.dns = dns;
	}

	/**
	 * @param connect the connect to set
	 */
	public void setConnect(Integer connect) {
		this.connect = connect;
	}

	/**
	 * @param send the send to set
	 */
	public void setSend(Integer send) {
		this.send = send;
	}

	/**
	 * @param wait the wait to set
	 */
	public void setWait(Integer wait) {
		this.wait = wait;
	}

	/**
	 * @param receive the receive to set
	 */
	public void setReceive(Integer receive) {
		this.receive = receive;
	}

	/**
	 * @param ssl the ssl to set
	 */
	public void setSsl(Integer ssl) {
		this.ssl = ssl;
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
		return "HarEntryTimings [blocked=" + blocked + ", dns=" + dns
				+ ", connect=" + connect + ", send=" + send + ", wait=" + wait
				+ ", receive=" + receive + ", ssl=" + ssl + ", comment="
				+ comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocked == null) ? 0 : blocked.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((connect == null) ? 0 : connect.hashCode());
		result = prime * result + ((dns == null) ? 0 : dns.hashCode());
		result = prime * result + ((receive == null) ? 0 : receive.hashCode());
		result = prime * result + ((send == null) ? 0 : send.hashCode());
		result = prime * result + ((ssl == null) ? 0 : ssl.hashCode());
		result = prime * result + ((wait == null) ? 0 : wait.hashCode());
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
		HarEntryTimings other = (HarEntryTimings) obj;
		if (blocked == null) {
			if (other.blocked != null)
				return false;
		} else if (!blocked.equals(other.blocked))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (connect == null) {
			if (other.connect != null)
				return false;
		} else if (!connect.equals(other.connect))
			return false;
		if (dns == null) {
			if (other.dns != null)
				return false;
		} else if (!dns.equals(other.dns))
			return false;
		if (receive == null) {
			if (other.receive != null)
				return false;
		} else if (!receive.equals(other.receive))
			return false;
		if (send == null) {
			if (other.send != null)
				return false;
		} else if (!send.equals(other.send))
			return false;
		if (ssl == null) {
			if (other.ssl != null)
				return false;
		} else if (!ssl.equals(other.ssl))
			return false;
		if (wait == null) {
			if (other.wait != null)
				return false;
		} else if (!wait.equals(other.wait))
			return false;
		return true;
	}

}
