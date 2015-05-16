import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

//Suppose qu'il y a au moins 2 colonnes...

public class EMCFileParsing {

	// Classe consacrée à la lecture des fichiers passés en entrée.
	// readEMC prend en argument le nom du fichier qu'il doit lire, et renvoie un objet LinkMatrix qui représente les données du fichier.
	// Necessité de faire deux passages pour chainer dans les deux sens
	
	public static LinkMatrix readEMC(String file_name) {
		try{
			 //Création du lecteur de fichier
	          FileReader inputFile = new FileReader("src/tests/emc/"+file_name);

	          //Création du buffer de lecture à partir du FileReader
	          // Maintenant on peut faire .readLine() pour obtenir une ligne 
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          // String dans lequel on va stocker les lignes du fichier
	          String line;
	          
	          // On lit la première ligne : nombre de colonnes primaires
	          line=bufferReader.readLine();
	          int nbColPrim=Integer.parseInt(line);
	          
	          //On lit la deuxieme ligne : nombre de colonnes secondaires
	          line=bufferReader.readLine();
	          int nbColSec=Integer.parseInt(line);
	          
	          //On lit le nombre de ligne
	          line=bufferReader.readLine();
	          int nbLignes=Integer.parseInt(line);
	          
	          // On remplit d'abord un tableau d'entier avec ce qui est lu dans le fichier
	          int[][] tableau=new int[nbLignes][nbColPrim+nbColSec];
	          int i=0;
	          line=bufferReader.readLine();
	          while(i<nbLignes){
	        	  for(int j=0;j<nbColPrim+nbColSec;j++){
	        		  tableau[i][j]=Integer.parseInt(Character.toString(line.charAt(j)));
	        	  }
	        	  i++;
	        	  line=bufferReader.readLine();
	          }
	          

	          //Debugage : affichage du tableau
	          DebugUtils.affTab(tableau, nbLignes, nbColPrim+nbColSec);
	          
	          bufferReader.close();
	          inputFile.close();
	          System.out.println("closing buffer, successful read");
	          System.out.println("creating matrix from tab...");
	          
	          //On crée ensuite la matrice à partir du tableau
	          // et on renvoie le résultat
	          return LinkMatrixCreation.createMatrixFromTab(tableau, nbColPrim, nbColSec, nbLignes);
	          
		}
		catch(Exception e){
			System.out.println("Erreur en ouvrant le fichier EMC, ou autre");
			System.err.println(e);
		}
		
		return null;
	}
	
	
}
