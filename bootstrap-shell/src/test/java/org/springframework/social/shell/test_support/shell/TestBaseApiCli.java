/**
 * 
 */
package org.springframework.social.shell.test_support.shell;

import org.springframework.social.shell.BaseApiCli;
import org.springframework.social.shell.TestBaseApiCliMarker;


/**
 * Test CLI
 *
 * @author robin
 */
public class TestBaseApiCli extends BaseApiCli {

	/* (non-Javadoc)
	 * @see org.springframework.social.BaseApiCli#basePackages()
	 */
	@Override
	public String[] basePackages() {
		return new String[] { TestBaseApiCliMarker.class.getPackage().getName() };
	}

}
