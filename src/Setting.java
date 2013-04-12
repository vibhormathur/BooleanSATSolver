import java.util.Stack;


public class Setting {
	int currentClause;
	int totalVar;
	int totalCla;
	static Stack<Integer> setVar = new Stack<Integer>();

	public void setFunction(int varToSet){
		setVar.push(varToSet);
		clauseDone(varToSet);
		rearrangeClause(-(varToSet));
	}

	public void desetFunction(int varToDeset){
		int y;

		do{
			y = setVar.peek();
			setVar.pop();
		}while(y != varToDeset);
	}

	public void rearrangeClause(int varToRemove){
		currentClause = 1;
		int tempIndex;
		int tempClause;

		while(currentClause<=totalCla){
			if(Line.c[currentClause].doneFlag == 1){
				for(int k=0; k<Line.c[currentClause].clauseVars.size(); k++){
					if((Line.c[currentClause].clauseVars.get(k)) == varToRemove)
					{
						tempIndex=k;
						tempClause=currentClause;
						Line.c[currentClause].clauseVars.remove(Line.c[currentClause].clauseVars.get(k));
					}
				}
			}
			currentClause++;
		}
	}


	public void clauseDone(int settedVar){
		totalVar = Line.NumberofVariables;
		totalCla = Line.NumberofClauses;
		currentClause =1;

		while(currentClause<=totalCla){
			if(Line.c[currentClause].doneFlag == 1){
				for(int k=0; k<Line.c[currentClause].clauseVars.size(); k++){
					if((Line.c[currentClause].clauseVars.get(k)) == settedVar)
					{
						Line.c[currentClause].doneFlag = 0;
					}
				}
			}
			currentClause++;
		}
	}
}
