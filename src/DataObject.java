
public class DataObject extends LinkObject {
	public LinkObject L;
	public LinkObject R;
	public LinkObject U;
	public LinkObject D;
	public final ColumnObject C;//La colonne a laquelle appartient l'objet ne change pas
	
	public DataObject(LinkObject L, LinkObject U, ColumnObject C) {
		//on va lire le fichier de haut en bas de gauche a droite donc au moment de construire
		//le DataObject on aura que ces renseignements la
		this.L=L;
		this.R=null;
		this.U=U;
		this.D=null;
		this.C=C;
	}
}
