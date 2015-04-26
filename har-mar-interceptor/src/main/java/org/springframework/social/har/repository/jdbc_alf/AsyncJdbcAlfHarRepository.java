/**
 * 
 */
package org.springframework.social.har.repository.jdbc_alf;

import static org.slf4j.LoggerFactory.getLogger;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.social.har.domain.jdbc_alf.JdbcAlfHarEntry;
import org.springframework.social.har.repository.HarRepository;

/**
 * Implementation of {@link HarRepository} that extends {@link JdbcDaoSupport} to 
 * asynchronously persist and query {@link JdbcAlfHarEntry} events to/form a database using 
 * the provided {@link JdbcTemplate}
 *
 * @author robin
 */
public class AsyncJdbcAlfHarRepository extends JdbcDaoSupport implements HarRepository<JdbcAlfHarEntry> {
	
	private static final Logger LOG = getLogger(AsyncJdbcAlfHarRepository.class);
	
	private final String table;

	public AsyncJdbcAlfHarRepository() {
		this("har");
	}
	
	public AsyncJdbcAlfHarRepository(String table) {
		super();
		this.table = table;
	}

	@Override
	public JdbcAlfHarEntry save(final JdbcAlfHarEntry harEntry) {
		new SimpleAsyncTaskExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try {
					getJdbcTemplate().update(
						"INSERT INTO " + table + " ("
							+ "user, provider, ts, method, "
							+ "scheme, hostname, path, port, query, "
							+ "resp_code, resp_reason, resp_time, "
							+ "request, response, "
							+ "pageref, cache, timings, "
							+ "server_ip, client_ip, connection, comment"
						+ ") VALUES ("
							+ "?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, "
							+ "?, ?, ?, "
							+ "?, ?, "
							+ "?, ?, ?, "
							+ "?, ?, ?, ?)", 
							harEntry.getUser(), harEntry.getProvider(), harEntry.getTs(), harEntry.getMethod(),
							harEntry.getScheme(), harEntry.getHostname(), harEntry.getPath(), harEntry.getPort(), harEntry.getQuery(),
							harEntry.getRespCode(), harEntry.getRespReason(), harEntry.getRespTime(),
							harEntry.getRequest(), harEntry.getResponse(),
							harEntry.getPageref(), harEntry.getCache(), harEntry.getTimings(),
							harEntry.getServerIP(), harEntry.getClientIP(), harEntry.getConnection(), harEntry.getComment());
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		});
		
		return harEntry;
	}
	
	@Override
	public Iterable<JdbcAlfHarEntry> findAll() {
		return getJdbcTemplate().query("SELECT * FROM " + table + " ORDER BY ts ASC", new RequestMapper());
	}
	
	@Override
	public Iterable<JdbcAlfHarEntry> findAllOrderByTimestampDesc() {
		return getJdbcTemplate().query("SELECT * FROM " + table + " ORDER BY ts DESC", new RequestMapper());
	}
	
	@Override
	public Iterable<JdbcAlfHarEntry> findByTimestampGreaterThan(final DateTime start) {
		return getJdbcTemplate().query(
				"SELECT * FROM " + table + " " + 
				"WHERE ts > " + start.getMillis() + " " +
				"ORDER BY ts ASC", new RequestMapper());
	}
	
	@Override
	public Iterable<JdbcAlfHarEntry> findByTimestampBetween(final DateTime start, final DateTime end) {
		return getJdbcTemplate().query(
				"SELECT * FROM " + table + " " + 
				"WHERE ts > " + start.getMillis() + " AND ts < " + end.getMillis() + " " +
				"ORDER BY ts ASC", new RequestMapper());
	}
	
	/**
	 * Map DB row to {@link JdbcAlfHarEntry} instance 
	 *
	 * @author robin
	 */
	protected static final class RequestMapper implements RowMapper<JdbcAlfHarEntry> {

		@Override
		public JdbcAlfHarEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
			String user			= rs.getString("user");
			String provider		= rs.getString("provider");
			long ts 			= rs.getLong("ts");
			String method 		= rs.getString("method");
			String scheme 		= rs.getString("scheme");
			String hostname		= rs.getString("hostname");
			String path 		= rs.getString("path");
			Integer port 		= rs.getInt("port");
			String query 		= rs.getString("query");
			Integer respCode	= rs.getInt("resp_code");
			String respReason	= rs.getString("resp_reason");
			Integer respTime	= rs.getInt("resp_time");
			
			String request 		= rs.getString("request");
			String response 	= rs.getString("response");
			String pageref 		= rs.getString("pageref");
			String cache	 	= rs.getString("cache");
			String timings 		= rs.getString("timings");
			String serverIP		= rs.getString("server_ip");
			String clientIP		= rs.getString("client_ip");
			String connection	= rs.getString("connection");
			String comment 		= rs.getString("comment");			
			
			return new JdbcAlfHarEntry(user, provider, ts, 
					method, scheme, hostname, path, port, query, 
					respCode, respReason, respTime, 
					request, response, pageref, cache, timings, 
					serverIP, clientIP, connection, comment);
		}
		
	}
	
}
