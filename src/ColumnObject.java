
public class ColumnObject extends LinkObject {
	public LinkObject L;
	public LinkObject R;
	public LinkObject U;
	public LinkObject D;
	public final ColumnObject C;//La colonne a laquelle appartient l'objet ne change pas
	public int S;//nombre de maillons dans la colonne
	public int N;//numero qui identifie la colonne
	
	public ColumnObject(int N, LinkObject L) {
		//on construit les colonnes de la matrice de gauche a droite, en commencant par le RootObject
		//�a va donner une liste chainee que l'on chaine doublement en repassant une deuxieme fois
		this.L=null;
		this.R=null;
		this.U=null;
		this.D=null;
		this.C=this;
		this.S=0;
		this.N=N;
	}
}
