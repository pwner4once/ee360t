import gov.nasa.jpf.jvm.Verify;


public class BooleanSolver {
	public boolean[] solve(ThreeCNF formula){
		Verify.resetCounter(0);
		int numofVars = formula.numVars();
		boolean[] trial_candidate = new boolean[numofVars];
		
		for(int i=0; i<numofVars; i++){ trial_candidate[i] = Verify.getBoolean();}
		
		if ((Verify.getCounter(0) == 0) && (formula.evaluate(trial_candidate)== true)){
			Verify.incrementCounter(0);
			for (int i=0;i<trial_candidate.length;i++){
				if (i!=0) System.out.print(", ");
				System.out.print("v" + i + " = " + trial_candidate[i]);
			}
			System.out.println();
			return trial_candidate;
		}
		
		return null;
	}
}
