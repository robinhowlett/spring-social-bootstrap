/**
 * 
 */
package org.springframework.social.connect.test_support.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.BaseApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.impl.test_support.TestBaseApi;
import org.springframework.social.impl.test_support.impl.resources.test.TestTemplate.HealthCheck;
import org.springframework.social.impl.test_support.operations.resources.test.TestOperations;

/**
 * Example implementation of {@link BaseApiAdapter}
 * 
 * <p>{@link TestOperations#healthCheck()} is used to test if the {@link TestBaseApi} is functional. 
 * Fake values are used for {@link ConnectionValues} and a {@link UserProfile}; the assumption is 
 * {@link TestBaseApi} doesn't have social network-style capabilities like update statuses, showing profiles etc.
 *
 * @author robin
 */
public class TestBaseApiAdapter implements BaseApiAdapter<TestBaseApi> {

	@Override
	public boolean test(TestBaseApi testApi) {
		try {
			HealthCheck healthCheck = testApi.testOperations().healthCheck();
			return (healthCheck != null && healthCheck.getIsHealthy());
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(TestBaseApi testApi, ConnectionValues values) {
		values.setDisplayName("Test Display Name");
	}

	@Override
	public UserProfile fetchUserProfile(TestBaseApi testApi) {
		return new UserProfileBuilder().setName("Test Name").setEmail("Test Email").build();
	}

	@Override
	public void updateStatus(TestBaseApi testApi, String message) { /* no-op */ }

}
