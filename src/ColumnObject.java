
public class ColumnObject extends LinkObject {
	public LinkObject L;
	public LinkObject R;
	public LinkObject U;
	public LinkObject D;
	public final ColumnObject C;//La colonne a laquelle appartient l'objet ne change pas
	public int S;//nombre de maillons dans la colonne
	public int N;//numero qui identifie la colonne. La colonne de gauche est 1 (et le Root est 0 en fait)
	
	public ColumnObject(int N, LinkObject L) {
		//on construit les colonnes de la matrice de gauche a droite, en commencant par le RootObject
		//ca va donner une liste chainee que l'on chaine doublement en repassant une deuxieme fois
		this.L=L;
		this.R=null;
		this.U=null;
		this.D=null;
		this.C=this;
		this.S=0;
		this.N=N;
	}
	
	@Override
	public String toString(){
		String s="entete colonne num : "+this.N+" nb maillons : "+this.S;
		
		if(this.L instanceof RootObject)
			s+=" L : root";
		else
			s+=" L : "+((ColumnObject)(this.L)).N;
		if(this.R instanceof RootObject)
			s+=" R : root";
		else
			s+=" R : "+((ColumnObject)this.R).N;
		
		return s;

		
	}
}
