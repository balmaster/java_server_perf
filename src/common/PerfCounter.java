package common;

import java.util.Date;

public class PerfCounter {
	private int count;
	private boolean debug = false;
	private String label;

	public String getLabel() {
		return label;
	}

	private PerfCounterGroup group;

	public synchronized int incCount() {
		int result = ++count;
		if (debug && (result % 100 == 0)) {
			if (group == null) {
				System.out.println(new StringBuilder().append(label)
						.append(" ").append(new Date().toString()).append(" ")
						.append(result).toString());
			} else {
				group.debugOut();
			}
		}
		return result;
	}

	public PerfCounter(PerfCounterGroup group, String label, boolean debug) {
		this.group = group;
		this.label = label;
		this.debug = debug;
		if (group != null)
			group.register(this);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}
}
