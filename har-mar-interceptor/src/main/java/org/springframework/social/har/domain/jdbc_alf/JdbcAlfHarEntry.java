/**
 * 
 */
package org.springframework.social.har.domain.jdbc_alf;

import org.springframework.social.har.domain.HarEntry;

/**
 * Implementation of {@link HarEntry} to be stored in a basic JDBC DB's table
 * 
 * @author robin
 */
public class JdbcAlfHarEntry implements HarEntry {
	
	private Long	uid;
	private String 	user;
	private String 	provider;
	private Long 	ts;
	private String	method;
	private String 	scheme;
	private String	hostname;
	private String	path;
	private Integer port;
	private String 	query;
	private Integer respCode;
	private String	respReason;
	private Integer respTime;
	
	private String 	request;
	private String 	response;
	private String 	pageref;
	private String 	cache;
	private String	timings;
	private String	serverIP;
	private String	clientIP;
	private String	connection;
	private String 	comment;
	
	/**
	 * @param user
	 * @param provider
	 * @param ts
	 * @param method
	 * @param scheme
	 * @param hostname
	 * @param path
	 * @param port
	 * @param query
	 * @param respCode
	 * @param respReason
	 * @param respTime
	 * @param request
	 * @param response
	 * @param pageref
	 * @param cache
	 * @param timings
	 * @param serverIP
	 * @param clientIP
	 * @param connection
	 * @param comment
	 */
	public JdbcAlfHarEntry(String user, String provider, Long ts, String method,
			String scheme, String hostname, String path, Integer port,
			String query, Integer respCode, String respReason,
			Integer respTime, String request, String response, String pageref,
			String cache, String timings, String serverIP, String clientIP,
			String connection, String comment) {
		super();
		this.user = user;
		this.provider = provider;
		this.ts = ts;
		this.method = method;
		this.scheme = scheme;
		this.hostname = hostname;
		this.path = path;
		this.port = port;
		this.query = query;
		this.respCode = respCode;
		this.respReason = respReason;
		this.respTime = respTime;
		this.request = request;
		this.response = response;
		this.pageref = pageref;
		this.cache = cache;
		this.timings = timings;
		this.serverIP = serverIP;
		this.clientIP = clientIP;
		this.connection = connection;
		this.comment = comment;
	}
	
	public JdbcAlfHarEntry() {
		this(null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, 
				null, null, null, null);
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the ts
	 */
	public Long getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(Long ts) {
		this.ts = ts;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the respCode
	 */
	public Integer getRespCode() {
		return respCode;
	}

	/**
	 * @param respCode the respCode to set
	 */
	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}

	/**
	 * @return the respReason
	 */
	public String getRespReason() {
		return respReason;
	}

	/**
	 * @param respReason the respReason to set
	 */
	public void setRespReason(String respReason) {
		this.respReason = respReason;
	}

	/**
	 * @return the respTime
	 */
	public Integer getRespTime() {
		return respTime;
	}

	/**
	 * @param respTime the respTime to set
	 */
	public void setRespTime(Integer respTime) {
		this.respTime = respTime;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return the pageref
	 */
	public String getPageref() {
		return pageref;
	}

	/**
	 * @param pageref the pageref to set
	 */
	public void setPageref(String pageref) {
		this.pageref = pageref;
	}

	/**
	 * @return the cache
	 */
	public String getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(String cache) {
		this.cache = cache;
	}

	/**
	 * @return the timings
	 */
	public String getTimings() {
		return timings;
	}

	/**
	 * @param timings the timings to set
	 */
	public void setTimings(String timings) {
		this.timings = timings;
	}

	/**
	 * @return the serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * @param serverIP the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * @return the clientIP
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * @param clientIP the clientIP to set
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	/**
	 * @return the connection
	 */
	public String getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(String connection) {
		this.connection = connection;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
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
		return "JdbcAlfHarEntry [uid=" + uid + ", user=" + user + ", provider="
				+ provider + ", ts=" + ts + ", method=" + method + ", scheme="
				+ scheme + ", hostname=" + hostname + ", path=" + path
				+ ", port=" + port + ", query=" + query + ", respCode="
				+ respCode + ", respReason=" + respReason + ", respTime="
				+ respTime + ", request=" + request + ", response=" + response
				+ ", pageref=" + pageref + ", cache=" + cache + ", timings="
				+ timings + ", serverIP=" + serverIP + ", clientIP=" + clientIP
				+ ", connection=" + connection + ", comment=" + comment + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cache == null) ? 0 : cache.hashCode());
		result = prime * result
				+ ((clientIP == null) ? 0 : clientIP.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((connection == null) ? 0 : connection.hashCode());
		result = prime * result
				+ ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((pageref == null) ? 0 : pageref.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((respCode == null) ? 0 : respCode.hashCode());
		result = prime * result
				+ ((respReason == null) ? 0 : respReason.hashCode());
		result = prime * result
				+ ((respTime == null) ? 0 : respTime.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
		result = prime * result
				+ ((serverIP == null) ? 0 : serverIP.hashCode());
		result = prime * result + ((timings == null) ? 0 : timings.hashCode());
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		JdbcAlfHarEntry other = (JdbcAlfHarEntry) obj;
		if (cache == null) {
			if (other.cache != null)
				return false;
		} else if (!cache.equals(other.cache))
			return false;
		if (clientIP == null) {
			if (other.clientIP != null)
				return false;
		} else if (!clientIP.equals(other.clientIP))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else if (!connection.equals(other.connection))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (pageref == null) {
			if (other.pageref != null)
				return false;
		} else if (!pageref.equals(other.pageref))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (respCode == null) {
			if (other.respCode != null)
				return false;
		} else if (!respCode.equals(other.respCode))
			return false;
		if (respReason == null) {
			if (other.respReason != null)
				return false;
		} else if (!respReason.equals(other.respReason))
			return false;
		if (respTime == null) {
			if (other.respTime != null)
				return false;
		} else if (!respTime.equals(other.respTime))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (scheme == null) {
			if (other.scheme != null)
				return false;
		} else if (!scheme.equals(other.scheme))
			return false;
		if (serverIP == null) {
			if (other.serverIP != null)
				return false;
		} else if (!serverIP.equals(other.serverIP))
			return false;
		if (timings == null) {
			if (other.timings != null)
				return false;
		} else if (!timings.equals(other.timings))
			return false;
		if (ts == null) {
			if (other.ts != null)
				return false;
		} else if (!ts.equals(other.ts))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
