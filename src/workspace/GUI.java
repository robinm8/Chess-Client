package workspace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private static JPanel content = null;
	static JPanel game = new JPanel(new BorderLayout());
	private JPanel menu = new JPanel(new GridLayout(3, 1));
	private JPanel serverBrowser = new JPanel(new BorderLayout());
	private JPanel editSavedServerPane = new JPanel(new BorderLayout());
	private JPanel editSavedServerCenter = new JPanel(new GridBagLayout());

	private GridBagConstraints serverPaneConstraints = new GridBagConstraints();

	private JPanel leftServerPanel = new JPanel(new BorderLayout());
	private JPanel centerServerButtons = new JPanel(new GridLayout(3, 0));
	private JPanel serverPanel = new JPanel(new BorderLayout());
	private JPanel serverManagement = new JPanel(new GridLayout(0, 3));

	private JLabel title = new JLabel("Chess");
	private JLabel serverTitle = new JLabel("Play Multiplayer");
	private JLabel serverManagementTitle = new JLabel("Edit Server Info");
	private JLabel serverAddress = new JLabel("Server Address");

	private JTextField serverAddressEntry = new JTextField(15);

	private JButton start = new JButton("Singleplayer");
	private JButton serverButton = new JButton("Multiplayer");
	private static JButton playAsWhite = new JButton("Play As White");
	private static JButton playAsBlack = new JButton("Play As Black");
	private JButton backToMenu = new JButton("Cancel");
	private JButton addServer = new JButton("Add");
	private JButton editServer = new JButton("Edit");
	private JButton delServer = new JButton("Delete");
	private JButton doneServerInput = new JButton("Done");

	static JPanel chat = new JPanel(new BorderLayout());
	static JPanel bd = new Board();
	private JPanel rightContainer = new JPanel(new BorderLayout());
	private JPanel treePanel = new JPanel();
	private JScrollPane treeScroll = new JScrollPane(treePanel);
	private JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bd,
			rightContainer);

	private static JTextArea serverDetails = new JTextArea();
	private JList serverList = new JList();
	private JScrollPane serverListScroll = new JScrollPane(serverList);

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Dimension screenDim = toolkit.getScreenSize();

	private static JLabel footer = new JLabel("Mark Robinson & Derian Haas");

	public GUI() {
		super("Chess");

		setLayout(new BorderLayout());

		try {
			content = new BackgroundPanel(new BorderLayout(),
					ImageIO.read(new File("imgs/bg.png")));
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					ImageIO.read(new File("imgs/mouse.png")), new Point(8, 6),
					"Game Cursor"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"Chess was unable to find images",
					"Alert - Images Not Found", JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
				if (!Link.userName.isEmpty() && Client.server != null) {
					Client.toServer.println(Link.userName + "-exit");
				} else {
					if (Client.server != null) {
						Client.toServer.println("e404");
						Client.toServer.println("e404-exit");
					}
				}
				
				LocalServers.clear();
				
			}
		});

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchContentDisplay(game);
			}
		});

		serverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchContentDisplay(serverBrowser);
				new SwingWorker() {
					protected Object doInBackground() throws Exception {
						serverList.setListData(LocalServers.serverDisplay
								.toArray());
						serverList.revalidate();
						serverList.repaint();
						return null;
					}
				}.execute();
			}
		});

		serverButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				content.revalidate();
				content.repaint();
				revalidate();
				repaint();
				serverButton.revalidate();
				serverButton.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				content.revalidate();
				content.repaint();
				revalidate();
				repaint();
				serverButton.revalidate();
				serverButton.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				content.revalidate();
				content.repaint();
				revalidate();
				repaint();
				serverButton.revalidate();
				serverButton.repaint();

			}
		});

		serverList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					playAsBlack.setVisible(false);
					playAsWhite.setVisible(false);
					serverDetails.setText("");
					updateFromServerData();
				}
			}
		});

		doneServerInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchContentDisplay(serverBrowser);
				LocalServers.saveServer(serverAddressEntry.getText());
				serverList.setListData(LocalServers.serverDisplay.toArray());
			}
		});

		backToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playAsBlack.setVisible(false);
				playAsWhite.setVisible(false);
				serverDetails.setText("");
				switchContentDisplay(menu);
			}
		});

		playAsBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFromServerData();
				if (playAsBlack.isVisible()) {
					Link.playingAs = "b";
					Board.flipBoard();
					Client.start();
					Client.toServer.println(Link.userName + "-play black");
				} else {
					JOptionPane.showConfirmDialog(null,
							"Sorry, this color has already been assigned.",
							"Color ", JOptionPane.OK_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		playAsWhite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFromServerData();
				if (playAsWhite.isVisible()) {
					Link.playingAs = "w";
					Client.start();
					Client.toServer.println(Link.userName + "-play white");
				} else {
					JOptionPane.showConfirmDialog(null,
							"Sorry, this color has already been assigned.",
							"Color ", JOptionPane.OK_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		addServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverAddressEntry.setText("");
				switchContentDisplay(editSavedServerPane);
				serverList.setListData(LocalServers.serverDisplay.toArray());
			}
		});

		editServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serverAddressEntry.setText(LocalServers.serverIps
							.get(LocalServers.selectedServer));
					switchContentDisplay(editSavedServerPane);
					LocalServers.deleteServer(serverAddressEntry.getText());
					serverList.setListData(LocalServers.serverDisplay.toArray());
				} catch (IndexOutOfBoundsException e1) {
				}
			}
		});

		delServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalServers.deleteServer(LocalServers.serverIps
						.get(LocalServers.selectedServer));
				serverList.setListData(LocalServers.serverDisplay.toArray());
			}
		});

		doneServerInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalServers.saveServer(serverAddressEntry.getText());
			}
		});

		split.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		bd.setOpaque(false);
		game.setOpaque(false);
		menu.setOpaque(false);
		split.setOpaque(false);
		serverPanel.setOpaque(false);
		leftServerPanel.setOpaque(false);
		serverManagement.setOpaque(false);
		centerServerButtons.setOpaque(false);
		editSavedServerPane.setOpaque(false);
		editSavedServerCenter.setOpaque(false);

		chat.setOpaque(false);
		footer.setOpaque(false);
		treePanel.setOpaque(false);
		rightContainer.setOpaque(false);
		serverBrowser.setOpaque(false);
		serverDetails.setOpaque(false);
		treeScroll.getViewport().setOpaque(false);

		title.setOpaque(false);
		start.setOpaque(false);
		addServer.setOpaque(false);
		editServer.setOpaque(false);
		delServer.setOpaque(false);
		doneServerInput.setOpaque(false);
		backToMenu.setOpaque(false);
		playAsWhite.setOpaque(false);
		playAsBlack.setOpaque(false);

		start.setBackground(new Color(0, 0, 0, 0));
		start.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		serverButton.setBackground(new Color(0, 0, 0, 0));
		serverButton.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		playAsWhite.setBackground(new Color(0, 0, 0, 0));
		playAsWhite.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		playAsWhite.setVisible(false);
		playAsBlack.setBackground(new Color(0, 0, 0, 0));
		playAsBlack.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		playAsBlack.setVisible(false);
		backToMenu.setBackground(new Color(0, 0, 0, 0));
		backToMenu.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		addServer.setBackground(new Color(0, 0, 0, 0));
		addServer.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		editServer.setBackground(new Color(0, 0, 0, 0));
		editServer.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		delServer.setBackground(new Color(0, 0, 0, 0));
		delServer.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		serverDetails.setEditable(false);

		serverDetails.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		serverList.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		footer.setHorizontalAlignment(SwingConstants.CENTER);
		footer.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 65));
		serverTitle.setHorizontalAlignment(SwingConstants.CENTER);
		serverTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 58));
		serverManagementTitle.setHorizontalAlignment(SwingConstants.CENTER);
		serverManagementTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 58));
		start.setHorizontalAlignment(SwingConstants.CENTER);
		start.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		serverButton.setHorizontalAlignment(SwingConstants.CENTER);
		serverButton.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		playAsWhite.setHorizontalAlignment(SwingConstants.CENTER);
		playAsWhite.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		playAsBlack.setHorizontalAlignment(SwingConstants.CENTER);
		playAsBlack.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		backToMenu.setHorizontalAlignment(SwingConstants.CENTER);
		backToMenu.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		addServer.setHorizontalAlignment(SwingConstants.CENTER);
		addServer.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		editServer.setHorizontalAlignment(SwingConstants.CENTER);
		editServer.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		delServer.setHorizontalAlignment(SwingConstants.CENTER);
		delServer.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		serverAddress.setHorizontalAlignment(SwingConstants.LEFT);
		serverAddress.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		serverAddressEntry.setHorizontalAlignment(SwingConstants.CENTER);
		serverAddressEntry.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		doneServerInput.setHorizontalAlignment(SwingConstants.CENTER);
		doneServerInput.setFont(new Font("Trebuchet MS", Font.BOLD, 20));

		rightContainer.add(treePanel, BorderLayout.NORTH);
		rightContainer.add(chat, BorderLayout.CENTER);

		game.add(split, BorderLayout.CENTER);

		menu.add(title);
		//menu.add(start); -- To Do: Make chess AI player for Singleplayer mode 
		menu.add(serverButton);

		centerServerButtons.add(playAsWhite);
		centerServerButtons.add(playAsBlack);
		centerServerButtons.add(backToMenu);

		serverPanel.add(serverListScroll, BorderLayout.CENTER);
		serverPanel.add(serverDetails, BorderLayout.SOUTH);

		serverManagement.add(addServer);
		serverManagement.add(editServer);
		serverManagement.add(delServer);

		leftServerPanel.add(serverPanel, BorderLayout.CENTER);
		leftServerPanel.add(serverManagement, BorderLayout.SOUTH);

		serverBrowser.add(serverTitle, BorderLayout.NORTH);
		serverBrowser.add(leftServerPanel, BorderLayout.WEST);
		serverBrowser.add(centerServerButtons, BorderLayout.CENTER);

		serverPaneConstraints.weightx = 0.5;
		serverPaneConstraints.fill = GridBagConstraints.VERTICAL;
		serverPaneConstraints.gridx = 0;
		serverPaneConstraints.gridy = 0;
		serverPaneConstraints.ipadx = 300;
		serverPaneConstraints.anchor = GridBagConstraints.CENTER;
		editSavedServerCenter.add(serverAddress, serverPaneConstraints);

		serverPaneConstraints.fill = GridBagConstraints.VERTICAL;
		serverPaneConstraints.weightx = 0.5;
		serverPaneConstraints.gridx = 0;
		serverPaneConstraints.gridy = 1;
		serverPaneConstraints.ipady = 25;
		serverPaneConstraints.ipadx = 200;
		editSavedServerCenter.add(serverAddressEntry, serverPaneConstraints);
		serverPaneConstraints.weightx = 0.5;
		serverPaneConstraints.fill = GridBagConstraints.VERTICAL;
		serverPaneConstraints.gridx = 0;
		serverPaneConstraints.gridy = 2;
		serverPaneConstraints.ipadx = 100;
		serverPaneConstraints.insets = new Insets(200, 0, 0, 0);
		editSavedServerCenter.add(doneServerInput, serverPaneConstraints);

		editSavedServerPane.add(serverManagementTitle, BorderLayout.NORTH);
		editSavedServerPane.add(editSavedServerCenter);

		switchContentDisplay(menu);

		add(content);
		setVisible(true);
		setBounds((screenDim.width / 2) - 600, (screenDim.height / 2) - 400,
				1200, 800);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Waiting for data save operation");
				Link.timer.cancel();
				new SwingWorker() {
					@Override
					protected Object doInBackground() throws Exception {
						Thread.sleep(1000);
						System.out.println("Terminating");
						System.exit(0);
						return null;
					}
				}.execute();
			}
		});
	}

	public static void switchContentDisplay(JPanel to) {
		content.removeAll();
		content.add(to, BorderLayout.CENTER);
		content.add(footer, BorderLayout.SOUTH);

		content.revalidate();
		content.repaint();
		to.revalidate();
		to.repaint();
	}

	public static void resetServerPane() {
		playAsBlack.setVisible(false);
		playAsWhite.setVisible(false);
		serverDetails.setText("");
	}

	public void updateFromServerData() {
		Object[] list = LocalServers.serverDisplay.toArray();
		int foundInGameList = 0;
		for (int index = 0; index < list.length; index++) {
			if (list[index] != null
					&& list[index].equals(serverList.getModel().getElementAt(
							serverList.getSelectedIndex()))) {
				foundInGameList = index;
			}
		}

		LocalServers.selectedServer = foundInGameList;

		System.out.println("Set selected server as " + foundInGameList);

		System.out.println("serverDisplay: "
				+ LocalServers.serverDisplay.get(LocalServers.selectedServer));
		System.out.println("serverIp: "
				+ LocalServers.serverIps.get(LocalServers.selectedServer));
		System.out.println("servers: "
				+ LocalServers.servers.get(LocalServers.selectedServer));

		ArrayList<String> serverResponse = null;
		serverResponse = LocalServers.getServerDetails(LocalServers.serverIps
				.get(foundInGameList));

		if (!serverResponse.isEmpty()) {

			serverDetails.setText(serverResponse.get(0));

			System.out.println("server details: " + serverDetails.getText());

		}

		boolean blackAvail = (serverDetails.getText().indexOf("Black") != -1);
		boolean whiteAvail = (serverDetails.getText().indexOf("White") != -1);

		System.out.println(blackAvail);
		System.out.println(whiteAvail);

		playAsBlack.setVisible(blackAvail);
		playAsWhite.setVisible(whiteAvail);
	}

	/**
	 * Creates a custom JPanel with a painted background image.
	 * 
	 * @author Mark Robinson
	 */
	class BackgroundPanel extends JPanel {
		private Image image;

		/**
		 * Prepares for the image to be drawn to the JPanel.
		 * 
		 * @param layout
		 *            - Layout of the custom JPanel.
		 * @param image
		 *            - Image to the background of the custom JPanel
		 */
		public BackgroundPanel(LayoutManager layout, Image image) {
			this.image = image;
			repaint();
			revalidate();
			setLayout(layout);
		}

		/**
		 * Overrides the paintComponent to add my background image.
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image == null) {
				return;
			}
			g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
		}
	}
}