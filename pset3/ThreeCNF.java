import java.util.LinkedList;

public  class ThreeCNF{
  private int numVars; // total number of boolean  variables in the formula
                       // the variables are v_0,v_1,..., v_{numVars-1}
  private LinkedList<Clause> clauses;
  public ThreeCNF(int n){
    numVars = n;
    clauses = new LinkedList<Clause>();
  }

  public int  numVars(){ return numVars; }

  public void addClause(int v1,boolean n1,int v2,boolean n2,int v3,boolean n3){
    clauses.add(new Clause(v1,n1,v2,n2,v3,n3));
  }

  private static class Clause{
    int var1,var2,var3;
    boolean neg1,neg2,neg3; // neg=true iff the corresponding variable
                            // is negated
    Clause( int v1, boolean n1,
            int v2,boolean n2,
            int v3,boolean n3){
      var1 = v1; neg1 = n1;
      var2 = v2; neg2 = n2;
      var3 = v3; neg3 = n3;
    }
  }
  
  public boolean evaluate(boolean[] candidate){
	  // precondition: candidate != null && candidiate.length == numVars
	  boolean result = true;
	  for (Clause c : clauses){
		  result = result && evaluate_clause(c, candidate);
	  }
	  return result;
  }

  public boolean evaluate_clause(Clause c, boolean[] candidate) {
	// evaluate the current clause
	if ((c.var1 >= candidate.length)||
		(c.var2 >= candidate.length)||
		(c.var3 >= candidate.length)){
		System.out.println("Invalid variable index assigned to Clause obj");
		return false;
	}
	boolean t1 = candidate[c.var1] ^ c.neg1;
	boolean t2 = candidate[c.var2] ^ c.neg2;
	boolean t3 = candidate[c.var3] ^ c.neg3;
	
	return 	t1 || t2 || t3;
  }
}
