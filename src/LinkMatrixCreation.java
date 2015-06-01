import java.util.LinkedList;

public class LinkMatrixCreation {

	// Classe statique d'utilisation commune pour les deux parties : EMC et
	// Pavage : � chaque fois on lit le fichier, puis on obtient un tableau
	// d'entier
	// Cette classe sert alors � cr�er la LinkMatrix � partir de ce tableau
	// d'entiers

	public static LinkMatrix createMatrixFromTab(int[][] tab, int nbColPrim,
			int nbColSec, int nbLignes) {
		// Cette m�thode va cr�er la matrice a partir d'un tableau de 0 et de 1,
		// et de quelques infos suppl�mentaires
		// On commence par cr�er le root
		RootObject root = new RootObject();

		int N = nbColPrim + nbColSec;

		// D'abord, cr�ation des ent�tes de colonnes
		createColumnObjects(root, nbColPrim);

		// Debug : on affiche un peu tout �a, de gauche � droite
		// DebugUtils.printColumnsOnly(root, nbColPrim);

		// A ce stade, les ent�tes des colonnes sont cr�es
		// On compte d'abord les occurences de 1 dans chaque colonne, puis on
		// met � jour le champ .S pour les colonnes primaires
		updateCount(root, nbColPrim, tab);

		//DebugUtils.printColumnsOnly(root, nbColPrim);

		// m�thode bien brutale : on cr�e d'abord un tableau d'objets DataTab,
		// ou de Null (suivant 0 ou 1 dans le tableau de base)
		DataObject[][] datatab = createDataTab(root, tab, nbColPrim,nbColSec);

		// Affichage - Debug only
		// DebugUtils.affDataTab(datatab, nbLignes, N);

		// Maintenant on linke horizontalement
		linkHoriz(datatab);

		// Et verticalement, c'est un peu plus d�licat car il faut attacher aux
		// ent�tes des colonnes
		// Il faut passer aussi le root en argument, pour pouvoir
		linkVert(datatab, root,nbColPrim,nbColSec);

		// Il ne reste plus qu'a renvoyer un objet linkmatrix qui encapsule tout
		// ca
		return new LinkMatrix(root, nbLignes, N);
	}

	private static void linkVert(DataObject[][] datatab, RootObject root,int nbColPrim, int nbColSec) {
		int N = datatab[0].length;
		int l = datatab.length;
		LinkObject colcourante = root.R;

		// Pour chaque colonne primaire...
		for (int ncol = 0; ncol < nbColPrim; ncol++) {
			LinkedList<Integer> listeindun = new LinkedList<>();
			for (int i = 0; i < l; i++) {
				if (datatab[i][ncol] != null)
					listeindun.addLast(i);
			}

			if (listeindun.isEmpty())
				continue;
			for (int i = 0; i < listeindun.size() - 1; i++) {
				datatab[listeindun.get(i)][ncol].D = datatab[listeindun
						.get(i + 1)][ncol];
				datatab[listeindun.get(i + 1)][ncol].U = datatab[listeindun
						.get(i)][ncol];
			}
			// System.out.println("collage principal done");

			// On finit le travail en collant avec le haut des colonnes
			datatab[listeindun.getFirst()][ncol].U = colcourante;
			datatab[listeindun.getLast()][ncol].D = colcourante;
			colcourante.D = datatab[listeindun.getFirst()][ncol];
			colcourante.U = datatab[listeindun.getLast()][ncol];

			// Use for debug : cylcing through a line
			// Prints the whole column, starting with the header, plus the
			// header once again at the end
			/*
			 * LinkObject c=colcourante; for(int j=0;j<listeindun.size()+2;j++){
			 * System.out.println(c+""+c.hashCode()); c=c.D; }
			 */

			// On n'oublie pas de mettre a jour la colonne courante, suppos�e
			// deja bien construite

			colcourante = colcourante.R;

		}
		
		//Maintenant les colonnes secondaire
		for (int ncol = nbColPrim; ncol < nbColPrim+nbColSec; ncol++) {
			LinkedList<Integer> listeindun = new LinkedList<>();
			for (int i = 0; i < l; i++) {
				if (datatab[i][ncol] != null)
					listeindun.addLast(i);
			}
			if (listeindun.isEmpty())
				continue;
			else//Sinon on trouve la colonne grace au champ C d'un DataObject
				colcourante = datatab[listeindun.getFirst()][ncol].C;
			
			for (int i = 0; i < listeindun.size() - 1; i++) {
				datatab[listeindun.get(i)][ncol].D = datatab[listeindun
						.get(i + 1)][ncol];
				datatab[listeindun.get(i + 1)][ncol].U = datatab[listeindun
						.get(i)][ncol];
			}
			// System.out.println("collage principal done");

			// On finit le travail en collant avec le haut des colonnes
			datatab[listeindun.getFirst()][ncol].U = colcourante;
			datatab[listeindun.getLast()][ncol].D = colcourante;
			colcourante.D = datatab[listeindun.getFirst()][ncol];
			colcourante.U = datatab[listeindun.getLast()][ncol];
		}

	}

