package workspace;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * Chess Game Client
 * 
 * @author Mark Robinson
 * 
 */
public class Client {

	static String log = "";
	static JTextArea textArea = new JTextArea(log);
	static Socket server = null;
	static PrintWriter toServer = null;
	static Boolean ready = false;

	public static void start() {
		try {

			System.out.println("Connecting to server.");

			server = LocalServers.servers
					.get(LocalServers.selectedServer);

			System.out.println("connected to server at "
					+ server.getInetAddress().getHostAddress());

			ready = true;

			GUI.switchContentDisplay(GUI.game);

			JPanel container = GUI.chat;
			JPanel bottom = new JPanel(new BorderLayout());

			final JTextField textField = new JTextField(20);
			JButton submit = new JButton("Submit");

			textArea.setEditable(false);
			JScrollPane scroll = new JScrollPane(textArea);

			bottom.add(textField, BorderLayout.NORTH);
			bottom.add(submit, BorderLayout.SOUTH);

			container.add(scroll, BorderLayout.CENTER);
			container.add(bottom, BorderLayout.SOUTH);
			textArea.setFont(new Font("Trebuchet MS", Font.BOLD, 15));

			Client.toServer = new PrintWriter(server.getOutputStream(), true);

			Client.toServer.println(Link.userName);

			System.out.println("Sent userName");

			textField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!textField.getText().trim().isEmpty()) {
						if (textField.getText().equals("exit")
								&& Link.userName.isEmpty()) {
							toServer.println("exit");
							toServer.println("exit-exit");
						}

						toServer.println((Link.userName.isEmpty() ? ""
								: Link.userName + "-") + textField.getText());
						System.out.println("Sending this to server: "
								+ Link.userName + textField.getText());

						if (Link.userName.isEmpty()) {
							Link.userName = textField.getText();
						}

						if (textField.getText().equals("exit")
								&& !Link.userName.isEmpty()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.exit(0);
						}

						textField.setText("");

						textField.requestFocusInWindow();
					}
				}
			});

			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!textField.getText().trim().isEmpty()) {

						if (textField.getText().equals("exit")
								&& Link.userName.isEmpty()) {
							toServer.println("exit");
							toServer.println("exit-exit");
						}

						toServer.println((Link.userName.isEmpty() ? ""
								: Link.userName + "-") + textField.getText());
						System.out.println("Sending this to server: "
								+ Link.userName + "-" + textField.getText());

						if (Link.userName.isEmpty()) {
							Link.userName = textField.getText();
						}

						if (textField.getText().equals("exit")
								&& !Link.userName.isEmpty()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.exit(0);
						}

						textField.setText("");

						textField.requestFocusInWindow();
					}
				}
			});

			new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					UpdateServerData updator = new UpdateServerData();
					updator.update();
					JOptionPane.showMessageDialog(null, "Connection Closed",
							"Game Over.", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
					return null;
				}

			}.execute();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Server Offline - Try again later", "Server Offline",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);
		}
	}
}