
public class TestParsing {
	public static void main(String[] args) throws Exception {
		// test de lecture d'un fichier...
		// à lancer depuis la console et non depuis le compilateur d'eclipse, puisqu'on ne peut pas spécifier des arguments pour le main (args)
		
		
		// Pour l'utiliser avec la console : d'abord : javac *.java
		// Puis java -ea TestParsing emc nomdufichier
		
		/*
		if (args[0].equals("emc")) {
			FileParsing.readEMC(args[1]);
		}
		*/
		//Problème de chemin : depuis la console, on part de src, 
		//depuis eclipse du repertoire parent, donc il faut changer
		//à la main dans FileParsing au début, en mettant ou non src/
		//Pour lancer depuis eclipse : (commenter plus haut aussi)
		/*
		String file_name="knuth.txt";
		EMCFileParsing.readEMC(file_name);
		*/
		String file_name="tetris_5_8.txt";
		LinkMatrix matrix=PavageFileParsing.readPavage(file_name);
		int nbSol=matrix.DancingLinks().size();
		System.out.println(nbSol);
	}
}
