package src;

import src.commands.Reader;
import src.filemanager.writer.Writer;
import src.moviecollection.MovieCol;

public class Main {
	public static void main(String[] args)  {
		MovieCol mc = new MovieCol();
		Reader in = new Reader(mc);
		while (true) {
			try {
				in.startReading();
			} catch (Exception e) {
				System.out.println("Fatal error occurred. Check logger for more info.");
				new Writer(mc);
				Writer.logErrors(e);
				System.out.println("Restarting the collection.");
			}
		}
	}
}