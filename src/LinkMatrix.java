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
	
	public ArrayList<DataObject> DancingLinks(ArrayList<DataObject> solution, int k) {
		//ArrayList est une structure de donnees que l'on peut parcourir
		//avec un iterateur
		
		//Si root.R = root, la matrice est vide et c'est fini
		if (this.root.R == this.root) {
			//on affiche la solution ou quoi
			return solution;
		}
		
		//Ensuite on choisit de maniere deterministe une colonne.
		ColumnObject c = this.FindMinimalSizeColumn();
		
		//On couvre la colonne c
		this.CoverColumn(c);
		
		LinkObject currentColLink = c.D;
		while (currentColLink instanceof DataObject) {
			solution.set(k, (DataObject)currentColLink);//On ajoute l'element au tableau qui stocke la solution en cours
			
			DataObject currentRowLink = (DataObject)currentColLink.R;
			while (currentRowLink != currentColLink) {
				this.CoverColumn(currentRowLink.C);
				
				currentRowLink = (DataObject)currentRowLink.R;
			}
			
			DancingLinks(solution, k+1);
			
			currentColLink = solution.get(k);
			c = currentColLink.C;
			
			currentRowLink = (DataObject)currentColLink.L;
			while (currentRowLink != currentColLink) {
				this.UncoverColumn(currentRowLink.C);
				
				currentRowLink = (DataObject)currentRowLink.L;
			}
			
			currentColLink = currentColLink.D;
		}
		
		this.UncoverColumn(c);
		
		return null;
	}
	
}
