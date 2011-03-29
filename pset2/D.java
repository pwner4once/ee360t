package pset2;

public class D {
	static void main(String[] a){
		foo(a);
		bar(a);
	}

	static void foo(String[] a) {
		if (a==null) return;
		bar(a);
	}
	
	static void bar(String[] a) {}
}
