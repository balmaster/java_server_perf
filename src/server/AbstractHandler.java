package server;

import common.PerfCounter;
import common.PerfCounterGroup;

public class AbstractHandler implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	protected static PerfCounterGroup group = new PerfCounterGroup("CLIENT");
	protected static PerfCounter beginRequest = new PerfCounter(group,"BEGIN", true);
	protected static PerfCounter errorRequest = new PerfCounter(group,"ERROR", true);
	protected static PerfCounter successRequest = new PerfCounter(group,"SUCCESS", true);
}
