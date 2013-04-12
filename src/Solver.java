import java.util.*;


public class Solver {
	int m;
	static int totalVar;
	static int totalCla;
	static int count=0;
	static int solvable = 0;
	static int s = 0;
	int errorunsat=1;
	int successsat=0;
	int firsterror=0;
	int currentClause = 1;
	int countRecursion=0;
	int ifpos=1;
	int aflag=0;
	Output output=new Output();
	Setting setting = new Setting();
	Decisions[] decisions ;


	public int startSolve(getFile file){
		totalVar = Line.NumberofVariables;
		totalCla = Line.NumberofClauses;
		decisions=new Decisions[totalVar];

		if(s == 0){
			s=duplicates(file);
			return(s);
		}
		else{
			return(s);
		}
	}

	public int duplicates(getFile file){
		while(currentClause<=totalCla){
			Set<Integer> hashSet = new HashSet<Integer>();
			hashSet.addAll(Line.c[currentClause].clauseVars);
			hashSet.remove(0);
			Line.c[currentClause].clauseVars.clear();
			Line.c[currentClause].clauseVars.add(0);
			Line.c[currentClause].clauseVars.addAll(hashSet);
			currentClause++;
		}

//		pure(file);
		testAgain(file);
		solvable=0;
		return(solvable);

	}


	public void testAgain(getFile file){
		int iterate=1;
		int testAgainFlag;
		int unittest;
		int goFurther;
		int clausesDoneFlag;

		while(iterate==1){
			testAgainFlag=1;
			goFurther=0;
			clausesDoneFlag=1;
			iterate=0;
			currentClause = 1;

			for(int i=1; i<=totalCla;i++){
				if(file.clauseSize[i] == 0){
					aflag=1 ;
				}
				else{
					aflag=0;
					break;
				}
			}

			while(currentClause<=totalCla){
				if(Line.c[currentClause].doneFlag==1 & aflag==0){
					testAgainFlag=0;	
				}
				currentClause++;
			}

			currentClause=1;

			if(testAgainFlag==0){
				pure(file);
				unittest=unit(file);

				if(unittest != errorunsat){
					while(currentClause<=totalCla){
						if(file.clauseSize[currentClause]!=0){
							goFurther=1;
						}
						currentClause++;
					}
				}

				if(goFurther == 1){
					other(file);
				}

			}
			else{
				return;
			}

			while(currentClause<=totalCla){
				if(Line.c[currentClause].doneFlag != 0 ){
					clausesDoneFlag=0;
					break;
				}
				currentClause++;
			}
			/*
			if(clausesDoneFlag == 1){
				return;
		}else{
			 */			currentClause=1;
			 while(currentClause<=totalCla){
				 if(file.clauseSize[currentClause]!=0){
					 countRecursion++;
					 iterate=1;	
					 //	testAgain(file);
				 }
				 currentClause++;
			 }
		}
		//	}
	}



	public int unit(getFile file){
		int unitFlag = 0;
		int repeat = 1;
		int error=0;

		while(repeat==1){
			for (int n = 1; n <=totalCla; n++)
			{
				if(file.clauseSize[n] == 1){
					unitFlag = 1;

					for(int x = 1; x <=totalCla; x++){
						if(file.clauseSize[x] == 1 & x != n){
							if(Line.c[n].clauseVars.get(1) == (-(Line.c[x].clauseVars.get(1)))){
								error=1;
								repeat=0;
								errorunsat = backJumping(file);
								file.getLiterals();
								return(errorunsat);
							}
						}
					}

					if(error != 1){

						if(!(Setting.setVar.contains(Line.c[n].clauseVars.get(1)))){
							if(Line.c[n].clauseVars.get(1)>0){
								if(file.setPosLit[Line.c[n].clauseVars.get(1)] != 1 & file.setNegLit[Line.c[n].clauseVars.get(1)] != 1){
									file.setPosLit[Line.c[n].clauseVars.get(1)] = 1;
									file.setNegLit[Line.c[n].clauseVars.get(1)] = 1;
									setting.setFunction(Line.c[n].clauseVars.get(1));
									repeat=1;
								}
							}


							if(Line.c[n].clauseVars.get(1)<0){
								if(file.setPosLit[-(Line.c[n].clauseVars.get(1))] != 1 & file.setNegLit[-(Line.c[n].clauseVars.get(1))] != 1){

									file.setPosLit[-(Line.c[n].clauseVars.get(1))] = 1;
									file.setNegLit[-(Line.c[n].clauseVars.get(1))] = 1;
									setting.setFunction(Line.c[n].clauseVars.get(1));
									repeat=1;
								}
							}
						}
					}
					file.getLiterals();
				}
				else{
					repeat=0;
				}
			}
		}

		return(successsat);
	}


