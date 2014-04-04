package workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Piece extends JButton {

	private static final long serialVersionUID = 1L;
	private String color = "";
	private String type = "";
	private int row;
	private int col;
	private String img = "";

	public Piece() {
	}

	public Piece(String color, String type, int row, int col) {
		this.setColor(color);
		this.setType(type);
		this.setRow(row);
		this.setCol(col);
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
		try {
			if (img.isEmpty()) {
				setIcon(null);
			} else {
				setIcon(new ImageIcon(ImageIO.read(new File("imgs/" + img
						+ ".png"))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void moveit(ArrayList<int[]> moves, int newRow, int newCol) {
		int[] move = new int[2];
		move[0] = newRow;
		move[1] = newCol;
		moves.add(move);
	}
	
	public void checkPieces(int row, int col, String color, Boolean b,
			ArrayList<int[]> m) {
		// ArrayList<int[]> whites = Board.getWhites();
		// ArrayList<int[]> blacks = Board.getBlacks();
		// int[] x = { row, col };
		Boolean bool = true;
		// if (color.equals("w")) {
		// bool = whites.contains(x) == b;
		// } else if (color.equals("b")) {
		// bool = blacks.contains(x) == b;
		// } else {
		// bool = false;
		// }
		if (bool) {
			moveit(m, row, col);
		}
	}

	public void checkPos(Piece p) {
		int[] x = new int[2];
		x[0] = p.row;
		x[1] = p.col;
		JOptionPane.showMessageDialog(null, x.toString());
	}
	// public int[][] whitepossMoves() {
	//
	// String t = this.type;
	// int row = this.row;
	// int col = this.col;
	// ArrayList<int[]> moves = new ArrayList<int[]>();
	// ArrayList<int[]> whites = Board.getWhites();
	// ArrayList<int[]> blacks = Board.getBlacks();
	// // if (t.equals("pawn")) {
	// //// if (row == 6) {
	// //// int[] move = new int[2];
	// //// move[0] = 5;
	// //// move[1] = col;
	// //// moves.add(move);
	// //// }
	// int[] x = { row - 1, col };
	// if (!whites.contains(x) && !blacks.contains(x)) {
	// moveit(moves, row - 1, col); // if no piece
	// }
	// checkPieces(row - 1, col - 1, "b", true, moves);// only if black
	// // piece
	//
	// checkPieces(row - 1, col + 1, "b", true, moves);// only if black
	// for (int i = 0; i < moves.size(); i++) {
	// System.out.println(moves.get(i)[0] + "," + moves.get(i)[1]);// piece
	// }
	//
	// } else if (t.equals("knight")) {
	// // only if no white pieces
	// checkPieces(row - 2, col - 1, "w", false, moves);
	// checkPieces(row - 2, col - 1, "w", false, moves);
	// checkPieces(row - 1, col - 2, "w", false, moves);
	// checkPieces(row - 1, col + 2, "w", false, moves);
	// checkPieces(row - 2, col + 1, "w", false, moves);
	// checkPieces(row + 1, col - 2, "w", false, moves);
	// checkPieces(row + 1, col + 2, "w", false, moves);
	// checkPieces(row + 2, col - 1, "w", false, moves);
	// checkPieces(row + 2, col + 1, "w", false, moves);
	// } else if (t.equals("bishop")) {
	// for (int i = 0; i < 8; i++) {
	// if (i != row) {
	// moveit(moves, i, col - (row - i)); // less than row:
	// // if piece, cut out
	// // those
	// // before it,
	// // greater: break;
	// moveit(moves, i, col + (row - i)); // same thing
	// }
	// }
	// } else if (t.equals("rook")) {
	// for (int i = 0; i < 8; i++) {
	// if (i != row) {
	// moveit(moves, i, col);// same as bishop
	// }
	// if (i != col) {
	// moveit(moves, row, i);// same as bishop
	// }
	// }
	// } else if (t.equals("queen")) {
	// for (int i = 0; i < 8; i++) {
	// if (i != row) {
	// moveit(moves, i, col);// same as bishop
	// moveit(moves, i, col - (row - i));// bishop
	// moveit(moves, i, col + (row - i));// bishop
	// }
	// if (i != col) {
	// moveit(moves, row, i);// bishop
	// }
	// }
	// } else if (t.equals("king")) {
	// // only if no whites
	// moveit(moves, row + 1, col);
	// moveit(moves, row - 1, col);
	// moveit(moves, row, col + 1);
	// moveit(moves, row, col - 1);
	// moveit(moves, row + 1, col + 1);
	// moveit(moves, row + 1, col - 1);
	// moveit(moves, row - 1, col - 1);
	// moveit(moves, row - 1, col + 1);
	// }
	// int[][] possmoves = new int[moves.size()][moves.size()];
	// for (int i = 0; i < moves.size(); i++) {
	// for (int j = 0; j < 2; j++) {
	// possmoves[i][j] = moves.get(i)[j];
	// }
	// }
	// return possmoves;
	// }

}