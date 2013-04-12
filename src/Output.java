import java.util.Collections;
import java.util.Stack;



public class Output {

	public void printOutput(int ifSolvableOutput){
	    Stack<Integer> copysetVar = (Stack<Integer>)Setting.setVar.clone();

		System.out.print("c Solution for previous problem\n");

		if(ifSolvableOutput == 0){
			checker(copysetVar);
			System.out.print("s SATIFIABLE\n");
			System.out.print("v ");

			Collections.sort(Setting.setVar, Collections.reverseOrder());

			while (!Setting.setVar.empty()){
				System.out.print(Setting.setVar.pop()+" ");
			}
			System.out.print("0\n");

		}
		else{
			System.out.print("s UNSATIFIABLE");
			System.out.print("\n");

			System.exit(0);
		}


	}

	public void checker(Stack<Integer> settedVar){
		int y;
		int currentClause;
		int checkerAllDone=0;
		int totalVar = Line.NumberofVariables;
		int totalCla = Line.NumberofClauses;

		do{
			y = settedVar.peek();
			currentClause=1;
			while(currentClause<=totalCla){
				if(Line.c[currentClause].doneFlag == 1){
					for(int k=0; k<Line.c[currentClause].clauseVars.size(); k++){
						if((Line.c[currentClause].clauseVars.get(k)) == y)
						{
							Line.c[currentClause].doneFlag = 0;
						}
					}
				}
				currentClause++;

			}

			settedVar.pop();
		}while(!settedVar.isEmpty());


		currentClause=1;
		while(currentClause<=totalCla){
			if(Line.c[currentClause].doneFlag != 0 ){
				checkerAllDone=0;
				break;
			}
			else{
				checkerAllDone=1;
			}
			currentClause++;
		}

		if(checkerAllDone==0){
			System.out.println("Solution Incorrect");
		}
		else{
			System.out.println("Solution Correct");
		}
	}

}
