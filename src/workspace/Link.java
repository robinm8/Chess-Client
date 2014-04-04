package workspace;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

public class Link {

	public static String userName = "";
	public static String playingAs = "w";
	public static String whoseTurn = "w";
	public static String pieceSelected = "";
	public static int[] selectedDim = new int[2];
	public static Document doc = null;
	public static Date time = Calendar.getInstance().getTime();

	public static Timer timer = new Timer();
	public static XMLStore localDataStore = new XMLStore();

	public static void main(String[] args) {

		localDataStore.Do("create");

		LocalServers.loadSavedServers();
		
		TimerTask dataSave = new TimerTask() {
			public void run() {
				time = Calendar.getInstance().getTime();
				localDataStore.Do("save");
			}
		};

		while (userName == null || userName.trim().isEmpty()) {
			userName = JOptionPane.showInputDialog("Please input a user name.");
		}

		timer.scheduleAtFixedRate(dataSave, 0, 1000);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});

		new Link();

	}
}
