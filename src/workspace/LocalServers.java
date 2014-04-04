package workspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import org.w3c.dom.Element;

public class LocalServers {

	static ArrayList<String> serverDisplay = new ArrayList<String>();
	static ArrayList<String> serverIps = new ArrayList<String>();

	static ArrayList<Socket> servers = new ArrayList<Socket>();

	static int selectedServer = 0;

	static boolean ready = false;

	static BufferedReader fromServer = null;

	@SuppressWarnings("resource")
	public static void loadSavedServers() {
		for (int index = 0; index < Link.doc.getDocumentElement()
				.getElementsByTagName("server").getLength(); index++) {
			Element server = (Element) Link.doc.getDocumentElement()
					.getElementsByTagName("server").item(index);

			Socket serverSoc = new Socket();
			try {
				SocketAddress sock = new InetSocketAddress(
						server.getAttribute("ip"), 5679);
				serverSoc.connect(sock, 5000);
				System.out.println("connected to server at "
						+ server.getAttribute("ip"));

			} catch (Exception e) {
				System.out.println("Unable to connect to server at "
						+ server.getAttribute("ip"));
			}

			serverIps.add(server.getAttribute("ip"));
			servers.add(serverSoc.isConnected() ? serverSoc : null);
			serverDisplay
					.add(server.getAttribute("ip")
							+ " - "
							+ (serverSoc.isConnected() ? "Connectable"
									: "Unreachable"));
		}
	}

	@SuppressWarnings("resource")
	public static void saveServer(String ip) {

		Socket server = new Socket();

		try {
			SocketAddress sock = new InetSocketAddress(ip, 5679);
			server.connect(sock, 5000);
			System.out.println("connected to server at " + ip);

		} catch (Exception e) {
			System.out.println("Unable to connect to server at " + ip);
		}

		boolean makeServerElement = true;
		for (int index = 0; index < Link.doc.getDocumentElement()
				.getElementsByTagName("server").getLength(); index++) {
			Element serv = (Element) Link.doc.getDocumentElement()
					.getElementsByTagName("server").item(index);
			if (serv.getAttribute("ip").equals(ip)) {
				makeServerElement = false;
			}
		}

		if (makeServerElement) {
			Element element = Link.doc.createElement("server");
			element.setAttribute("ip", ip);
			Link.doc.getDocumentElement().appendChild(element);

			serverIps.add(ip);
			servers.add(server.isConnected() ? server : null);
			serverDisplay.add(ip + " - "
					+ (server.isConnected() ? "Connectable" : "Unreachable"));
			GUI.resetServerPane();
		}
	}

	public static void deleteServer(String ip) {
		for (int index = 0; index < Link.doc.getDocumentElement()
				.getElementsByTagName("server").getLength(); index++) {
			Element server = (Element) Link.doc.getDocumentElement()
					.getElementsByTagName("server").item(index);
			if (server.getAttribute("ip").equals(ip)) {
				Link.doc.getDocumentElement().removeChild(server);
			}
		}

		System.out.println("serverDisplay: "
				+ serverDisplay.get(selectedServer));
		System.out.println("serverIp: " + serverIps.get(selectedServer));
		System.out.println("servers: " + servers.get(selectedServer));

		serverDisplay.remove(selectedServer);
		serverIps.remove(selectedServer);
		servers.remove(selectedServer);

		GUI.resetServerPane();

	}

	public static Socket getSelectedServerSocket(String ip) {
		for (int i = 0; i < serverIps.size(); i++) {
			if (serverIps.get(i).equals(ip)) {
				return servers.get(i);
			}
		}
		return null;
	}

	public static void clear() {
		for (Socket server : servers) {
			if (server != null && server.isConnected()) {
				PrintWriter toServer = null;
				try {
					toServer = new PrintWriter(server.getOutputStream(), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				toServer.println("exit");
			}
		}
		servers.clear();
		serverIps.clear();
		serverDisplay.clear();
	}

	public static ArrayList<String> getServerDetails(String ip) {

		final ArrayList<String> list = new ArrayList<String>();
		PrintWriter toServer = null;
		Socket server = null;

		for (int i = 0; i < servers.size(); i++) {
			if (servers.get(i) != null
					&& serverIps.get(i).equals(
							servers.get(i).getInetAddress().getHostAddress())) {
				server = servers.get(i);
			}
		}

		if (server != null
				&& server.getInetAddress().getHostAddress().equals(ip)) {
			System.out.println("connected to server at "
					+ server.getInetAddress().getHostAddress());
			try {
				toServer = new PrintWriter(server.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fromServer = new BufferedReader(new InputStreamReader(
						server.getInputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			System.out.println("Trying to get server details");

			toServer.println("reqservdata");
			System.out.println("Sent request");

			new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					try {
						list.add(fromServer.readLine());
						System.out.println("Recv data");
					} catch (Exception e) {
						e.printStackTrace();
					}

					return list;
				}
			}.execute();

		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<String> scan() {
		clear();

		ready = false;

		for (int f = 210; f <= 211; f++) {
			for (int i = 0; i <= 255; i++) {
				String ip = "";
				try {
					ip = InetAddress.getLocalHost().getHostAddress()
							.substring(0, 8)
							+ f + "." + i;
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				try {
					Socket server = new Socket();
					SocketAddress sock = new InetSocketAddress(ip, 5679);
					server.connect(sock, 5000);
					System.out.println("connected to server at " + ip);

					serverIps.add(ip);
					servers.add(server);

				} catch (Exception e) {
					System.out.println("Unable to connect to server at " + ip);
				}
			}
		}

		ready = true;

		return serverIps;
	}
}
