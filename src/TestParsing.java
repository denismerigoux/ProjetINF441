
public class TestParsing {
	public static void main(String[] args) throws Exception {
		// test de lecture d'un fichier...
		// � lancer depuis la console et non depuis le compilateur d'eclipse, puisqu'on ne peut pas sp�cifier des arguments pour le main (args)
		
		
		// Pour l'utiliser avec la console : d'abord : javac *.java
		// Puis java -ea TestParsing emc nomdufichier
		
		/*
		if (args[0].equals("emc")) {
			FileParsing.readEMC(args[1]);
		}
		*/
		//Probl�me de chemin : depuis la console, on part de src, 
		//depuis eclipse du repertoire parent, donc il faut changer
		//� la main dans FileParsing au d�but, en mettant ou non src/
		//Pour lancer depuis eclipse : (commenter plus haut aussi)
		/*
		String file_name="knuth.txt";
		EMCFileParsing.readEMC(file_name);
		*/
		String file_name="scott.txt";
		Object gg=null;
		LinkMatrix matrix=PavageParsing.readPavageFromFile(file_name,(Grille)gg);
		//matrix.DancingLinksSolution(0);
		System.out.println(gg);
		//Fenetre fen=new Fenetre(g,matrix.DancingLinks().get(0));
	}
}
