package pset1;

public class Test2 {
	public int method1(int a, int b){
		if(b==0){
			return 0;
		}else if(b<0){
			return a/-b;
		}else{
			return a/b;
		}
	}

	public int method2(int a, int b){
		int result = 1;
		while(b>0){
			result = result * a;
			b--;
		}
		return result;
	}
}
