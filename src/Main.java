public class Main {

	public static void main(String[] args) {
		if (args[0].equals("emc")) {
			LinkMatrix matrix = EMCParsing.readEMCFromStandardInput();
			matrix.DancingLinksNumberOfSolutions();
		} else if (args[0].equals("pavage")) {
			Pair<LinkMatrix, Grille> result = PavageParsing.readPavageFromStandardInput();
			result.object1.DancingLinksNumberOfSolutions();
		} else {
			System.out.println(args[0]+" n'est pas un argument valable !");
		}
	}

}
