import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class PavageFileParsing {

	public static LinkMatrix readPavage(String file_name) throws Exception {
	//	try{
			 //Cr�ation du lecteur de fichier
	          FileReader inputFile = new FileReader("src/tests/pavage/"+file_name);

	          //Cr�ation du buffer de lecture � partir du FileReader
	          // Maintenant on peut faire .readLine() pour obtenir une ligne 
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          // String dans lequel on va stocker les lignes du fichier
	          String line;
	          
	          // On lit la premi�re ligne : nombre de colonnes de la grille
	          line=bufferReader.readLine();
	          int nbColGrille=Integer.parseInt(line);
	          
	          //On lit le nombre de lignes de la grille
	          line=bufferReader.readLine();
	          int nbLignesGrille=Integer.parseInt(line);
	          
	          // On remplit d'abord un tableau d'entier avec ce qui est lu dans le fichier
	          //Ce tableau sera la grille
	          int[][] gridd=new int[nbLignesGrille][nbColGrille];
	          int i=0;
	          line=bufferReader.readLine();
	          while(i<nbLignesGrille){
	        	  for(int j=0;j<nbColGrille;j++){
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
	          
	          //On cr�e la grille
	          Grille grid=new Grille(nbColGrille, nbLignesGrille, gridd);
	          
	          //Debug only : on affiche la grille
	          System.out.println(grid);
	          System.out.println(grid.numberOfValidCases());
	          //Maintenant on lit le nombre de pi�ces
	          int nbPieces=Integer.parseInt(line);
	          System.out.println(nbPieces+" pieces");
	          
	          // On va stocker les pi�ces dans un tableau :
	          Piece[] pieces=new Piece[nbPieces];
	          
	          //On lit chaque pi�ce et on remplit le tableau de pi�ces
	          for(int numPiece=0;numPiece<nbPieces;numPiece++){
	        	  	line=bufferReader.readLine();
	        	  	int nc=Integer.parseInt(line);
	        	  	line=bufferReader.readLine();
	        	  	int nl=Integer.parseInt(line);
	        	  	int[][] motif=new int[nl][nc];
	        	  	for(int nlp=0;nlp<nl;nlp++){
	        	  		line=bufferReader.readLine();
	        	  		for(int j=0;j<nc;j++){
	        	  			String c=Character.toString(line.charAt(j));
	  	        		  if(c.equals("*")){
	  	        			  motif[nlp][j]=1;
	  	        		  }
	  	        		  else{
	  	        			  motif[nlp][j]=0;
	  	        		  }
	        	  		}
	        	  		
	        	  	}
	        	  	pieces[numPiece]=new Piece(motif);
	        	  	//Debug only : print each piece
	        	  	//System.out.println(pieces[numPiece]);
	          }
	          
	          System.out.println("begin generation");
	          //G�n�ration du tableau selon toutes les positions possibles de chaque piece
	          int[][] allLines=generateLinesFromPieces(pieces,grid);
	          System.out.println(allLines.length);
	          
	        //  DebugUtils.affTab(allLines);
	          //Il ne reste qu'� cr�er la LinkMatrix...
	          LinkMatrix matrice=LinkMatrixCreation.createMatrixFromTab(allLines, allLines[0].length, 0, allLines.length);
	          
	          
	          //On finalise
	          bufferReader.close();
	          inputFile.close();
	          System.out.println("closing buffer, successful read");
	          
	          //On cr�e ensuite la matrice � partir du tableau
	          // et on renvoie le r�sultat
	          return matrice;
	          
	/*	}
		catch(Exception e){
			System.out.println("Erreur en ouvrant le fichier Pavage, ou autre");
			System.err.println(e);
		}
		
		return null;*/
	}

	public static int[][] generateLinesFromPieces(Piece[] pieces, Grille grid) {
		// TODO A impl�menter... et remettre private

        //Pour chaque pi�ce, il faut d'abord obtenir la liste des pi�ces qu'elle g�n�re, puis la faire bouger partout l� ou c'est possible
        //Pour ce faire, on va utiliser la m�thode admissiblePositionForPiece(Piece p, int i,int j) de la classe Grille
        
		int totPos=0; //On va maj ce nombre au fur et a mesure, il donnera a la fin le nb de lignes remplies du tableau
		int nbColTypePavage=grid.numberOfValidCases(); // Nombre de colonnes dans la partie gauche de la matrice
		int[][] tab=new int[pieces.length*8*nbColTypePavage][nbColTypePavage+pieces.length]; //Repr�sente notre future matrice
		//toutes les lignes ne seront pas utilis�es il faudra recopier � la fin
		//Pire des cas : chaque piece se retourne 8 fois, et on peut la mettre partout (pas tr�s r�aliste mais bon...)
		for(int numPiece=0;numPiece<pieces.length;numPiece++){
			LinkedList<Piece> genpieces=pieces[numPiece].getListOfTransformations();
			System.out.println("treating piece \n"+pieces[numPiece]+" transfo : "+genpieces.size());
			//On r�cup�re les diff�rentes transformations de la piece de d�part
			//Ensuite on les parcourt toutes
			for(Piece p : genpieces){
				//Ici, il faut g�n�rer des lignes
				for(int i=0;i<grid.nbLigne;i++){
					for(int j=0;j<grid.nbCol;j++){
						//On fait bouger la pi�ce un peu partout
						if(grid.admissiblePositionForPiece(p, i, j)){
							//D�licat : il faut obtenir les cases couvertes, jusque l� rien de bien m�chant
							//MAIS il faut aussi transformer ces coordonn�es x,y en une seule coord sur la ligne, entre 0 et numberOfValidCases !
							LinkedList<Integer> covered = grid.getCoveredPositions(p,i,j);
							for(int l : covered){
								tab[totPos][l]=1;
							}
							tab[totPos][nbColTypePavage+numPiece]=1;
							totPos++;
						}
					}
				}
				
			}
		}
		
		
		//On rabote le tableau, de sorte qu'il n'y ait plus de ligne vide en bas
		int[][] tabdef=new int[totPos][nbColTypePavage+pieces.length];
		for(int i=0;i<totPos;i++){
			for(int j=0;j<nbColTypePavage+pieces.length;j++){
				tabdef[i][j]=tab[i][j];
			}
		}
		return tabdef;
	}
}
