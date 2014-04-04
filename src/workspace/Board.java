package workspace;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Board extends JPanel {

	private static Piece[][] board = new Piece[8][8];

	public int color(Piece p, int moverow, int movecol, Boolean isThere,
			Boolean colorit) {
		int row = p.getRow() + moverow;
		int col = p.getCol() + movecol;
		Boolean isWhite = Link.whoseTurn.equals("w");
		if (row <= 7 && row >= 0 && col <= 7 && col >= 0) {
			if (isWhite) {
				if (board[row][col].getImg().isEmpty()
						|| board[row][col].getImg().substring(0, 1).equals("w") == isThere) {
					if (!board[row][col].getImg().isEmpty()) {
						if (colorit) {
							board[row][col].setBackground(Color.RED);
						}
						return 42;
					} else {
						if (colorit) {
							board[row][col].setBackground(Color.YELLOW);
						}
						return 1;
					}
				}
			} else {
				if (board[row][col].getImg().isEmpty()
						|| board[row][col].getImg().substring(0, 1).equals("b") == isThere) {
					if (!board[row][col].getImg().isEmpty()) {
						if (colorit) {
							board[row][col].setBackground(Color.RED);
						}
						return 42;
					} else {
						if (colorit) {
							board[row][col].setBackground(Color.YELLOW);
						}
						return 1;
					}
				}
			}

		}
		return 9001;
	}

	public ArrayList<ArrayList<int[]>> colorarr(Piece p, int moverow,
			int movecol, Boolean isWhite, Boolean isThere) {
		int row = p.getRow() + moverow;
		int col = p.getCol() + movecol;
		ArrayList<int[]> opens = new ArrayList<int[]>();
		ArrayList<int[]> attacks = new ArrayList<int[]>();
		int[] x = new int[2];
		ArrayList<ArrayList<int[]>> results = new ArrayList<ArrayList<int[]>>();
		if (row <= 7 && row >= 0 && col <= 7 && col >= 0) {
			if (isWhite) {
				if (board[row][col].getImg().isEmpty()
						|| board[row][col].getImg().substring(0, 1).equals("w") == isThere) {
					if (!board[row][col].getImg().isEmpty()) {
						x[0] = row;
						x[1] = col;
						attacks.add(x);
					} else {
						x[0] = row;
						x[1] = col;
						opens.add(x);
					}
				}
			} else {
				if (board[row][col].getImg().isEmpty()
						|| board[row][col].getImg().substring(0, 1).equals("b") == isThere) {
					if (!board[row][col].getImg().isEmpty()) {
						x[0] = row;
						x[1] = col;
						attacks.add(x);
					} else {
						x[0] = row;
						x[1] = col;
						opens.add(x);
					}
				}
			}

		}
		results.add(opens);
		results.add(attacks);
		return results;
	}

	public ArrayList<int[]> check(String color) {
		ArrayList<int[]> attacks = new ArrayList<int[]>();
		int[] x = new int[2];
		if (color.equals("w")) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					Piece p = board[r][c];
					if (!p.getImg().isEmpty()
							&& p.getImg().substring(0, 1).equals("b")) {
						if (p.getImg().substring(1, 2).equals("p")) {
							if (c > 0) {
								if (!board[r + 1][p.getCol() + 1].getImg()
										.isEmpty()
										&& board[r + 1][c + 1].getImg()
												.substring(0, 1).equals("w")) {
									x[0] = r + 1;
									x[1] = c + 1;
									attacks.add(x);

								}
							}
							if (c < 7) {
								if (!board[r + 1][c - 1].getImg().isEmpty()
										&& board[r + 1][c - 1].getImg()
												.substring(0, 1).equals("w")) {
									x[0] = r + 1;
									x[1] = c - 1;
									attacks.add(x);
								}
							}
						}
						if (p.getImg().substring(1, 2).equals("n")) {
							attacks.addAll(colorarr(p, -1, -2, true, false)
									.get(1));
							attacks.addAll(colorarr(p, -1, 2, true, false).get(
									1));
							attacks.addAll(colorarr(p, -2, -1, true, false)
									.get(1));
							attacks.addAll(colorarr(p, -2, 1, true, false).get(
									1));
							attacks.addAll(colorarr(p, 1, -2, true, false).get(
									1));
							attacks.addAll(colorarr(p, 1, 2, true, false)
									.get(1));
							attacks.addAll(colorarr(p, 2, -1, true, false).get(
									1));
							attacks.addAll(colorarr(p, 2, 1, true, false)
									.get(1));
						}
						if (p.getImg().substring(1, 2).equals("r")) {
							for (int i = 1; i < 8; i++) {
								int y = color(p, i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r + i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, -i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r - i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, -i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c - i;
									attacks.add(x);
									break;
								}
							}
						} else if (p.getImg().substring(1, 2).equals("b")) {
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
						} else if (p.getImg().substring(1, 2).equals("q")) {
							for (int i = 1; i < 8; i++) {
								int y = color(p, i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r + i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, -i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r - i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, -i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c - i;
									attacks.add(x);
									break;
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
						}
					}
				}
			}
		} else if (color.equals("b")) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					Piece p = board[r][c];
					if (!p.getImg().isEmpty()
							&& p.getImg().substring(0, 1).equals("w")) {
						if (p.getImg().substring(1, 2).equals("p")) {
							if (c > 0) {
								if (!board[r - 1][c - 1].getImg().isEmpty()
										&& board[r - 1][c - 1].getImg()
												.substring(0, 1).equals("b")) {
									x[0] = r - 1;
									x[1] = c - 1;
									attacks.add(x);

								}
							}
							if (c < 7) {
								if (!board[r - 1][c + 1].getImg().isEmpty()
										&& board[r - 1][c + 1].getImg()
												.substring(0, 1).equals("b")) {
									x[0] = r - 1;
									x[1] = c + 1;
									attacks.add(x);
								}
							}
						}
						if (p.getImg().substring(1, 2).equals("n")) {
							attacks.addAll(colorarr(p, -1, -2, true, false)
									.get(1));
							attacks.addAll(colorarr(p, -1, 2, true, false).get(
									1));
							attacks.addAll(colorarr(p, -2, -1, true, false)
									.get(1));
							attacks.addAll(colorarr(p, -2, 1, true, false).get(
									1));
							attacks.addAll(colorarr(p, 1, -2, true, false).get(
									1));
							attacks.addAll(colorarr(p, 1, 2, true, false)
									.get(1));
							attacks.addAll(colorarr(p, 2, -1, true, false).get(
									1));
							attacks.addAll(colorarr(p, 2, 1, true, false)
									.get(1));
						}
						if (p.getImg().substring(1, 2).equals("r")) {
							for (int i = 1; i < 8; i++) {
								int y = color(p, i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r + i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, -i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r - i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, -i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c - i;
									attacks.add(x);
									break;
								}
							}
						} else if (p.getImg().substring(1, 2).equals("b")) {
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
						} else if (p.getImg().substring(1, 2).equals("q")) {
							for (int i = 1; i < 8; i++) {
								int y = color(p, i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r + i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, -i, 0, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r - i;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c;
									attacks.add(x);
									break;
								}
							}
							for (int i = 1; i < 8; i++) {
								int y = color(p, 0, -i, false, true);
								if (y > 9000) {
									break;
								}
								if (y == 42) {
									x[0] = r;
									x[1] = c - i;
									attacks.add(x);
									break;
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, -i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r - i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c + i;
										attacks.add(x);
										break;
									}
								}
							}
							for (int i = 0; i < 8; i++) {
								if (i != 0) {
									int y = color(p, i, -i, false, true);
									if (y > 9000) {
										break;
									}
									if (y == 42) {
										x[0] = r + i;
										x[1] = c - i;
										attacks.add(x);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		resetBoardBackground();
		return attacks;
	}

	public Board() {
		super(new GridLayout(8, 8));

		boolean makeBlack = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				final Piece piece = new Piece("", "", r, c);
				piece.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {

						int row = piece.getRow();
						int col = piece.getCol();
						Boolean kingcastle = false;
						Boolean queencastle = false;
						if (Link.playingAs.equals(Link.whoseTurn)) {

							if (Link.pieceSelected.isEmpty()) {
								resetBoardBackground();
								if (!piece.getImg().isEmpty()
										&& piece.getImg().substring(0, 1)
												.equals(Link.whoseTurn)) {
									Link.pieceSelected = piece.getImg()
											.substring(1, 2);
									Link.selectedDim[0] = row;
									Link.selectedDim[1] = col;
									piece.setBackground(new Color(0, 20, 255, 1));

									if (!piece.getImg().isEmpty()
											&& Link.pieceSelected.equals("p")) {
										if (Link.whoseTurn.equals("w")) {
											if (board[row - 1][piece.getCol()]
													.getImg().isEmpty()) {
												board[row - 1][piece.getCol()]
														.setBackground(Color.YELLOW);
												if (row == 6
														&& board[row - 2][piece
																.getCol()]
																.getImg()
																.isEmpty()) {
													board[row - 2][piece
															.getCol()]
															.setBackground(Color.YELLOW);
												}

											}
											if (col > 0) {
												if (!board[row - 1][piece
														.getCol() - 1].getImg()
														.isEmpty()
														&& board[row - 1][piece
																.getCol() - 1]
																.getImg()
																.substring(0, 1)
																.equals("b")) {
													board[row - 1][piece
															.getCol() - 1]
															.setBackground(Color.RED);
												}
											}
											if (col < 7) {
												if (!board[row - 1][piece
														.getCol() + 1].getImg()
														.isEmpty()
														&& board[row - 1][piece
																.getCol() + 1]
																.getImg()
																.substring(0, 1)
																.equals("b")) {
													board[row - 1][piece
															.getCol() + 1]
															.setBackground(Color.RED);
												}
											}
										} else {
											if (board[row - 1][piece.getCol()]
													.getImg().isEmpty()) {
												board[row - 1][piece.getCol()]
														.setBackground(Color.YELLOW);
												if (row == 6
														&& board[row - 2][piece
																.getCol()]
																.getImg()
																.isEmpty()) {
													board[row - 2][piece
															.getCol()]
															.setBackground(Color.YELLOW);
												}

											}
											if (col > 0) {
												if (!board[row - 1][piece
														.getCol() - 1].getImg()
														.isEmpty()
														&& board[row - 1][piece
																.getCol() - 1]
																.getImg()
																.substring(0, 1)
																.equals("w")) {
													board[row - 1][piece
															.getCol() - 1]
															.setBackground(Color.RED);
												}
											}
											if (col < 7) {
												if (!board[row - 1][piece
														.getCol() + 1].getImg()
														.isEmpty()
														&& board[row - 1][piece
																.getCol() + 1]
																.getImg()
																.substring(0, 1)
																.equals("w")) {
													board[row - 1][piece
															.getCol() + 1]
															.setBackground(Color.RED);
												}
											}
										}
									} else if (!piece.getImg().isEmpty()
											&& piece.getImg().substring(1, 2)
													.equals("n")) {
										color(piece, -1, -2, false, true);
										color(piece, -1, 2, false, true);
										color(piece, -2, -1, false, true);
										color(piece, -2, 1, false, true);
										color(piece, 1, -2, false, true);
										color(piece, 1, 2, false, true);
										color(piece, 2, -1, false, true);
										color(piece, 2, 1, false, true);
									} else if (!piece.getImg().isEmpty()
											&& piece.getImg().substring(1, 2)
													.equals("r")) {
										for (int i = 1; i < 8; i++) {
											int x = color(piece, i, 0, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, -i, 0, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, 0, i, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, 0, -i, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
									} else if (!piece.getImg().isEmpty()
											&& piece.getImg().substring(1, 2)
													.equals("b")) {
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, -i, -i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, -i, i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, i, i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, i, -i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
									} else if (!piece.getImg().isEmpty()
											&& piece.getImg().substring(1, 2)
													.equals("q")) {
										for (int i = 1; i < 8; i++) {
											int x = color(piece, i, 0, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}

										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, -i, 0, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}

										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, 0, i, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
										for (int i = 1; i < 8; i++) {
											int x = color(piece, 0, -i, false,
													true);
											if (x > 9000 || x == 42) {
												break;
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, -i, -i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, -i, i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, i, i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
										for (int i = 0; i < 8; i++) {
											if (i != 0) {
												int x = color(piece, i, -i,
														false, true);
												if (x > 9000 || x == 42) {
													break;
												}
											}
										}
									} else if (!piece.getImg().isEmpty()
											&& piece.getImg().substring(1, 2)
													.equals("k")) {
										if (col == 4 && (row == 0 || row == 7)) {
											if (board[7][0].getImg().equals(
													"wr")
													&& board[7][1].getImg()
															.isEmpty()
													&& board[7][2].getImg()
															.isEmpty()
													&& board[7][3].getImg()
															.isEmpty()) {
												queencastle = true;
											}
											if (board[7][7].getImg().equals(
													"wr")
													&& board[7][6].getImg()
															.isEmpty()
													&& board[7][5].getImg()
															.isEmpty()) {
												kingcastle = true;
												board[7][6]
														.setBackground(Color.YELLOW);
											}
										}
										ArrayList<int[]> checks = check("b");
										for (int[] arr : checks) {
											System.out.print(Arrays
													.toString(arr) + ", ");
										}
										int[] x1 = { row, col - 1 };
										int[] x2 = { row - 1, col };
										int[] x3 = { row - 1, col - 1 };
										int[] x4 = { row + 1, col };
										int[] x5 = { row, col + 1 };
										int[] x6 = { row + 1, col + 1 };
										int[] x7 = { row + 1, col - 1 };
										int[] x8 = { row - 1, col + 1 };
										if (!checks.contains(x1)
												&& (x1[0] >= 0 && x1[0] <= 7
														&& x1[1] >= 0 && x1[1] <= 7)) {
											if (board[x1[0]][x1[1]].getImg()
													.isEmpty()) {
												board[x1[0]][x1[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x1[0]][x1[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x1[0]][x1[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x2)
												&& (x2[0] >= 0 && x2[0] <= 7
														&& x2[1] >= 0 && x2[1] <= 7)) {
											if (board[x2[0]][x2[1]].getImg()
													.isEmpty()) {
												board[x2[0]][x2[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x2[0]][x2[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x2[0]][x2[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x3)
												&& (x3[0] >= 0 && x3[0] <= 7
														&& x3[1] >= 0 && x3[1] <= 7)) {
											if (board[x3[0]][x3[1]].getImg()
													.isEmpty()) {
												board[x3[0]][x3[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x3[0]][x3[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x3[0]][x3[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x4)
												&& (x4[0] >= 0 && x4[0] <= 7
														&& x4[1] >= 0 && x4[1] <= 7)) {
											if (board[x4[0]][x4[1]].getImg()
													.isEmpty()) {
												board[x4[0]][x4[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x4[0]][x4[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x4[0]][x4[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x5)
												&& (x5[0] >= 0 && x5[0] <= 7
														&& x5[1] >= 0 && x5[1] <= 7)) {
											if (board[x5[0]][x5[1]].getImg()
													.isEmpty()) {
												board[x5[0]][x5[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x5[0]][x5[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x5[0]][x5[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x6)
												&& (x6[0] >= 0 && x6[0] <= 7
														&& x6[1] >= 0 && x6[1] <= 7)) {
											if (board[x6[0]][x6[1]].getImg()
													.isEmpty()) {
												board[x6[0]][x6[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x6[0]][x6[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x6[0]][x6[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x7)
												&& (x7[0] >= 0 && x7[0] <= 7
														&& x7[1] >= 0 && x7[1] <= 7)) {
											if (board[x7[0]][x7[1]].getImg()
													.isEmpty()) {
												board[x7[0]][x7[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x7[0]][x7[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x7[0]][x7[1]]
														.setBackground(Color.RED);
											}
										}
										if (!checks.contains(x8)
												&& (x8[0] >= 0 && x8[0] <= 7
														&& x8[1] >= 0 && x8[1] <= 7)) {
											if (board[x8[0]][x8[1]].getImg()
													.isEmpty()) {
												board[x8[0]][x8[1]]
														.setBackground(Color.YELLOW);
											} else if (!board[x8[0]][x8[1]]
													.getImg().substring(0, 1)
													.equals(Link.whoseTurn)) {
												board[x8[0]][x8[1]]
														.setBackground(Color.RED);
											}
										}
									}
								}

							} else {
								Piece oldPiece = board[Link.selectedDim[0]][Link.selectedDim[1]];
								if (piece.getBackground() == Color.YELLOW
										|| piece.getBackground() == Color.RED) {
									if (kingcastle) {
										piece.setImg("wk");
										oldPiece.setImg("");
										board[7][5].setImg("wr");
										board[7][7].setImg("");
										kingcastle = false;
									}
									if (queencastle
											&& oldPiece.getImg().equals("wk")) {
										piece.setImg("wk");
										oldPiece.setImg("");
										board[7][3].setImg("wr");
										board[7][0].setImg("");
										queencastle = false;
									} else {
										piece.setImg(board[Link.selectedDim[0]][Link.selectedDim[1]]
												.getImg());
										board[Link.selectedDim[0]][Link.selectedDim[1]]
												.setImg("");
									}
									int[] start = Link.selectedDim;
									int[] end = { row, col };
									Client.toServer.println(Link.userName
											+ "-move|" + Arrays.toString(start)
											+ ">" + Arrays.toString(end));
									System.out
											.println("Sending piece move data.");
								}
								Link.pieceSelected = "";
								resetBoardBackground();
							}
						}
					}
				});


				if (makeBlack) {
					piece.setBackground(Color.LIGHT_GRAY);
				} else {
					piece.setBackground(Color.WHITE);
				}

				piece.setFocusPainted(false);

				makeBlack = !makeBlack;

				if (r == 1) {
					piece.setImg("bp");
					piece.setType("pawn");
					piece.setColor("black");
				}
				if (r == 6) {
					piece.setImg("wp");
					piece.setType("pawn");
					piece.setColor("white");
				}
				if (r == 0 && (c == 0 || c == 7)) {
					piece.setImg("br");
					piece.setType("rook");
					piece.setColor("black");
				}
				if (r == 7 && (c == 0 || c == 7)) {
					piece.setImg("wr");
					piece.setType("rook");
					piece.setColor("white");
				}
				if (r == 0 && (c == 1 || c == 6)) {
					piece.setImg("bn");
					piece.setType("knight");
					piece.setColor("black");
				}
				if (r == 7 && (c == 1 || c == 6)) {
					piece.setImg("wn");
					piece.setType("knight");
					piece.setColor("white");
				}
				if (r == 0 && (c == 2 || c == 5)) {
					piece.setImg("bb");
					piece.setType("bishop");
					piece.setColor("black");
				}
				if (r == 7 && (c == 2 || c == 5)) {
					piece.setImg("wb");
					piece.setType("bishop");
					piece.setColor("white");
				}
				if (r == 0 && c == 3) {
					piece.setImg("bq");
					piece.setType("queen");
					piece.setColor("black");
				}
				if (r == 7 && c == 3) {
					piece.setImg("wq");
					piece.setType("queen");
					piece.setColor("white");
				}
				if (r == 0 && c == 4) {
					piece.setImg("bk");
					piece.setType("king");
					piece.setColor("black");
				}
				if (r == 7 && c == 4) {
					piece.setImg("wk");
					piece.setType("king");
					piece.setColor("white");
				}

				board[r][c] = piece;
				add(piece);
			}
			makeBlack = !makeBlack;
		}

	}

	public static void recieveMove(String move) {
		if (move.indexOf(Link.userName.toUpperCase()) == -1) {
			try {
				int arrow = move.indexOf(">");
				int oldRow = Integer.parseInt(move.substring(arrow - 5,
						arrow - 4));
				int oldCol = Integer.parseInt(move.substring(arrow - 2,
						arrow - 1));
				int newRow = Integer.parseInt(move.substring(arrow + 2,
						arrow + 3));
				int newCol = Integer.parseInt(move.substring(arrow + 5,
						arrow + 6));
				oldRow = 7 - oldRow;
				oldCol = 7 - oldCol;
				newRow = 7 - newRow;
				newCol = 7 - newCol;
				String temp = board[oldRow][oldCol].getImg();
				board[oldRow][oldCol].setImg("");
				board[newRow][newCol].setImg(temp);

				System.out.println("end of receiveMove");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void resetBoardBackground() {
		boolean makeBlack = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Piece piece = board[r][c];
				if (makeBlack) {
					piece.setBackground(Color.LIGHT_GRAY);
				} else {
					piece.setBackground(Color.WHITE);
				}

				makeBlack = !makeBlack;
			}
			makeBlack = !makeBlack;
		}
	}

	public String originalColor(int row, int col) {
		boolean makeBlack = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (row == r && col == c) {
					if (makeBlack) {
						return "b";
					} else {
						return "w";
					}
				}
				makeBlack = !makeBlack;
			}
			makeBlack = !makeBlack;
		}
		return "";
	}

	public static void flipBoard() {
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 8; c++) {
				String temp = board[r][c].getImg();
				board[r][c].setImg(board[7 - r][7 - c].getImg());
				board[7 - r][7 - c].setImg(temp);
			}
		}
	}
}