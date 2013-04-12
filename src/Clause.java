import java.util.ArrayList;

public class Clause
{
	int doneFlag=1;
	ArrayList<Integer> clauseVars = new ArrayList<Integer>();

	public static Clause cloneObj(Clause cToCopy){

		Clause cl = new  Clause();
		cl.doneFlag = cToCopy.doneFlag;
		cl.clauseVars = cToCopy.clauseVars;	

		return cl;

	}

	public void inputvarinClause(int v)
	{
		clauseVars.add(v);
	}


}
