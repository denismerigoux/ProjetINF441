
public class DebugUtils {

	//Classe consacr�e � des m�thodes statiques permettant d'afficher des bouts de la matrice pour d�buguer lors de sa cr�ation
	public static void printColumnsOnly(RootObject root, int N){
		System.out.println(root);
		LinkObject colcour=root.R;
		
		for(int i=1;i<N;i++){
			System.out.println(colcour);
			colcour=colcour.R;
		}
		System.out.println(colcour);
	}
	
	public static void affTab(int[][] tableau, int nbLignes,int nbCol){
		for(int i=0;i<nbLignes;i++){
      	  for(int j=0;j<nbCol;j++){
      		  System.out.print(tableau[i][j]);
      	  }
      	  System.out.println();
        }
	}
	
	public static void affDataTab(DataObject[][] tableau, int nbLignes,int nbCol){
		for(int i=0;i<nbLignes;i++){
      	  for(int j=0;j<nbCol;j++){
      		  System.out.print(tableau[i][j]);
      	  }
      	  System.out.println();
        }
	}
	
	
}
