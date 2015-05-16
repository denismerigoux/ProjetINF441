import java.util.LinkedList;


public class Piece {
	//Classe repr�sentant une pi�ce 
	// Taille repr�sente le cot� du plus petit carr� permettant d'inscrire la pi�ce dedans. C'est mieux de faire comme �a 
	//QU'avec des rectangles, car au moins quand on fait tourner la pi�ce, la taille ne change pas
	// CONVENTION et INVARIANT : dans le carr�, la pi�ce est le plus en haut et le plus � gauche possible
	/*Exemple :
	  Si la pi�ce a la base est :
	 *
	 *	taille = 4
	 *
	 *
	 on la tourne :
	 * * * *		taille = 4 inchang�e
	 et seule la premi�re ligne du tableau carr� sera remplie
	 
	 */
	// La case 0,0 du tableau motif repr�sente la case en haut et a gauche du motif
	// 0 si rien, 1 si la pi�ce est pr�sente dans la case
	
	int taille;
	int[][] motif;
	
	public Piece(int taille, int[][] motif){ //Constructeur supposant le tableau d�j� carr�
		this.taille=taille;
		this.motif=motif;
	}
	
	public Piece(int[][] t){ //Constructeur selon un tableau �ventuellement rectangulaire
		this.taille=Math.max(t.length, t[0].length);
		this.motif=new int[this.taille][this.taille];
		for(int i=0;i<t.length;i++){
			for(int j=0;j<t[0].length;j++){
				motif[i][j]=t[i][j];
			}
		}
		
		
	}
	
	@Override
	public String toString(){
		String s="";
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				if(motif[i][j]==1)
					s+="*";
				else
					s+=" ";
			}
			s+="\n";
		}
		
		return s;
	}
	
	@Override
	public boolean equals(Object q){
		//Deux pieces sont �gales ssi leur taille et motif est identique, utile pour chercher la liste des rotations diff�rentes d'une m�me pi�ce
		if(this.taille!=((Piece)q).taille)
			return false;
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				if(motif[i][j]!=((Piece)q).motif[i][j])
					return false;
			}
		}
		return true;
	}
	
	private void reframe(){ //Cette m�thode, � invoquer apr�s une rotation ou retournement, permet de respecter notre invariant en reframant le motif
		while(this.hasEmptyFirstCol()){
			this.horizShift();
		}
		while(this.hasEmptyFirstRow()){
			this.vertShift();
		}
	}
	


	private boolean hasEmptyFirstRow() {//Renvoie True si le motif ne respecte pas l'invariant, premi�re ligne vide
		for(int j=0;j<taille;j++){
			if(motif[0][j]==1)
				return false;
		}
		return true;
	}

	private boolean hasEmptyFirstCol(){//Renvoie True si le motif n'est pas bien align� (premi�re colonne vide)
		for(int i=0;i<taille;i++){
			if(motif[i][0]==1)
				return false;
		}
		return true;
	}
	
	private void horizShift(){ //D�cale vers la gauche d'une colonne tout le motif, a utiliser si la premiere colonne est vide
		int[][] newmotif=new int[taille][taille];
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille-1;j++){
				newmotif[i][j]=motif[i][j+1];
					
			}
		}
		this.motif=newmotif;
	}
	
	private void vertShift() {//Idem si la premiere ligne est vide, remonte le motif
		int[][] newmotif=new int[taille][taille];
		for(int i=0;i<taille-1;i++){
			for(int j=0;j<taille;j++){
				newmotif[i][j]=motif[i+1][j];
					
			}
		}
		this.motif=newmotif;
	}
	
	public Piece mirrorH(){
		//Renvoie la m�me pi�ce, mais retourn�e via une sym�trie axialle, d'axe vertical, � droite ou a gauche de la piece peu importe
		int[][] newmotif=new int [taille][taille];
		
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				newmotif[i][j]=motif[i][taille-j-1];
					
			}
		}
		Piece m=new Piece(taille, newmotif);
		m.reframe();
		return m;
		
	}
	
	public Piece mirrorV(){
		//Renvoie la m�me pi�ce, mais retourn�e via une sym�trie axialle, d'axe horizontal
		int[][] newmotif=new int [taille][taille];
		
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				newmotif[i][j]=motif[taille-i-1][j];
					
			}
		}
		Piece m=new Piece(taille, newmotif);
		m.reframe();
		return m;
		
	}
	
	public Piece rotation(){
		//Renvoie la m�me pi�ce, mais apr�s une rotation de 90 degr�s vers la droite... ou la gauche ? Sens horaire
		int[][] newmotif=new int [taille][taille];
		
		for(int i=0;i<taille;i++){
			for(int j=0;j<taille;j++){
				newmotif[i][j]=motif[taille-j-1][i];
					
			}
		}
		Piece m=new Piece(taille, newmotif);
		m.reframe();
		return m;
	}
	
	public int numberOfDifferentTransformations(){//Renvoie le nombre de pi�ces diff�rentes obtenues via des rotations et retournements de la pi�ce de base
		
		return this.getListOfTransformations().size();
	}
	
	public LinkedList<Piece> getListOfTransformations(){ //Renvoie une liste contenant toutes les pieces obtenues par transfotrmation a partir de la piece de base
		LinkedList<Piece> listetransfo=new LinkedList<>();
		listetransfo.add(this);
		
		Piece tr=this.rotation();
		
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=tr.rotation();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=tr.rotation();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=this.mirrorH();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=tr.rotation();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=this.mirrorV();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		tr=tr.rotation();
		if(!listetransfo.contains(tr)){
			listetransfo.addLast(tr);
		}
		return listetransfo;
	}
}
