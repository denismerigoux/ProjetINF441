import java.util.LinkedList;


public class Grille {
	// Cette classe représente le quadrillage à paver pour la partie pavage du projet
	// Ce peut être par exemple un équiquier, ou un carré évidé
	
	int nbCol;
	int nbLigne;
	private final int[][] grid;
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
		//Il y a probleme si : la piece existe dans une case ou la grille n'existe pas ; attention aux débordements
		if(i>=this.nbLigne||j>=this.nbCol)
			return false;
		for(int xp=0;xp<p.taille;xp++){
			for(int yp=0;yp<p.taille;yp++){
				if(p.motif[xp][yp]==1){
					if(i+xp>=this.nbLigne)
						return false;
					if(j+yp>=this.nbCol)
						return false;
					if(this.grid[i+xp][j+yp]!=0)
						return false;
				}
					
			}
		}

		return true;
	}

	public int numberOfValidCases(){
		//Cette méthode renvoie le nombre de cases effectives de la grille (ie là ou il y a des 0
		//Cela représentera aussi le nombre (enfin une partie) de colonnes de la matrice qu'on va créer
		int cpt=0;
		for(int i=0;i<nbLigne;i++){
			for(int j=0;j<nbCol;j++){
				if(this.grid[i][j]==0)
					cpt++;

			}
		}
		return cpt;
	}

	public LinkedList<Integer> getCoveredPositions(Piece p, int i, int j) {
		//Cette méthode renvoie la liste des positions occupées par une certaine pièce, mais sous forme de coordonnée unique, déjà convertie !
		if(!this.admissiblePositionForPiece(p, i, j)){
			System.out.println("error error error");
			return null;
		}
		LinkedList<Integer> cov=new LinkedList<>();
		for(int xp=0;xp<p.taille;xp++){
			for(int yp=0;yp<p.taille;yp++){
				if(p.motif[xp][yp]==1){
					cov.add(this.convertToOneCoord(i+xp,j+yp));
				}
					
			}
		}
		return cov;
	}
	
	public int convertToOneCoord(int i, int j){
		int l=0;
		//Méthode bourrine : on compte le nombre de cases valides avant, parcours en ligne de gauche a droite de haut en bas
		//TODO : a améliorer
		//Ce n'est plus bugé, mais ca recalcule beaucoup trop souvent
		for(int x=0;x<=i;x++){
			for(int y=0;y<this.nbCol;y++){
				if(this.grid[x][y]==0)
					l++;
				if(i==x&&y==j)
					break;
				
			}
		}
		l--;
		//For debug
		//System.out.println("("+i+","+j+") -> "+l);
		return l;
	}
}
