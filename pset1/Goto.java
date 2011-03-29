package pset1;

public class Goto { 
  void max(){
outer:
    for (int i = 2; i < 1000; i++) {
      for (int j = 2; j < i; j++) {
        if (i % j == 0)
          continue outer;
      }
      System.out.println (i);
    }
  }
}
