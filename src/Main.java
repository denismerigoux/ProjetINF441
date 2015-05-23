public class Main {

	public static void main(String[] args) {
		if (args[0].equals("emc")) {
			LinkMatrix matrix = EMCParsing.readEMCFromStandardInput();
			matrix.DancingLinksNumberOfSolutions();
		} else if (args[0].equals("pavage")) {
			LinkMatrix matrix = PavageParsing.readPavageFromStandardInput();
			matrix.DancingLinksNumberOfSolutions();
		} else {
			System.out.println(args[0]+" n'est pas un argument valable !");
		}
	}

}
