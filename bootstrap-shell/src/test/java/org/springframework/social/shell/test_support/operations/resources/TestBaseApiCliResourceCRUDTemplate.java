package org.springframework.social.shell.test_support.operations.resources;

import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.BaseApiPage;
import org.springframework.social.impl.resources.AbstractBaseApiResourceCRUDTemplate;
import org.springframework.social.impl.test_support.builders.TestPaginationCliUriComponentsBuilder;
import org.springframework.social.settings.ClientSettings;
import org.springframework.social.shell.test_support.dto.TestBaseApiCliResource;
import org.springframework.web.client.RestTemplate;

public class TestBaseApiCliResourceCRUDTemplate extends AbstractBaseApiResourceCRUDTemplate<TestBaseApiCliResource, BaseApiPage<TestBaseApiCliResource>, TestPaginationCliUriComponentsBuilder> {

	public TestBaseApiCliResourceCRUDTemplate(ClientSettings settings, RestTemplate restTemplate) {
		super(settings, restTemplate);
	}
	
	@Override
	public TestPaginationCliUriComponentsBuilder qb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseApiPage<TestBaseApiCliResource> previousPage(BaseApiPage<TestBaseApiCliResource> currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseApiPage<TestBaseApiCliResource> nextPage(BaseApiPage<TestBaseApiCliResource> currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getResourcePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getResourceIdPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<? extends BaseApiResource> getResourceClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Class<? extends BaseApiPage<TestBaseApiCliResource>> getPageClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
}