	public void pure(getFile file){
		int pureFlag = 0;
		int continuePure=1;

		while(continuePure == 1){
			continuePure=0;
			
			for (int n = 1; n <=totalVar; n++){
				if(file.posLit[n] != 0 & file.negLit[n] == 0){
					continuePure=1;
					pureFlag = 1;
					if(!(Setting.setVar.contains(n))){
						if(file.setPosLit[n] != 1)
						{
							file.setPosLit[n] = 1;
							file.setNegLit[n] = 1;
							setting.setFunction(n);
							file.getLiterals();
						}
					}
				}
				if(file.posLit[n] == 0 & file.negLit[n] != 0){
					continuePure=1;
					pureFlag = 1;
					m=-n;
					if(!(Setting.setVar.contains(m))){
						if(file.setNegLit[n] != 1)
						{
							file.setPosLit[n] = 1;
							file.setNegLit[n] = 1;
							setting.setFunction(m);
							file.getLiterals();
						}
					}
				}
			}

			if(pureFlag == 0){
				continuePure=0;
			}
		}


		return;
	}

	/*
	public void pure(getFile file){
		int pureFlag = 0;
		for (int n = 1; n <=totalVar; n++){
			if(file.posLit[n] != 0 & file.negLit[n] == 0){
				pureFlag = 1;
				if(!(Setting.setVar.contains(n))){
					if(file.setPosLit[n] != 1)
					{
						file.setPosLit[n] = 1;
						file.setNegLit[n] = 1;
						setting.setFunction(n);
						file.getLiterals();
					}
				}
			}
			if(file.posLit[n] == 0 & file.negLit[n] != 0){
				pureFlag = 1;
				m=-n;
				if(!(Setting.setVar.contains(m))){
					if(file.setNegLit[n] != 1)
					{
						file.setPosLit[n] = 1;
						file.setNegLit[n] = 1;
						setting.setFunction(m);
						file.getLiterals();
					}
				}
			}
		}

		if(pureFlag == 0){
			return;
		}
	}

	 */
	public int findMaxOcc(getFile file){
		int maxOcc = file.posLit[1];
		int index=0;

		for(int i = 1; i <= (file.posLit.length-1); i++) {

			if(maxOcc<=file.posLit[i]){
				maxOcc = file.posLit[i];
				index=i;
			}
		}

		for(int i = 1; i <= (file.negLit.length-1); i++) {

			if(maxOcc<=file.negLit[i]){

				maxOcc = file.negLit[i];
				index=i;
				ifpos=0;
			}
		}  
		return(index);
	}

	public void other(getFile file){
		int maxOccuringIndex = findMaxOcc(file);

		if(file.setPosLit[maxOccuringIndex] != 1 & file.setNegLit[maxOccuringIndex] != 1){
			if (ifpos==1){
				file.setPosLit[maxOccuringIndex] = 1;
				file.setNegLit[maxOccuringIndex] = 1;
				count++;
				decisions[count] = new Decisions();
				decisions[count].variable = maxOccuringIndex;
				decisions[count].copyClauseArray = (Clause[])Line.c.clone();
				decisions[count].checked++;
				setting.setFunction(maxOccuringIndex);
				file.getLiterals();
			}
			else{
				file.setPosLit[maxOccuringIndex] = 1;
				file.setNegLit[maxOccuringIndex] = 1;
				count++;
				decisions[count] = new Decisions();
				decisions[count].variable = -(maxOccuringIndex);
				decisions[count].copyClauseArray = copyClauses(Line.c);
				decisions[count].checked++;
				setting.setFunction(-(maxOccuringIndex));
				file.getLiterals();
			}
		}
	}


	public int backJumping(getFile file){
		while(count > 0 ){
			if(decisions[count].checked != 2){
				break;
			}
			else{
				decisions[count].checked = 0;
				count--;
			}
		}

		if(count == 0){
			output.printOutput(1);
		}
		else{
			file.setPosLit[Math.abs(decisions[count].variable)] = 0;
			file.setNegLit[Math.abs(decisions[count].variable)] = 0;
			setting.desetFunction(decisions[count].variable);
			Line.c = (Clause[])decisions[count].copyClauseArray.clone();
			decisions[count].variable = -(decisions[count].variable);
			decisions[count].checked++;
			file.setPosLit[Math.abs(decisions[count].variable)] = 1;
			file.setNegLit[Math.abs(decisions[count].variable)] = 1;
			setting.setFunction(decisions[count].variable);
			file.getLiterals();
		}

		return (1);

	}

	public Clause[] copyClauses(Clause[] tempc){
		Clause[] cltemp = new Clause[Line.NumberofClauses+1];

		for(int i=1; i<=(Line.NumberofClauses); i++){
			cltemp[i] = new Clause();			
			cltemp[i].doneFlag = tempc[i].doneFlag;
			cltemp[i].clauseVars = tempc[i].clauseVars;	
		}
		return(cltemp);
	}
}