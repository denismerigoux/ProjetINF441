import java.util.ArrayList;

public class LinkMatrix {
	public RootObject root;
	
	
	public LinkMatrix(RootObject root) {
		this.root = root;
	}
	
	public ColumnObject FindMinimalSizeColumn() {
		ColumnObject MinColumn = null;
		int min = Integer.MAX_VALUE;
		LinkObject CurrentLink = this.root.R;
		while (CurrentLink instanceof ColumnObject) {
			//c'est un peu complique parce que on ne peut pas prendre le champ S d'un LinkObject
			//le test du while sert a verifier si on est pas retombe sur le root, parce que sinon
			//c'est bien un ColumnObject.
			if (((ColumnObject)CurrentLink).S < min) {
				min = ((ColumnObject)CurrentLink).S;
				MinColumn = (ColumnObject)CurrentLink;
			}
			CurrentLink = CurrentLink.R;
		}
		return MinColumn;
	}
	
	public ArrayList<LinkMatrix> DancingLinks() {
		//ArrayList est une structure de donnees que l'on peut parcourir
		//avec un iterateur
		return null;
	}
	
}
