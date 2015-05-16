import java.io.BufferedReader;
import java.io.FileReader;


public class PavageFileParsing {

	public static LinkMatrix readPavage(String file_name) {
		try{
			 //Création du lecteur de fichier
	          FileReader inputFile = new FileReader("src/tests/pavage/"+file_name);

	          //Création du buffer de lecture à partir du FileReader
	          // Maintenant on peut faire .readLine() pour obtenir une ligne 
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          // String dans lequel on va stocker les lignes du fichier
	          String line;
	          
	          // On lit la première ligne : nombre de colonnes
	          line=bufferReader.readLine();
	          int nbCol=Integer.parseInt(line);
	          
	          //On lit le nombre de lignes
	          line=bufferReader.readLine();
	          int nbLignes=Integer.parseInt(line);
	          
	          // On remplit d'abord un tableau d'entier avec ce qui est lu dans le fichier
	          //Ce tableau sera la grille
	          int[][] gridd=new int[nbLignes][nbCol];
	          int i=0;
	          line=bufferReader.readLine();
	          while(i<nbLignes){
	        	  for(int j=0;j<nbCol;j++){
	        		  String c=Character.toString(line.charAt(j));
	        		  if(c.equals("*")){
	        			  gridd[i][j]=0;
	        		  }
	        		  else{
	        			  gridd[i][j]=1;
	        		  }
	        	  }
	        	  i++;
	        	  line=bufferReader.readLine();
	          }
	          
	          //On crée la grille
	          Grille grid=new Grille(nbCol, nbLignes, gridd);
	          
	          //Debug only : on affiche la grille
	          System.out.println(grid);

	          //Maintenant on lit le nombre de pièces
	          int nbPieces=Integer.parseInt(line);
	          System.out.println(nbPieces+" pieces");
	          
	          //TODO : créer un tableau contenant les x pièces, en les lisant
	          
	          //TODO : créer ensuite le tableau de 0 et de 1
	          //Pour chaque pièce, il faut d'abord obtenir la liste des pièces qu'elle génère, puis la faire bouger partout là ou c'est possible
	          //Pour ce faire, on va utiliser la méthode admissiblePositionForPiece(Piece p, int i,int j) de la classe Grille
	          
	          
	          //On finalise
	          bufferReader.close();
	          inputFile.close();
	          System.out.println("closing buffer, successful read");
	          //System.out.println("creating matrix from tab...");
	          
	          //On crée ensuite la matrice à partir du tableau
	          // et on renvoie le résultat
	          //return LinkMatrixCreation.createMatrixFromTab(tableau, nbColPrim, nbColSec, nbLignes);
	          
		}
		catch(Exception e){
			System.out.println("Erreur en ouvrant le fichier Pavage, ou autre");
			System.err.println(e);
		}
		
		return null;
	}
}