	private static void linkHoriz(DataObject[][] datatab) {
		int N = datatab[0].length;
		int l = datatab.length;

		// Pour chaque ligne...
		for (int i = 0; i < l; i++) {
			LinkedList<Integer> listeindun = new LinkedList<>();
			for (int j = 0; j < N; j++) {
				if (datatab[i][j] != null)
					listeindun.addLast(j);
			}
			if (listeindun.isEmpty())
				continue;
			for (int j = 0; j < listeindun.size() - 1; j++) {
				datatab[i][listeindun.get(j)].R = datatab[i][listeindun
						.get(j + 1)];
				datatab[i][listeindun.get(j + 1)].L = datatab[i][listeindun
						.get(j)];
			}
			datatab[i][listeindun.getFirst()].L = datatab[i][listeindun
					.getLast()];
			datatab[i][listeindun.getLast()].R = datatab[i][listeindun
					.getFirst()];

			// Use for debug : cylcing through a line
			/*
			 * LinkObject c=datatab[i][listeindun.getFirst()]; for(int
			 * j=0;j<listeindun.size();j++){ System.out.println(c); c= c.R; }
			 */
		}

	}

	private static DataObject[][] createDataTab(RootObject root, int[][] tab,int nbColPrim, int nbColSec) {
		// Cette m�thode renvoie un tableau de DataObjects, non chain�s entre
		// eux, mais dont les colonnes ent�tes sont bien initialis�es
		int N = tab[0].length;
		int l = tab.length;
		DataObject[][] datatab = new DataObject[l][N];

		ColumnObject colcour = (ColumnObject) root.R;
		for (int ncol = 1; ncol < nbColPrim; ncol++) {

			for (int i = 0; i < l; i++) {
				if (tab[i][ncol - 1] == 1) {
					datatab[i][ncol - 1] = new DataObject(colcour);
				} else {
					datatab[i][ncol - 1] = null;
				}
			}

			colcour = (ColumnObject) colcour.R;
		}
		// Cas � part � la fin : la derni�re colonne primaire
		for (int i = 0; i < l; i++) {
			if (tab[i][nbColPrim - 1] == 1) {
				datatab[i][nbColPrim - 1] = new DataObject(colcour);
			} else {
				datatab[i][nbColPrim - 1] = null;
			}
		}
		
		//Pour les colonnes secondaires, on créé les en-têtes de colonne à ce moment
		//Puisqu'on les rattache aux éventuels DataObject
		for (int ncol = nbColPrim+1; ncol <= nbColPrim+nbColSec; ncol++) {
			ColumnObject colSecondaire = new ColumnObject(ncol);			
			for (int i = 0; i < l; i++) {
				if (tab[i][ncol - 1] == 1) {
					datatab[i][ncol - 1] = new DataObject(colSecondaire);
				} else {
					datatab[i][ncol - 1] = null;
				}
			}
		}

		return datatab;
	}

	private static int[] columnsCount(int[][] tab) {
		// méthode permettant de calculer le nombre d'occurences de 1 dans les
		// diff�rentes colonnes
		int N = tab[0].length;
		int[] counts = new int[N];
		for (int j = 0; j < N; j++) {
			int cpt = 0;
			for (int i = 0; i < tab.length; i++) {
				if (tab[i][j] == 1)
					cpt++;
			}
			counts[j] = cpt;
		}
		return counts;
	}

	private static void createColumnObjects(RootObject root, int nbColPrim) {
		// Méthode permettant de créer les entetes de colonnes primaires
		// On crée la premi�re colonne
		ColumnObject colcourante = new ColumnObject(1, root);
		root.R = colcourante;

		ColumnObject nouvellecol;
		// Ensuite les suivantes
		for (int i = 2; i <= nbColPrim; i++) {
			nouvellecol = new ColumnObject(i, colcourante);

			// On linke dans l'autre sens
			colcourante.R = nouvellecol;

			// On passe au suivant
			colcourante = nouvellecol;
		}
		// Reste des raccordements � faire
		// A ce moment, colcourante pointe sur le dernier maillon
		root.L = colcourante;
		colcourante.R = root;
	
	}

	private static void updateCount(RootObject root, int N, int[][] tab) {
		// méthode permettant de mettre � jour le champ S des diff�rentes
		// colonnes

		ColumnObject colcourante;
		int[] counts = columnsCount(tab);
		colcourante = (ColumnObject) root.R;
		colcourante.S = counts[0];
		colcourante = (ColumnObject) colcourante.R;
		for (int i = 2; i < N; i++) {
			colcourante.S = counts[i - 1];
			colcourante = (ColumnObject) colcourante.R;
		}
		colcourante.S = counts[N - 1];

	}
}
