package common;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PerfCounterGroup {
	private Set<PerfCounter> counters = new HashSet<PerfCounter>();
	private String label;
	public PerfCounterGroup(String label)
	{
		this.label=label;
	}
	public void register(PerfCounter value)
	{
		counters.add(value);
	}
	public void debugOut()
	{
		StringBuilder sb = new StringBuilder()
		.append(new Date().toString()).append(" ")
		.append(label)
		.append(": "); 
		for (PerfCounter pc : counters) {
			sb.append(pc.getLabel()).append(" ").append(pc.getCount()).append(", ");
		}
		System.out.println(sb.toString());
	}
}
