import java.util.Scanner;
import java.util.regex.MatchResult;


public class Line {
	static Integer NumberofVariables=0;
	static Integer NumberofClauses=0;
	static Integer currentClause = 1;
	static Integer varinClause;
	static Output output=new Output();
	static Clause[] c;

	public static void fetchLines(String str)
	{

		Scanner s = new Scanner(str);

		if (str.charAt(0)=='c')
		{
			s.close();
			return;
		}
		else if (str.charAt(0)=='p')
		{
			int[] intArray = new int[2];
			s.findInLine("p cnf ((?<=\\[)[^\\]]+|-?\\w+) ((?<=\\[)[^\\]]+|-?\\w+)");
			MatchResult result = s.match();

			for (int i=1; i<=result.groupCount(); i++)
			{
				intArray[i-1] = Integer.parseInt(result.group(i));
			}
			
			NumberofVariables = intArray[0];
			NumberofClauses = intArray[1];
			c = new Clause[NumberofClauses+1];

			s.close();
			return;
		}
		else 
			{
				String delims = "[ ]+";
				String[] tokens = str.split(delims);
				varinClause = tokens.length;
				c[currentClause] = new Clause();
				c[currentClause].clauseVars.add(0);


				for (int j = 1; j < varinClause; j++)
				{
					c[currentClause].inputvarinClause(Integer.parseInt(tokens[j-1]));
				}

				currentClause++;
			


		}
		s.close();

	}
}

