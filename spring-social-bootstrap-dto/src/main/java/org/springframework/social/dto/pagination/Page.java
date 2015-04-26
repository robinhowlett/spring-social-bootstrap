/**
 * 
 */
package org.springframework.social.dto.pagination;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.social.dto.BaseApiResource;
import org.springframework.social.dto.pagination.Page.PageMetadata.Sort.Direction;
import org.springframework.social.dto.ser.PageSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO compatible with Spring Data's {@linkplain Pageable} Page representation.
 * Wraps {@link BaseApiResource} DTOs
 * 
 * Deserialization should be configured in the base template implementation's 
 * {@link ObjectMapper} configuration
 * 
 * @author robin
 */
@JsonSerialize(using = PageSerializer.class)
public class Page<T extends BaseApiResource> implements BaseApiPage<T> {

	public static final String PAGE = "page";
	public static final String PAGE_SIZE = "size";
	public static final String SORT = "sort";
	public static final String DIRECTION = "sort.dir";
	public static final long DEFAULT_PAGE_SIZE = 25;
	
	private static final long serialVersionUID = 1L;
	
	private final Collection<T> content;
	private final PageMetadata metadata;
	
	public Page() {
		this(Collections.<T> emptyList(), new PageMetadata(DEFAULT_PAGE_SIZE));
	}

	public Page(final Iterable<T> content, final PageMetadata metadata) {
		this.content = new ArrayList<T>();
		for (T element : content) {
			this.content.add(element);
		}
		
		this.metadata = metadata;
	}
	
	@Override
	public Object getUid() {
		return null;
	}

	@Override
	public Collection<T> getContent() {
		return Collections.unmodifiableCollection(content);
	}
	
	public PageMetadata getMetadata() {
		return metadata;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Page [content=" + content + ", metadata=" + metadata + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((metadata == null) ? 0 : metadata.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(content, other.content))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		return true;
	}

	/**
	 * Spring Data Page metadata-compatible DTO 
	 *
	 * @author robin
	 */
	public static class PageMetadata {
		
		public static final List<Sort> NO_SORT = null;
		
		public static final String USE_SERVER_DEFAULT_SORT_PROPERTY = null;
		public static final Direction USE_SERVER_DEFAULT_SORT_DIRECTION = null;
		
		private boolean firstPage;
		private boolean lastPage;
		private long number;
		private final long numberOfElements;
		private long size;
		private List<Sort> sorts;
		private long totalElements;
		private long totalPages;
		
		public PageMetadata(long size) {
			this(1, 0, size, PageMetadata.NO_SORT, 0, 1);
		}
		
		public PageMetadata(long number, long numberOfElements, long size, List<Sort> sorts, long totalElements, long totalPages) {
			validatePageMetadata(number, numberOfElements, size, totalElements, totalPages);
			
			this.firstPage = (number == 1);
			this.lastPage = (number == totalPages);
			this.number = number;
			this.numberOfElements = numberOfElements;
			this.size = size;
			this.sorts = sorts;
			this.totalElements = totalElements;
			this.totalPages = totalPages;
		}
		
		public boolean isFirstPage() {
			return firstPage;
		}

		public void setFirstPage(boolean firstPage) {
			this.firstPage = firstPage;
		}

		public boolean isLastPage() {
			return lastPage;
		}

		public void setLastPage(boolean lastPage) {
			this.lastPage = lastPage;
		}

		public long getNumber() {
			return number;
		}

		public void setNumber(long number) {
			this.number = number;
		}

		public long getNumberOfElements() {
			return numberOfElements;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public List<Sort> getSorts() {
			return sorts;
		}

		public void setSorts(List<Sort> sorts) {
			this.sorts = sorts;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public long getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(long totalPages) {
			this.totalPages = totalPages;
		}
		
		private void validatePageMetadata(final long number, long numberOfElements, long size,
				long totalElements, long totalPages) {
			if (number < 0 || numberOfElements < 0 || size < 0 || totalElements < 0) {
				throw new IllegalArgumentException("number, numberOfElements, size, "
						+ "and totalElements must be 0 or higher"); 
			}
			if (number > totalPages) {
				throw new IllegalArgumentException("The current page number cannot be "
						+ "greater or equal to the total number of pages!");
			}
			if (((number > 0 ? number : 1)*numberOfElements) > totalElements) {
				throw new IllegalArgumentException("The current page number times the "
						+ "numberOfElements cannot be greater or equal to the total "
						+ "number of elements!");
			}
			
		}
		
		/**
		 * Spring Data Sort compatible DTO 
		 *
		 * @author robin
		 */
		public static class Sort {
			
			private boolean ascending;
			private Direction direction;
			private boolean ignoreCase;
			private String property;
			
			public Sort() {
				setAscending(true);
				setDirection(Direction.ASC);
			}
			
			public Sort(String property, Direction direction) {
				this.property = property;
				this.direction = direction;
				this.ascending = (direction == Direction.ASC);
			}
			
			public boolean isAscending() {
				return ascending;
			}

			public void setAscending(boolean ascending) {
				this.ascending = ascending;
			}

			public Direction getDirection() {
				return direction;
			}

			public void setDirection(Direction direction) {
				this.direction = direction;
			}

			public boolean isIgnoreCase() {
				return ignoreCase;
			}

			public void setIgnoreCase(boolean ignoreCase) {
				this.ignoreCase = ignoreCase;
			}

			public String getProperty() {
				return property;
			}

			public void setProperty(String property) {
				this.property = property;
			}

			public enum Direction {
				ASC, DESC;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Sort other = (Sort) obj;
				if (ascending != other.ascending)
					return false;
				if (direction != other.direction)
					return false;
				if (ignoreCase != other.ignoreCase)
					return false;
				if (!property.equals(other.property))
					return false;
				return true;
			}

			@Override
			public String toString() {
				return "Sort [ascending=" + ascending + ", direction=" + direction
						+ ", ignoreCase=" + ignoreCase + ", property=" + property
						+ "]";
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (firstPage ? 1231 : 1237);
			result = prime * result + (lastPage ? 1231 : 1237);
			result = prime * result + (int) (number ^ (number >>> 32));
			result = prime * result
					+ (int) (numberOfElements ^ (numberOfElements >>> 32));
			result = prime * result + (int) (size ^ (size >>> 32));
			result = prime * result + ((sorts == null) ? 0 : sorts.hashCode());
			result = prime * result
					+ (int) (totalElements ^ (totalElements >>> 32));
			result = prime * result + (int) (totalPages ^ (totalPages >>> 32));
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
			PageMetadata other = (PageMetadata) obj;
			if (firstPage != other.firstPage)
				return false;
			if (lastPage != other.lastPage)
				return false;
			if (number != other.number)
				return false;
			if (numberOfElements != other.numberOfElements)
				return false;
			if (size != other.size)
				return false;
			if (sorts == null) {
				if (other.sorts != null)
					return false;
			} else if (!sorts.equals(other.sorts))
				return false;
			if (totalElements != other.totalElements)
				return false;
			if (totalPages != other.totalPages)
				return false;
			return true;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "PageMetadata [firstPage=" + firstPage + ", lastPage="
					+ lastPage + ", number=" + number + ", numberOfElements="
					+ numberOfElements + ", size=" + size + ", sorts=" + sorts
					+ ", totalElements=" + totalElements + ", totalPages="
					+ totalPages + "]";
		}
		
	}
	
}
