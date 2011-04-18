public class Test_ThreeCNF {
	public static void main(String args[]){
		ThreeCNF formula = new ThreeCNF(3);
		formula.addClause(0, true, 1, false, 2, true);
		formula.addClause(0, true, 1, true, 2, false);
		boolean[] candidate = new boolean[]{ false, true, false }; // i.e., v0 = false, v1 = true, v2 = false
		System.out.println(formula.evaluate(candidate));
	}
}
