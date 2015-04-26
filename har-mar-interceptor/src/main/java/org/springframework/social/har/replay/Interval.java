package org.springframework.social.har.replay;

/**
 * Three interval types are provided:
 * 
 * <ul>
 * 	<li>Real-Time (replay at the frequency the events were saved to the HTTP Archive) 
 * 	<li>Fixed (replay at a fixed interval (defined in seconds)
 * 	<li>No Interval (replay sequentially without an interval)
 * </ul>
 *
 * @author robin
 */
public interface Interval {
	
	enum IntervalType {
		REALTIME,
		FIXED,
		NO_INTERVAL
	}
	
	public long getInterval();
	public void updateInterval(long next, long previous);

	public IntervalType getIntervalType();
	
	public abstract class AbstractInterval implements Interval {
		
		private final IntervalType intervalType;
		
		public AbstractInterval(final IntervalType intervalType) {
			this.intervalType = intervalType;
		}
		
		public abstract long getInterval();
		
		@Override
		public void updateInterval(long next, long previous) {
			// default is no-op
		}
		
		public IntervalType getIntervalType() {
			return intervalType;
		}
		
	}
	
	public class RealTimeInterval extends AbstractInterval {
		
		private long interval;

		public RealTimeInterval(final long previous, final long next) {
			super(IntervalType.REALTIME);

			updateInterval(next, previous);
		}
		
		@Override
		public void updateInterval(long next, long previous) {
			if (previous != 0) {
				interval = next - previous;
			} else {
				interval = 0;
			}
		}
		
		@Override
		public final long getInterval() {
			return interval;
		}
	}
	
	public class FixedInterval extends AbstractInterval {
		
		private final long interval;

		public FixedInterval(final int intervalInSeconds) {
			super(IntervalType.FIXED);

			interval = (intervalInSeconds * 1000);
		}
		
		@Override
		public final long getInterval() {
			return interval;
		}
	}
	
	public class NoInterval extends AbstractInterval {
		
		private final long interval;

		public NoInterval() {
			super(IntervalType.NO_INTERVAL);

			interval = 0;
		}
		
		@Override
		public final long getInterval() {
			return interval;
		}
	}
}