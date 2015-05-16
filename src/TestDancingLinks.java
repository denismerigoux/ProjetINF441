import java.util.ArrayList;

public class TestDancingLinks {
	
	public static void main(String args[]) {
		String file_name="sujet.txt";
		LinkMatrix Matrix = FileParsing.readEMC(file_name);
		ArrayList<ArrayList<Integer>> solutions = Matrix.DancingLinks();
		System.out.println("Le nombre de solutions est : "+solutions.size());
	}

}
