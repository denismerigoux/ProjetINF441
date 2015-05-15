
public class TestDancingLinks {
	
	public static void main(String args[]) {
		String file_name="knuth.txt";
		LinkMatrix Matrix = FileParsing.readEMC(file_name);
		Matrix.DancingLinks();
		
	}

}
