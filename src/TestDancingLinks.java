import java.util.ArrayList;

public class TestDancingLinks {
	
	public static void main(String args[]) {
		String file_name="knuth.txt";
		LinkMatrix Matrix = EMCFileParsing.readEMC(file_name);
		System.out.println("Calcul des solutions.");
		Matrix.DancingLinksSolutions();
	}

}
