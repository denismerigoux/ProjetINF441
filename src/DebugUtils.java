import java.util.Arrays;
import java.util.Vector;

public class DebugUtils {

	//Classe consacrée à des méthodes statiques permettant d'afficher des bouts de la matrice pour débuguer lors de sa création
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

	public static void affTab(int[][] tableau) {
		int nbLignes=tableau.length;
		int nbCol=tableau[0].length;
		for(int i=0;i<nbLignes;i++){
	      	  for(int j=0;j<nbCol;j++){
	      		  System.out.print(tableau[i][j]);
	      	  }
	      	  System.out.println();
	        }
	}
	
	public static boolean testSolution(Vector<DataObject> solution, int nbColPrim) {
		int[] nbDataObject = new int[nbColPrim];
		Arrays.fill(nbDataObject,0);
		for (DataObject O : solution) {
			DataObject currentLink = O;
			if (currentLink.C.N<= nbColPrim) {
				nbDataObject[currentLink.C.N - 1]++;
			}

			while (currentLink.R != O) {
				// Attention, les colonnes sont numÃ©rotÃ©es de 1 Ã  columnNumber
				// et pas Ã  partir de 0
				if (currentLink.R.C.N<= nbColPrim) {
					nbDataObject[currentLink.R.C.N - 1]++;
				}
				currentLink = (DataObject) currentLink.R;
			}
		}
		boolean result = true;
		for (int i =0; i<nbColPrim;i++) {
			if (nbDataObject[i] != 1) {
				result = false;
			}
		}
		
		return result;	
			
	}
	
}
