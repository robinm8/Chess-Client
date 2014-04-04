package workspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

public class UpdateServerData {

	static BufferedReader fromServer = null;

	public void update() {

		try {
			fromServer = new BufferedReader(new InputStreamReader(
					Client.server.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (Client.server.isConnected()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!Client.textArea.getText().equals(Client.log)) {
				Client.textArea.setText(Client.log);
				System.out.println("set text");
			}
			new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					String response = null;
					try {
						response = fromServer.readLine();
						System.out.println("Server response: " + response);

						if (response.indexOf("It is now white's turn.") != -1) {
							Link.whoseTurn = "w";
							System.out.println("Pieces ready for movement.");
						} else if (response.indexOf("move") != -1) {
							Board.recieveMove(response);
							Link.whoseTurn = (Link.whoseTurn.equals("w") ? "b"
									: "w");
							Client.log += "SERVER: It is now "
									+ (Link.whoseTurn.equals("w") ? "white"
											: "black") + "'s turn \n";
							Client.textArea.setText(Client.log);
							System.out.println("set text");
						} else if (response.indexOf("disconnect") != -1) {
							Client.toServer.println(Link.userName + "-exit");
							System.exit(0);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(response);

					if (!response.isEmpty() && (response.indexOf("move") == -1)) {
						Client.log += response + "\n";
						if (!Client.textArea.getText().equals(Client.log)) {
							Client.textArea.setText(Client.log);
							System.out.println("set text");
						}
					}
					return null;
				}
			}.execute();
		}
	}
}