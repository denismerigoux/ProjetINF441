import java.util.ArrayList;

public class LinkMatrix {
	public RootObject root;
	
	
	public LinkMatrix(RootObject root) {
		this.root = root;
	}
	
	public ColumnObject FindMinimalSizeColumn() {
		ColumnObject MinColumn = null;
		int min = Integer.MAX_VALUE;
		LinkObject currentLink = this.root.R;
		while (currentLink instanceof ColumnObject) {
			//c'est un peu complique parce que on ne peut pas prendre le champ S d'un LinkObject
			//le test du while sert a verifier si on est pas retombe sur le root, parce que sinon
			//c'est bien un ColumnObject.
			if (((ColumnObject)currentLink).S < min) {
				min = ((ColumnObject)currentLink).S;
				MinColumn = (ColumnObject)currentLink;
			}
			currentLink = currentLink.R;
		}
		return MinColumn;
	}
	
	public void CoverColumn(ColumnObject c) {
		c.R.L = c.L;//on enleve c de la liste des colonnes
		c.L.R = c.R;
		
		LinkObject currentColLink = c.D;
		while (currentColLink instanceof DataObject) {
			DataObject currentRowLink = (DataObject)currentColLink.R;
			while (currentRowLink != currentColLink) {
				currentRowLink.D.U = currentRowLink.U;//on enleve currenRowLink de sa colonne
				currentRowLink.U.D = currentRowLink.D;
				
				currentRowLink.C.S--;//et on met a jour la taille de la colonne
				
				currentRowLink = (DataObject)currentRowLink.R;
			}
			currentColLink = currentColLink.D;
		}
	}
	
	public void UncoverColumn(ColumnObject c) {		
		LinkObject currentColLink = c.U;
		while (currentColLink instanceof DataObject) {
			DataObject currentRowLink = (DataObject)currentColLink.L;
			while (currentRowLink != currentColLink) {
				currentRowLink.C.S++;//on met a jour la taille de la colonne
				
				currentRowLink.D.U = currentRowLink;//on remet currenRowLink dans sa colonne
				currentRowLink.U.D = currentRowLink;
				
				currentRowLink = (DataObject)currentRowLink.L;
			}
			currentColLink = currentColLink.U;
		}
		c.R.L = c;//on rajoute c a la liste des colonnes
		c.L.R = c;
	}
	
	public ArrayList<ArrayList<Integer>> DancingLinks(ArrayList<DataObject> currentSolution, int k, ArrayList<ArrayList<Integer>> solutions) {
		//La fonction est recursive, il faut d'abord l'appeler avec k=0 et des tableaux vides pour solutions et currentSolution
		//Elle outpute une liste iterative contenant des listes iteratives qui sont les numeros des colonnes retenues pour chacune des solutions
		
		//Si root.R = root, la matrice est vide et on ajoute la solution en cours au tableau des solutions
		if (this.root.R == this.root) {
			//Mais avant on transforme les DataObjects en le numero de leur colonne pour stocker ca plus facilement.
			ArrayList<Integer> printedCurrentSolution = new ArrayList<Integer>();
			for (DataObject o : currentSolution) {
				printedCurrentSolution.add(o.C.N);
			}
			solutions.add(printedCurrentSolution);
			return solutions;
		}
		
		//Ensuite on choisit de maniere deterministe une colonne.
		ColumnObject c = this.FindMinimalSizeColumn();
		
		//On couvre la colonne c
		this.CoverColumn(c);
		
		LinkObject currentColLink = c.D;
		while (currentColLink instanceof DataObject) {
			
			if (currentSolution.size() > k) {
				currentSolution.set(k, (DataObject)currentColLink);//On ajoute l'element au tableau qui stocke la solution en cours
			} else {
				currentSolution.add((DataObject)currentColLink);
			}
			
			DataObject currentRowLink = (DataObject)currentColLink.R;
			while (currentRowLink != currentColLink) {
				this.CoverColumn(currentRowLink.C);
				
				currentRowLink = (DataObject)currentRowLink.R;
			}
			
			DancingLinks(currentSolution, k+1, solutions);
			
			currentColLink = currentSolution.get(k);
			c = currentColLink.C;
			
			currentRowLink = (DataObject)currentColLink.L;
			while (currentRowLink != currentColLink) {
				this.UncoverColumn(currentRowLink.C);
				
				currentRowLink = (DataObject)currentRowLink.L;
			}
			
			currentColLink = currentColLink.D;
		}
		
		this.UncoverColumn(c);
		
		return solutions;
	}
	
	public ArrayList<ArrayList<Integer>> DancingLinks() {//La fonction globale est surchargee, on peut l'appeler sans arguments
		return DancingLinks(new ArrayList<DataObject>(),0,new ArrayList<ArrayList<Integer>>());
	}
	
}
