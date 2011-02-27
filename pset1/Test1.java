package pset1;

public class Test1 {
	public int method(int a, int b){
		int result = 1;
		for(int i=b; i>0; i--){
			result = result * a;
		}
		return result;
	}
}
