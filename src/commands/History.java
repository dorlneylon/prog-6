package src.commands;

import java.util.ArrayList;

/**
 * Class for the history command. Prints the last 7 commands.
 * @see History#latest()
 */
public class History {
	private static int c = 0;
	private static final ArrayList<String> latest = new ArrayList<>();

	public static void latest() {
		for (int i = 0; i < Math.min(7,c); i++)
			System.out.println(latest.get(i));
	}

	public static void updCounter(String s) {
		if (c < 7) {
			latest.add(s);
			c++;
		} else {
			latest.remove(0);
			latest.add(s);
		}
	}

	public static String getLast() {
		return (latest.size() > 0) ? latest.get(c-1) : "";
	}
}
