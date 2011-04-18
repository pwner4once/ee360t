public class BooleanSolverTest {
	public static void main(String args[]){
		ThreeCNF formula = new ThreeCNF(3);
		formula.addClause(0, false, 1, false, 2, false);
		//formula.addClause(0, true, 1, true, 2, true);
		boolean[] solution = new BooleanSolver().solve(formula);
//		if (solution != null){
//			System.out.println("Var => Value");
//			for (int a=0;a<solution.length;a++){
//				System.out.println(a + " : " + solution[a]);
//			}			
//		}
	}
}
