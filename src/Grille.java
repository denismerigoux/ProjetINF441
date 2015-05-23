import java.util.LinkedList;

public class Grille {
	// Cette classe repr�sente le quadrillage � paver pour la partie pavage du
	// projet
	// Ce peut �tre par exemple un �quiquier, ou un carr� �vid�

	int nbCol;
	int nbLigne;
	private final int[][] grid;

	// Convention interne de repr�sentation : 0 si la case est accessible,
	// n'importe quoi d'autre si elle ne l'est pas (ie quadrillage �vid�)

	public Grille(int nbCol, int nbLigne) {// Constructeur ^pour un quadrillage
											// entier
		this.nbCol = nbCol;
		this.nbLigne = nbLigne;
		this.grid = new int[nbLigne][nbCol];
	}

	public Grille(int nbCol, int nbLigne, int[][] g) {// Constructeur pour un
														// quadrillage
														// �ventuellement creux
		this.nbCol = nbCol;
		this.nbLigne = nbLigne;
		this.grid = new int[nbLigne][nbCol];
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbCol; j++) {
				if (g[i][j] == 0)
					this.grid[i][j] = 0;
				else
					this.grid[i][j] = 1;
			}
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbCol; j++) {
				if (grid[i][j] == 0)
					s += "*";
				else
					s += " ";
			}
			if (i < nbLigne - 1) {
				s += "\n";
			}
		}
		return s;
	}

	public boolean admissiblePositionForPiece(Piece p, int i, int j) {
		// Renvoie True ssi la piece p peut �tre pos�e � la position i, j pour
		// son coin en haut � gauche dans la grille
		// Coin en haut a gauche : 0, 0
		// Renvoie False par exemple si la piece est trop grande, ou interfere
		// avec les cases �vid�es
		// Sera utile pour g�n�rer l'ensemble des lignes de la matrice qu'on
		// enverra ensuite � l'algo DancingLinks
		// Il y a probleme si : la piece existe dans une case ou la grille
		// n'existe pas ; attention aux d�bordements
		if (i >= this.nbLigne || j >= this.nbCol)
			return false;
		for (int xp = 0; xp < p.taille; xp++) {
			for (int yp = 0; yp < p.taille; yp++) {
				if (p.motif[xp][yp] == 1) {
					if (i + xp >= this.nbLigne)
						return false;
					if (j + yp >= this.nbCol)
						return false;
					if (this.grid[i + xp][j + yp] != 0)
						return false;
				}

			}
		}

		return true;
	}

	public int numberOfValidCases() {
		// Cette m�thode renvoie le nombre de cases effectives de la grille (ie
		// l� ou il y a des 0
		// Cela repr�sentera aussi le nombre (enfin une partie) de colonnes de
		// la matrice qu'on va cr�er
		int cpt = 0;
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbCol; j++) {
				if (this.grid[i][j] == 0)
					cpt++;

			}
		}
		return cpt;
	}

	public LinkedList<Integer> getCoveredPositions(Piece p, int i, int j) {
		// Cette m�thode renvoie la liste des positions occup�es par une
		// certaine pi�ce, mais sous forme de coordonn�e unique, d�j� convertie
		// !
		if (!this.admissiblePositionForPiece(p, i, j)) {
			System.out.println("error error error");
			return null;
		}
		LinkedList<Integer> cov = new LinkedList<>();
		for (int xp = 0; xp < p.taille; xp++) {
			for (int yp = 0; yp < p.taille; yp++) {
				if (p.motif[xp][yp] == 1) {
					cov.add(this.convertToOneCoord(i + xp, j + yp));
				}

			}
		}
		return cov;
	}

	public int convertToOneCoord(int i, int j) {
		int l = 0;
		// M�thode bourrine : on compte le nombre de cases valides avant,
		// parcours en ligne de gauche a droite de haut en bas
		// TODO : a am�liorer
		// Ce n'est plus bug�, mais ca recalcule beaucoup trop souvent
		for (int x = 0; x <= i; x++) {
			for (int y = 0; y < this.nbCol; y++) {
				if (this.grid[x][y] == 0)
					l++;
				if (i == x && y == j)
					break;

			}
		}
		l--;
		// For debug
		// System.out.println("("+i+","+j+") -> "+l);
		return l;
	}
}
