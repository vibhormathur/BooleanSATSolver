import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class getFile {
	int[] clauseSize;
	int[] posLit;
	int[] negLit;
	int[] setPosLit;
	int[] setNegLit;

	public void fetchFile(File inputFile)    
	{
		try{
			String strLine;

			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while((strLine=br.readLine()) != null)
			{
				Line.fetchLines(strLine);
			}

			br.close();
			in.close();

			getLiterals();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public void getLiterals(){

		int currentClause = 1;
		int totalVar = Line.NumberofVariables;
		int totalCla = Line.NumberofClauses;
		posLit = new int[totalVar+1];
		negLit = new int[totalVar+1];
		clauseSize = new int[totalCla+1];
		setPosLit = new int[totalVar+1];
		setNegLit = new int[totalVar+1];

		for (int n = 0; n <= totalVar; n++)
		{
			posLit[n] = 0;
		}

		for (int n = 0; n <= totalVar; n++)
		{
			negLit[n] = 0;
		}

		while(currentClause<=totalCla){
			if(Line.c[currentClause].doneFlag==1){
				for(int k=0; k<Line.c[currentClause].clauseVars.size(); k++){
					clauseSize[currentClause] = Line.c[currentClause].clauseVars.size() - 1;

					if((Line.c[currentClause].clauseVars.get(k))>0)
					{					
						posLit[Line.c[currentClause].clauseVars.get(k)] = posLit[Line.c[currentClause].clauseVars.get(k)] + 1; 

					}
					else {
						negLit[Math.abs(Line.c[currentClause].clauseVars.get(k))] = negLit[Math.abs(Line.c[currentClause].clauseVars.get(k))] + 1; 
					}
				}

			}
			currentClause++;
		}
	}
}



