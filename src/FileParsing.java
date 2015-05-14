import java.io.BufferedReader;
import java.io.FileReader;

//Suppose qu'il y a au moins 2 colonnes...

public class FileParsing {

	// Classe consacr�e � la lecture des fichiers pass�s en entr�e.
	// readEMC prend en argument le nom du fichier qu'il doit lire, et renvoie un objet LinkMatrix qui repr�sente les donn�es du fichier.
	// Necessit� de faire deux passages pour chainer dans les deux sens
	
	public static LinkMatrix readEMC(String file_name){
		try{
			 //Cr�ation du lecteur de fichier
	          FileReader inputFile = new FileReader("src/tests/emc/"+file_name);

	          //Cr�ation du buffer de lecture � partir du FileReader
	          // Maintenant on peut faire .readLine() pour obtenir une ligne 
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          // String dans lequel on va stocker les lignes du fichier
	          String line;
	          
	          // On lit la premi�re ligne : nombre de colonnes primaires
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
	          
	          //On cr�e ensuite la matrice � partir du tableau
	          // et on renvoie le r�sultat
	          return createMatrixFromTab(tableau, nbColPrim, nbColSec, nbLignes);
	          
		}
		catch(Exception e){
			System.out.println("Erreur en ouvrant le fichier EMC, ou autre");
			System.err.println(e);
		}
		
		return null;
	}
	
	public static LinkMatrix createMatrixFromTab(int[][] tab,int nbColPrim,int nbColSec,int nbLignes){
		
		//On commence par cr�er le root
		RootObject root=new RootObject();
		
		
		
		int N=nbColPrim+nbColSec;
		
		//D'abord, cr�ation des ent�tes de colonnes
		createColumnObjects(root, N);
		
		//Debug : on affiche un peu tout �a, de gauche � droite
		//DebugUtils.printColumnsOnly(root, N);
		
		//A ce stade, les ent�tes des colonnes sont cr�es
		// On compte d'abord les occurences de 1 dans chaque colonne, puis on met � jour le champ .S
		updateCount(root, N, tab);
		
		DebugUtils.printColumnsOnly(root, N);
		
		//m�thode bien brutale : on cr�e d'abord un tableau d'objets DataTab, ou de Null (suivant 0 ou 1 dans le tableau de base)
		DataObject[][] datatab=createDataTab(root, tab);
		DebugUtils.affDataTab(datatab, nbLignes, N);
		
		//Maintenant on linke horizontalement
		linkHoriz(datatab);
		
		
		return new LinkMatrix(root);
	}

	private static void linkHoriz(DataObject[][] datatab) {
		int N=datatab[0].length;
		int l=datatab.length;
		
		//Pour chaque ligne...
		for(int i=0;i<l;i++){
			int courant=0;
			int first=0;
			while(datatab[i][courant]==null){
				courant++;
				if(courant>=N){
					System.out.println("empty line, is it an error ?");
					break;
				}
			}
			if(courant>=N)
				continue;
			first=courant;
			courant++;
			while(courant!=first){
				courant=(courant+1)%N;
				//TODO : finir cette m�thode... et faire la m�me pour la version linkage vertical
			}
			
			
			
		}
		
	}

	private static DataObject[][] createDataTab(RootObject root, int[][] tab) {
		//Cette m�thode renvoie un tableau de DataObjects, non chain�s entre eux, mais dont les colonnes ent�tes sont bien initialis�es
		int N=tab[0].length;
		int l=tab.length;
		DataObject[][] datatab=new DataObject[l][N];
		
		ColumnObject colcour=(ColumnObject) root.R;
		for(int ncol=1;ncol<N;ncol++){
			
			for(int i=0;i<l;i++){
				if(tab[i][ncol-1]==1){
					datatab[i][ncol-1]=new DataObject(colcour);
				}
				else{
					datatab[i][ncol-1]=null;
				}
			}
			
			colcour=(ColumnObject) colcour.R;
		}
		//Cas � part � la fin : la derni�re colonne
		for(int i=0;i<l;i++){
			if(tab[i][N-1]==1){
				datatab[i][N-1]=new DataObject(colcour);
			}
			else{
				datatab[i][N-1]=null;
			}
		}
		
		return datatab;
	}

	private static int[] columnsCount(int[][] tab) {
		//m�thode permettant de calculer le nombre d'occurences de 1 dans les diff�rentes colonnes
		int N=tab[0].length;
		int [] counts=new int[N];
		for(int j=0;j<N;j++){
			int cpt=0;
			for(int i=0;i<tab.length;i++){
				if(tab[i][j]==1)
					cpt++;
			}
			counts[j]=cpt;
		}
		return counts;
	}
	
	private static void createColumnObjects(RootObject root,int N){ 
		// M�thode permettant de cr�er les entetes de colonnes
		//On cr�e la premi�re colonne
				ColumnObject colcourante=new ColumnObject(1, root);
				root.R=colcourante;

				ColumnObject nouvellecol;
				//Ensuite les suivantes
				for(int i=2;i<=N;i++){
					nouvellecol=new ColumnObject(i,colcourante);

					
					//On linke dans l'autre sens
					colcourante.R=nouvellecol;

					//On passe au suivant
					colcourante=nouvellecol;
				}
				//Reste des raccordements � faire
				// A ce moment, colcourante pointe sur le dernier maillon
				root.L=colcourante;
				colcourante.R=root;
	}
	
	private static void updateCount(RootObject root,int N, int[][] tab){
		//m�thode permettant de mettre � jour le champ S des diff�rentes colonnes
		
		ColumnObject colcourante;
		int[] counts=columnsCount(tab);
		colcourante=(ColumnObject) root.R;
		colcourante.S=counts[0];
		colcourante=(ColumnObject) colcourante.R;
		for(int i=2;i<N;i++){
			colcourante.S=counts[i-1];
			colcourante=(ColumnObject) colcourante.R;
		}
		colcourante.S=counts[N-1];
		
	}
}
