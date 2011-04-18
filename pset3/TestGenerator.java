import gov.nasa.jpf.jvm.Verify;

public class TestGenerator {

	public static void main(String args[]){
		new TestGenerator().generateAllCombinationsTests(
				new MethodInfo("m", "C", new String[] {"int", "String"}, true),
				new Domain());
	}
	
	public TestGenerator(){
	}
	
	public String generateAllCombinationsTests(MethodInfo m, Domain d){
		if (!m.isStatic()){
			System.out.println("Non-static Method is not supported :(");
			return null;
		}
		int[] ints  = d.getInts();
		boolean[] booleans = d.getBooleans();
		String[] strings = d.getStrings();
		Verify.resetCounter(0);
		int argsCount = m.argumentTypes().length;
		String[] argTypes = m.argumentTypes();
		int[] counter = new int[argsCount];
		for (int i=0;i<argsCount; i++){
			int opts = 0;
			if (argTypes[i].toLowerCase().equals("int")) {
				opts = d.getInts().length - 1;
			} else if (argTypes[i].toLowerCase().equals("boolean")){
				opts = d.getBooleans().length - 1;
			} else if (argTypes[i].toLowerCase().equals("string")){
				opts = d.getStrings().length - 1;
			}
			counter[i] = Verify.getInt(0, opts);
		}
		
		System.out.println("@Test public void test" + Verify.getCounter(0) + "() {");
		Verify.incrementCounter(0);
		System.out.print("\t" + m.declaringClassName() + "." + m.methodName() + "(");
		for (int a=0;a<argsCount; a++){			
			if (a!=0){
				System.out.print(", ");
			}
			if (argTypes[a].toLowerCase().equals("int")) {
			   System.out.print(ints[counter[a]]);
			} else if (argTypes[a].toLowerCase().equals("boolean")){
				System.out.print(booleans[counter[a]]);
			} else if (argTypes[a].toLowerCase().equals("string")){
				System.out.print(strings[counter[a]]);
			}
		}
		System.out.println(");");
		System.out.println("}");
		
		return null;
	}
	
}