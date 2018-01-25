import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Use threads to implement a stop watch that displays,
 * once every five seconds, the minutes and seconds that
 * have passed since it was started.
 *
 * The display should be in the form mm:ss for minutes and seconds.
 *
 * When the clock reaches 15 minutes, it should wrap back and start at 0
 * minutes and 0 seconds.
 *
 * The user should be able to stop the watch at any time.
 *
 * Write the complete code for the application.
 *
 * (Not the most accurate stop watch, but the model is useful for
 * animations in which slight inaccuracies in time would not be detrimental.)
 */
public class StopWatch {

	public static void main(String[] args){
		StopWatchThread s = new StopWatchThread();
		Thread stopWatchThread = new Thread(s);
		
		stopWatchThread.start();
		BufferedReader br;
		String command;
		br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			// current thread wait for input and check if it is cmd to the watch
			do {
				try {
					command = br.readLine();
				}
				catch (IOException e) {
					command = "unknown";
				}
				if (command.equalsIgnoreCase("s")) {
					System.out.println("Stop");
					s.start = false;
					if (stopWatchThread.getState()==Thread.State.TIMED_WAITING) {
						stopWatchThread.interrupt();
					}
				}
				else if (command.equals("r")) {
					System.out.println("Reset");
					s.reset = true;
					if (stopWatchThread.getState()==Thread.State.TIMED_WAITING) {
						stopWatchThread.interrupt();
					}
				}
				else if (command.equals("t")) {
					System.out.println("Start");
					s.startTime = System.currentTimeMillis();
					s.start =true;
				}
				else if (command.equalsIgnoreCase("q")) {
					System.out.println("Quit");
					s.quit = true;
					if (stopWatchThread.getState()==Thread.State.TIMED_WAITING) {
						stopWatchThread.interrupt();
					}
					stopWatchThread.join();
					break;
				}
			} while (true);
		}
		catch (InterruptedException e) {
			System.out.println("Exiting");
		}	
	}
}

class StopWatchThread implements Runnable {
	public boolean quit = false;
	public boolean start = true;
	public boolean reset = false;
	public long startTime; 
	// delay is five second
	int delay = 5000;

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		Watch w = new Watch();
		// animation loop
		while (true) {
			System.out.println();
			if (quit) {
				break;
			}
			if (reset) {
				w.reset();
				startTime = System.currentTimeMillis();
				reset = false;
			}
			if (start) {
				try {
					startTime += delay;
					Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
					w.stepUp();
				}
				catch (InterruptedException e) {
					 //
				}
			}
		}
	}
}

class Watch {
	int mins;
	int secs;
	
	String allStr;
	
	public Watch() {
		mins = 0;
		secs = 0;
		allStr = " 00:00";
	}

	public void stepUp() {
		secs = secs + 5;
		if (secs == 60) {
			mins = (mins + 1) % 60;
			secs = 0;
		}
		if (mins >= 15) {
			mins = mins - 15;
		}

		String minsStr = String.valueOf(mins);
		String secsStr = String.valueOf(secs);

		if (mins < 10) {
			minsStr = "0" + minsStr;
		}
		if (secs > 10) {
			secsStr = "0" + secsStr;
		}
		allStr = " " + minsStr + ":" + secsStr;
		System.out.println(allStr);
	}
	
	public void reset()	{
		mins = 0;
		secs = 0;
		allStr = " 00:00";
		System.out.println(allStr);
	}
}