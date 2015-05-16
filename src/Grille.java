
public class Grille {
	// Cette classe représente le quadrillage à paver pour la partie pavage du projet
	// Ce peut être par exemple un équiquier, ou un carré évidé
	
	int nbCol;
	int nbLigne;
	int[][] grid;
	//Convention interne de représentation : 0 si la case est accessible, n'importe quoi d'autre si elle ne l'est pas (ie quadrillage évidé)
	
	public Grille(int nbCol, int nbLigne){//Constructeur ^pour un quadrillage entier
		this.nbCol=nbCol;
		this.nbLigne=nbLigne;
		this.grid=new int[nbLigne][nbCol];
	}
	
	public Grille(int nbCol,int nbLigne, int[][] g){//Constructeur pour un quadrillage éventuellement creux
		this.nbCol=nbCol;
		this.nbLigne=nbLigne;
		this.grid=new int[nbCol][nbLigne];
		for(int i=0;i<nbLigne;i++){
			for(int j=0;j<nbCol;j++){
				if(g[i][j]==0)
					this.grid[i][j]=0;
				else
					this.grid[i][j]=1;
			}
		}
	}
	
	@Override
	public String toString(){
		String s="";
		for(int i=0;i<nbLigne;i++){
			for(int j=0;j<nbCol;j++){
				if(grid[i][j]==0)
					s+="*";
				else
					s+=" ";
			}
			s+="\n";
		}
		return s;
	}
	
	public boolean admissiblePositionForPiece(Piece p, int i,int j){
		//Renvoie True ssi la piece p peut être posée à la position i, j pour son coin en haut à gauche dans la grille
		//Coin en haut a gauche : 0, 0
		//Renvoie False par exemple si la piece est trop grande, ou interfere avec les cases évidées
		//Sera utile pour générer l'ensemble des lignes de la matrice qu'on enverra ensuite à l'algo DancingLinks
		if(i>=this.nbLigne||j>=this.nbCol)
			return false;
		
		
		return true;
	}

}
