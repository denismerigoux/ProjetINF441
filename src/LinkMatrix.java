import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
import java.util.Vector;
import java.util.Arrays;

public class LinkMatrix {
	public RootObject root;
	public int rowNumber;// nombre de lignes
	public int columnNumber;// nombre de colonnes;

	public LinkMatrix(RootObject root, int rowNumber, int columnNumber) {
		this.root = root;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
	}

	private ColumnObject FindMinimalSizeColumn() {
		ColumnObject MinColumn = null;
		int min = Integer.MAX_VALUE;
		LinkObject currentLink = this.root.R;
		while (currentLink instanceof ColumnObject) {
			// c'est un peu complique parce que on ne peut pas prendre le champ
			// S d'un LinkObject
			// le test du while sert a verifier si on est pas retombe sur le
			// root, parce que sinon
			// c'est bien un ColumnObject.
			if (((ColumnObject) currentLink).S < min) {
				min = ((ColumnObject) currentLink).S;
				MinColumn = (ColumnObject) currentLink;
			}
			currentLink = currentLink.R;
		}
		return MinColumn;
	}

	private void CoverColumn(ColumnObject c) {
		c.R.L = c.L;// on enleve c de la liste des colonnes
		c.L.R = c.R;

		LinkObject currentColLink = c.D;
		while (currentColLink instanceof DataObject) {
			DataObject currentRowLink = (DataObject) currentColLink.R;
			while (currentRowLink != currentColLink) {
				currentRowLink.D.U = currentRowLink.U;// on enleve currenRowLink
														// de sa colonne
				currentRowLink.U.D = currentRowLink.D;

				currentRowLink.C.S--;// et on met a jour la taille de la colonne

				currentRowLink = (DataObject) currentRowLink.R;
			}
			currentColLink = currentColLink.D;
		}
	}

	private void UncoverColumn(ColumnObject c) {
		LinkObject currentColLink = c.U;
		while (currentColLink instanceof DataObject) {
			DataObject currentRowLink = (DataObject) currentColLink.L;
			while (currentRowLink != currentColLink) {
				currentRowLink.C.S++;// on met a jour la taille de la colonne

				currentRowLink.D.U = currentRowLink;// on remet currenRowLink
													// dans sa colonne
				currentRowLink.U.D = currentRowLink;

				currentRowLink = (DataObject) currentRowLink.L;
			}
			currentColLink = currentColLink.U;
		}
		c.R.L = c;// on rajoute c a la liste des colonnes
		c.L.R = c;
	}

	private ArrayList<DataObject[]> DancingLinks(
			Stack<DataObject> currentSolution, int k,
			ArrayList<DataObject[]> solutions) {
		// La fonction est recursive, il faut d'abord l'appeler avec k=0 et des
		// tableaux vides pour solutions et currentSolution
		// Elle outpute une liste iterative contenant des listes iteratives qui
		// sont les numeros des colonnes retenues pour chacune des solutions

		// Si root.R = root, la matrice est vide et on ajoute la solution en
		// cours au tableau des solutions
		if (this.root.R == this.root) {
			// Mais avant on transforme les DataObjects en le numero de leur
			// colonne pour stocker ca plus facilement.
			DataObject[] addedSolution = new DataObject[currentSolution.size()];
			solutions.add(currentSolution.toArray(addedSolution));
			return solutions;
		}

		// Ensuite on choisit de maniere deterministe une colonne.
		ColumnObject c = this.FindMinimalSizeColumn();

		// On couvre la colonne c
		this.CoverColumn(c);
		
		LinkObject currentColLink = c.D;
		while (currentColLink instanceof DataObject) {
			
			currentSolution.push((DataObject) currentColLink);
				

			DataObject currentRowLink = (DataObject) currentColLink.R;
			while (currentRowLink != currentColLink) {
				this.CoverColumn(currentRowLink.C);

				currentRowLink = (DataObject) currentRowLink.R;
			}

			this.DancingLinks(currentSolution, k + 1, solutions);

			currentColLink = currentSolution.pop();
			c = currentColLink.C;

			currentRowLink = (DataObject) currentColLink.L;
			while (currentRowLink != currentColLink) {
				this.UncoverColumn(currentRowLink.C);

				currentRowLink = (DataObject) currentRowLink.L;
			}

			currentColLink = currentColLink.D;
		}

		this.UncoverColumn(c);

		return solutions;
	}

	public ArrayList<DataObject[]> DancingLinks() {
		// La fonction globale est surchargee, on peut l'appeler sans arguments
		return DancingLinks(new Stack<DataObject>(), 0,
				new ArrayList<DataObject[]>());
	}

	public void PrintSolution(DataObject[] solution) {
		// la fonction prend en argument la largeur de la matrice
		for (DataObject O : solution) {
			// chaque O est sur une ligne différente de la solution
			// On créé un tableau qui représente la ligne
			boolean[] currentLine = new boolean[this.columnNumber];
			// true si 1, false si 0
			Arrays.fill(currentLine, false);
			// Attention, les colonnes sont numérotées de 1 à columnNumber et
			// pas à partir de 0
			currentLine[O.C.N - 1] = true;
			DataObject currentLink = O;

			while (currentLink.R != O) {
				// Attention, les colonnes sont numérotées de 1 à columnNumber
				// et pas à partir de 0
				currentLine[currentLink.R.C.N - 1] = true;
				currentLink = (DataObject) currentLink.R;
			}
			for (int i = 0; i < this.columnNumber; i++) {
				if (currentLine[i]) {
					System.out.print("1");
				} else {
					System.out.print("0");
				}
			}
			System.out.println();
		}
	}

	public void PrintSolutions(ArrayList<DataObject[]> solutions) {
		// affiche toutes les solutions
		for (int i = 0; i < solutions.size(); i++) {
			System.out.println(">>Solution n°" + i + " :");
			this.PrintSolution(solutions.get(i));
		}
		System.out.println("Le problème admet " + solutions.size()
				+ " solutions.");
	}

	public void DancingLinksSolution() {
		System.out.println("Exécution de l'algorithme DLX.");
		PrintSolutions(this.DancingLinks());
	}
	

	public void DancingLinksSolution(int i) {
		System.out.println("Exécution de l'algorithme DLX.");
		ArrayList<DataObject[]> solutions = this.DancingLinks();
		if (i >= solutions.size()) {
			System.out.println("La solution n°" + i
					+ " n'existe pas, il n'y a que " + solutions.size()
					+ " solutions.");
		} else {
			System.out.println("Affichage de la solution n°" + i +"/"+solutions.size()+ " :");
			PrintSolution(solutions.get(i));
		}
		return;
	}
	
	public void DancingLinksNumberOfSolutions() {
		System.out.println("Exécution de l'algorithme DLX.");
		ArrayList<DataObject[]> solutions = this.DancingLinks();
		System.out.println("Le problème admet "+solutions.size()+" solutions.");
	}

}
