import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

//Suppose qu'il y a au moins 2 colonnes...

public class EMCParsing {

	// Classe consacr�e � la lecture des fichiers pass�s en entr�e.
	// readEMC prend en argument le nom du fichier qu'il doit lire, et renvoie
	// un objet LinkMatrix qui repr�sente les donn�es du fichier.
	// Necessit� de faire deux passages pour chainer dans les deux sens

	public static LinkMatrix readEMCFromFile(String file_name) {
		try {
			// Cr�ation du lecteur de fichier
			FileReader inputFile = new FileReader("src/tests/emc/" + file_name);

			// Cr�ation du buffer de lecture � partir du FileReader
			// Maintenant on peut faire .readLine() pour obtenir une ligne
			BufferedReader bufferReader = new BufferedReader(inputFile);
			LinkMatrix result = readEMC(bufferReader);
			inputFile.close();
			return result;
		} catch (Exception e) {
			System.out.println("Erreur : fichier introuvable ou autre.");
		}
		return null;
	}

	public static LinkMatrix readEMC(BufferedReader bufferReader) {
		try {
			// String dans lequel on va stocker les lignes du fichier
			String line;

			// On lit la premi�re ligne : nombre de colonnes primaires
			line = bufferReader.readLine();
			int nbColPrim = Integer.parseInt(line);

			// On lit la deuxieme ligne : nombre de colonnes secondaires
			line = bufferReader.readLine();
			int nbColSec = Integer.parseInt(line);

			// On lit le nombre de ligne
			line = bufferReader.readLine();
			int nbLignes = Integer.parseInt(line);

			// On remplit d'abord un tableau d'entier avec ce qui est lu dans le
			// fichier
			int[][] tableau = new int[nbLignes][nbColPrim + nbColSec];
			int i = 0;
			line = bufferReader.readLine();
			while (i < nbLignes) {
				for (int j = 0; j < nbColPrim + nbColSec; j++) {
					tableau[i][j] = Integer.parseInt(Character.toString(line
							.charAt(j)));
				}
				i++;
				line = bufferReader.readLine();
			}

			// Debugage : affichage du tableau
			//System.out.println("Affichage de la matrice initiale :");
			//DebugUtils.affTab(tableau, nbLignes, nbColPrim + nbColSec);

			// System.out.println("closing buffer, successful read");
			System.out
					.println("Conversion de la matrice en objet utilisable par l'algorithme.");

			// On cr�e ensuite la matrice � partir du tableau
			// et on renvoie le r�sultat
			return LinkMatrixCreation.createMatrixFromTab(tableau, nbColPrim,
					nbColSec, nbLignes);

		} catch (Exception e) {
			System.out.println("Erreur en ouvrant le fichier EMC, ou autre");
			System.err.println(e);
		}

		return null;
	}

}
