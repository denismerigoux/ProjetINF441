import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		if (args[0].equals("emc")) {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bufferReader = new BufferedReader(isr);
			LinkMatrix matrix = EMCParsing.readEMC(bufferReader);
			ArrayList<DataObject[]> solutions = matrix.DancingLinks();
			System.out.println("Le problème admet " + solutions.size()
					+ " solutions.");

			Scanner in = new Scanner(System.in);
			int n = 0;

			while (true) {
				System.out
						.println("Entrer le numéro de la solution à afficher :");
				System.out.println("(entre 0 et " + (solutions.size() - 1)
						+ ", -1 pour toutes les solutions, -2 pour arrêter)");
				if (in.hasNextInt())
					n = in.nextInt();
				else
					n = -2;
				if (n == -1)
					matrix.PrintSolutions(solutions);
				else if (n == -2)
					break;
				else if ((n >= 0) && (n < solutions.size()))
					matrix.PrintSolution(solutions.get(n));
				else
					System.out
							.println("Il n'existe pas de solution pour ce numéro.");
			}
			in.close();
			try {
				bufferReader.close();
				isr.close();
			} catch (Exception e) {
				System.out.println("Erreur en fermant l'input.");
			}

		} else if (args[0].equals("pavage")) {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader bufferReader = new BufferedReader(isr);
			Pair<LinkMatrix, Grille> result = PavageParsing
					.readPavage(bufferReader);
			ArrayList<DataObject[]> solutions = result.object1.DancingLinks();
			System.out.println("Le problème admet " + solutions.size()
					+ " solutions.");

			Scanner in = new Scanner(System.in);
			int n = 0;

			while (true) {
				System.out
						.println("Entrer le numéro de la solution à afficher :");
				System.out.println("(entre 0 et " + (solutions.size() - 1)
						+ ", ou -1 pour arrêter)");
				n = in.nextInt();
				if ((n >= 0) && (n < solutions.size())) {
					result.object1.PrintSolution(solutions.get(n));
					new Fenetre(result.object2, solutions.get(n));
				}
				else if (n==-1)
					break;
				else
					System.out
							.println("Il n'existe pas de solution pour ce numéro.");
			}

			in.close();
			try {
				bufferReader.close();
				isr.close();
			} catch (Exception e) {
				System.out.println("Erreur lors de la fermeture de l'entrée.");
			}
		} else {
			System.out.println(args[0] + " n'est pas un argument valable !");
		}
	}
}
