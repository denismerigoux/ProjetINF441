public class TestDancingLinks {
	
	public static void main(String args[]) {
		String file_name="sujet.txt";
		LinkMatrix Matrix = EMCParsing.readEMCFromFile(file_name);
		System.out.println("Calcul des solutions.");
		Matrix.DancingLinksSolution();
	}

}
