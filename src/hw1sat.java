import java.io.*;

public class hw1sat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int ifSolvableOutput;
		getFile file = new getFile();
		Solver solver = new Solver();
		Output output = new Output();
		File inputFile = null;

		try{
			inputFile = new File(args[0]);
			file.fetchFile(inputFile);
			Clause[] originalClauses = new Clause[Solver.totalCla];
			originalClauses = solver.copyClauses(Line.c);
			ifSolvableOutput = solver.startSolve(file);
			Line.c = (Clause[])originalClauses.clone();
			output.printOutput(ifSolvableOutput);
		}
		catch (Exception e)	{
			System.err.println("Error: " + e.getMessage());
		}

	}

}
