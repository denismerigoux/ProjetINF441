import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class PavageParsing {

	public static LinkMatrix readPavageFromStandardInput(Grille gg) {
		try{
		// Cr�ation du buffer de lecture � partir du FileReader
		// Maintenant on peut faire .readLine() pour obtenir une ligne
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bufferReader = new BufferedReader(isr);
		LinkMatrix result = readPavage(bufferReader,gg);
		isr.close();
		return result;
		} catch (Exception e) {
			System.out.println("Erreur lors de la lecture de l'entrée.");
		}
		return null;
	}

	public static LinkMatrix readPavageFromFile(String file_name,Grille gg) {
		try{
		// Création du lecteur de fichier
		FileReader inputFile = new FileReader("src/tests/pavage/" + file_name);

		// Création du buffer de lecture à partir du FileReader
		// Maintenant on peut faire .readLine() pour obtenir une ligne
		BufferedReader bufferReader = new BufferedReader(inputFile);
		LinkMatrix result = readPavage(bufferReader,gg);
		inputFile.close();
		return result;
		} catch (Exception e) {
			System.out.println("Erreur : fichier introuvable ou autre");
		}
		return null;
	}

	private static LinkMatrix readPavage(BufferedReader bufferReader,Grille gg) {
		try {
			// String dans lequel on va stocker les lignes du fichier
			String line;

			// On lit la première ligne : nombre de colonnes de la grille
			line = bufferReader.readLine();
			int nbColGrille = Integer.parseInt(line);

			// On lit le nombre de lignes de la grille
			line = bufferReader.readLine();
			int nbLignesGrille = Integer.parseInt(line);

			// On remplit d'abord un tableau d'entier avec ce qui est lu dans le
			// fichier
			// Ce tableau sera la grille
			int[][] gridd = new int[nbLignesGrille][nbColGrille];
			int i = 0;
			line = bufferReader.readLine();
			while (i < nbLignesGrille) {
				for (int j = 0; j < nbColGrille; j++) {
					String c = Character.toString(line.charAt(j));
					if (c.equals("*")) {
						gridd[i][j] = 0;
					} else {
						gridd[i][j] = 1;
					}
				}
				i++;
				line = bufferReader.readLine();
			}

			// On crée la grille
			Grille grid = new Grille(nbColGrille, nbLignesGrille, gridd);
			// On la transf�re aussi sur le pointeur gg pour la r�cup�rer plus tard lors de l'affichage
			gg=null;
			System.out.println("gg : "+gg);
			gg=new Grille(nbColGrille, nbLignesGrille, gridd);
			
			System.out.println("gg :");
			System.out.println(gg);
			// Debug only : on affiche la grille
			System.out.println("Affichage de la grille à paver :");
			System.out.println(grid);
			System.out.println("Il y a " + grid.numberOfValidCases()
					+ " cases à paver dans cette grille.");
			// Maintenant on lit le nombre de pièces
			int nbPieces = Integer.parseInt(line);
			System.out.println("Le nombre de pièce est " + nbPieces + ".");

			// On va stocker les pièces dans un tableau :
			Piece[] pieces = new Piece[nbPieces];

			// On lit chaque pièce et on remplit le tableau de pièces
			for (int numPiece = 0; numPiece < nbPieces; numPiece++) {
				line = bufferReader.readLine();
				int nc = Integer.parseInt(line);
				line = bufferReader.readLine();
				int nl = Integer.parseInt(line);
				int[][] motif = new int[nl][nc];
				for (int nlp = 0; nlp < nl; nlp++) {
					line = bufferReader.readLine();
					for (int j = 0; j < nc; j++) {
						String c = Character.toString(line.charAt(j));
						if (c.equals("*")) {
							motif[nlp][j] = 1;
						} else {
							motif[nlp][j] = 0;
						}
					}

				}
				pieces[numPiece] = new Piece(motif);
				// Debug only : print each piece
				// System.out.println(pieces[numPiece]);
			}

			System.out.println("Génération de la matrice EMC");
			// Génération du tableau selon toutes les positions possibles de
			// chaque
			// piece
			int[][] allLines = generateLinesFromPieces(pieces, grid);
			System.out.println("La matrice comporte " + allLines.length
					+ " lignes et " + allLines[0].length + " colonnes.");

			// DebugUtils.affTab(allLines);
			// Il ne reste qu'à créer la LinkMatrix...
			LinkMatrix matrice = LinkMatrixCreation.createMatrixFromTab(
					allLines, allLines[0].length, 0, allLines.length);

			// On finalise
			bufferReader.close();
			// System.out.println("closing buffer, successful read");

			// On crée ensuite la matrice à partir du tableau
			// et on renvoie le résultat
			System.out.println("gg : "+gg);
			return matrice;

		} catch (Exception e) {
			System.out.println("Erreur en ouvrant le fichier Pavage, ou autre");
			System.err.println(e);
		}

		return null;

	}

	public static int[][] generateLinesFromPieces(Piece[] pieces, Grille grid) {
		// TODO A implémenter... et remettre private

		// Pour chaque pièce, il faut d'abord obtenir la liste des pièces
		// qu'elle génère, puis la faire bouger partout là ou c'est possible
		// Pour ce faire, on va utiliser la méthode
		// admissiblePositionForPiece(Piece p, int i,int j) de la classe Grille

		int totPos = 0; // On va maj ce nombre au fur et a mesure, il donnera a
						// la fin le nb de lignes remplies du tableau
		int nbColTypePavage = grid.numberOfValidCases(); // Nombre de colonnes
															// dans la partie
															// gauche de la
															// matrice
		int[][] tab = new int[pieces.length * 8 * nbColTypePavage][nbColTypePavage
				+ pieces.length]; // Représente notre future matrice
		// toutes les lignes ne seront pas utilisées il faudra recopier à la fin
		// Pire des cas : chaque piece se retourne 8 fois, et on peut la mettre
		// partout (pas très réaliste mais bon...)
		for (int numPiece = 0; numPiece < pieces.length; numPiece++) {
			LinkedList<Piece> genpieces = pieces[numPiece]
					.getListOfTransformations();
			// System.out.println("treating piece \n" + pieces[numPiece]
			// + " transfo : " + genpieces.size());
			// On récupère les différentes transformations de la piece de départ
			// Ensuite on les parcourt toutes
			for (Piece p : genpieces) {
				// Ici, il faut générer des lignes
				for (int i = 0; i < grid.nbLigne; i++) {
					for (int j = 0; j < grid.nbCol; j++) {
						// On fait bouger la pièce un peu partout
						if (grid.admissiblePositionForPiece(p, i, j)) {
							// Délicat : il faut obtenir les cases couvertes,
							// jusque là rien de bien méchant
							// MAIS il faut aussi transformer ces coordonnées
							// x,y en une seule coord sur la ligne, entre 0 et
							// numberOfValidCases !
							LinkedList<Integer> covered = grid
									.getCoveredPositions(p, i, j);
							for (int l : covered) {
								tab[totPos][l] = 1;
							}
							tab[totPos][nbColTypePavage + numPiece] = 1;
							totPos++;
						}
					}
				}

			}
		}

		// On rabote le tableau, de sorte qu'il n'y ait plus de ligne vide en
		// bas
		int[][] tabdef = new int[totPos][nbColTypePavage + pieces.length];
		for (int i = 0; i < totPos; i++) {
			for (int j = 0; j < nbColTypePavage + pieces.length; j++) {
				tabdef[i][j] = tab[i][j];
			}
		}
		return tabdef;
	}
}
