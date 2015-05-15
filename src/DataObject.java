
public class DataObject extends LinkObject {
	
	public DataObject(LinkObject L, LinkObject U, ColumnObject C) {
		//on va lire le fichier de haut en bas de gauche a droite donc au moment de construire
		//le DataObject on aura que ces renseignements la
		this.L=L;
		this.R=null;
		this.U=U;
		this.D=null;
		this.C=C;
	}
	
	//Autre constructeur, o� l'on ne sp�cifie que la colonne
	public DataObject(ColumnObject C){
		this.L=null;
		this.R=null;
		this.U=null;
		this.D=null;
		this.C=C;
	}
	
	@Override
	public String toString(){
		String s="";
		s+="col"+C.N;
		return s;
	}
}
