import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

//Suppose qu'il y a au moins 2 colonnes...

public class FileParsing {

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
	          return createMatrixFromTab(tableau, nbColPrim, nbColSec, nbLignes);
	          
		}
		catch(Exception e){
			System.out.println("Erreur en ouvrant le fichier EMC, ou autre");
			System.err.println(e);
		}
		
		return null;
	}
	
	public static LinkMatrix createMatrixFromTab(int[][] tab,int nbColPrim,int nbColSec,int nbLignes){
		// Cette méthode va créer la matrice a partir d'un tableau de 0 et de 1, et de quelques infos supplémentaires
		//On commence par créer le root
		RootObject root=new RootObject();
		
		
		
		int N=nbColPrim+nbColSec;
		
		//D'abord, création des entêtes de colonnes
		//TODO : faire la différence entre colonne primaires et secondaires
		createColumnObjects(root, N);
		
		//Debug : on affiche un peu tout ça, de gauche à droite
		//DebugUtils.printColumnsOnly(root, N);
		
		//A ce stade, les entêtes des colonnes sont crées
		// On compte d'abord les occurences de 1 dans chaque colonne, puis on met à jour le champ .S
		updateCount(root, N, tab);
		
		DebugUtils.printColumnsOnly(root, N);
		
		//méthode bien brutale : on crée d'abord un tableau d'objets DataTab, ou de Null (suivant 0 ou 1 dans le tableau de base)
		DataObject[][] datatab=createDataTab(root, tab);
		DebugUtils.affDataTab(datatab, nbLignes, N);
		
		//Maintenant on linke horizontalement
		linkHoriz(datatab);
		
		//Et verticalement, c'est un peu plus délicat car il faut attacher aux entêtes des colonnes
		//Il faut passer aussi le root en argument, pour pouvoir 
		linkVert(datatab,root);
		
		//Il ne reste plus qu'a renvoyer un objet linkmatrix qui encapsule tout ca
		return new LinkMatrix(root);
	}

	private static void linkVert(DataObject[][] datatab, RootObject root) {
		int N=datatab[0].length;
		int l=datatab.length;
		ColumnObject colcourante=(ColumnObject) root.R;
		
		//Pour chaque colonne...
		for(int ncol=0;ncol<N;ncol++){
			LinkedList<Integer> listeindun =new LinkedList<>();
			for(int i=0;i<l;i++){
				if(datatab[i][ncol]!=null)
					listeindun.addLast(i);
			}
			
			if(listeindun.isEmpty())
				continue;
			for(int i=0;i<listeindun.size()-1;i++){
				datatab[listeindun.get(i)][ncol].D=datatab[listeindun.get(i+1)][ncol];
				datatab[listeindun.get(i+1)][ncol].U=datatab[listeindun.get(i)][ncol];
			}
			//System.out.println("collage principal done");
			

			//On finit le travail en collant avec le haut des colonnes
			datatab[listeindun.getFirst()][ncol].U=colcourante;
			datatab[listeindun.getLast()][ncol].D=colcourante;
			colcourante.D=datatab[listeindun.getFirst()][ncol];
			colcourante.U=datatab[listeindun.getLast()][ncol];
			/*
			 //Use for debug : cylcing through a line
			//Prints the whole column, starting with the header, plus the header once again at the end
			ColumnObject c=colcourante;
			System.out.println(c);
			DataObject o=(DataObject) c.D;
			for(int j=0;j<listeindun.size()-1;j++){
				System.out.println(o+""+o.hashCode());
				o=(DataObject)o.D;
			}
			System.out.println(o+""+o.hashCode());
			System.out.println(o.D);
			*/
			
			//On n'oublie pas de mettre a jour la colonne courante, supposée deja bien construite
			if(ncol!=N-1){
				colcourante=(ColumnObject) colcourante.R;
			}
			
		}
		
	}

	private static void linkHoriz(DataObject[][] datatab) {
		int N=datatab[0].length;
		int l=datatab.length;
		
		//Pour chaque ligne...
		for(int i=0;i<l;i++){
			LinkedList<Integer> listeindun =new LinkedList<>();
			for(int j=0;j<N;j++){
				if(datatab[i][j]!=null)
					listeindun.addLast(j);
			}
			if(listeindun.isEmpty())
				continue;
			for(int j=0;j<listeindun.size()-1;j++){
				datatab[i][listeindun.get(j)].R=datatab[i][listeindun.get(j+1)];
				datatab[i][listeindun.get(j+1)].L=datatab[i][listeindun.get(j)];
			}
			datatab[i][listeindun.getFirst()].L=datatab[i][listeindun.getLast()];
			datatab[i][listeindun.getLast()].R=datatab[i][listeindun.getFirst()];
			/*
			 //Use for debug : cylcing through a line
			DataObject c=datatab[i][listeindun.getFirst()];
			for(int j=0;j<listeindun.size();j++){
				System.out.println(c);
				c=(DataObject) c.R;
			}
			*/
		}
		
	}

	private static DataObject[][] createDataTab(RootObject root, int[][] tab) {
		//Cette méthode renvoie un tableau de DataObjects, non chainés entre eux, mais dont les colonnes entêtes sont bien initialisées
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
		//Cas à part à la fin : la dernière colonne
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
		//méthode permettant de calculer le nombre d'occurences de 1 dans les différentes colonnes
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
		// Méthode permettant de créer les entetes de colonnes
		//On crée la première colonne
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
				//Reste des raccordements à faire
				// A ce moment, colcourante pointe sur le dernier maillon
				root.L=colcourante;
				colcourante.R=root;
	}
	
	private static void updateCount(RootObject root,int N, int[][] tab){
		//méthode permettant de mettre à jour le champ S des différentes colonnes
		
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
