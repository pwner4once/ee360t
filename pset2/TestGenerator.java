package pset2;

import java.util.ArrayList;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Type;

import com.sun.org.apache.bcel.internal.generic.BasicType;




public class TestGenerator {
  public String createTests(String className) throws ClassNotFoundException {
    JavaClass jc = Repository.lookupClass(className);
    ClassGen cg = new ClassGen(jc);
    ConstantPoolGen cpg = cg.getConstantPool();
    int combinations = 0;
    
    StringBuffer output = new StringBuffer();
    for (Method m: cg.getMethods()){
      Method m1 = m;
      Type[] types = m1.getArgumentTypes();
      String m_name = m.getName();
      String sig = m.getSignature();
      // only care about methods with arguments
      if (types.length <= 0){
        continue;
      } else {
        combinations = 1;
      }
      
      for (Type bt: types){
        if(bt.toString().toLowerCase().indexOf("int") != -1){
          combinations *= Domain.INT.length;
        } else if (bt.toString().toLowerCase().indexOf("boolean") != -1){
          combinations *= Domain.BOOLEAN.length;
        } else if (bt.toString().toLowerCase().indexOf("object") != -1){
          combinations *= Domain.OBJECT.length;
        } else if (bt.toString().toLowerCase().indexOf("string") != -1){
          combinations *= Domain.STRING.length;
        }
      }
    
      // setting up the bins to append strings to
      StringBuffer[] sbs = new StringBuffer[combinations];
      boolean[] appended2 = new boolean[combinations];
      java.util.Arrays.fill(appended2, false);
      
      // set up the basic string for each test case
      for (int i = 0; i<sbs.length; i++){
        sbs[i] = new StringBuffer();
        sbs[i].append("@Test public void test_" + m_name + "_" + (i+1) + "() { " + m_name + "(");
      }
      
      int[][] indecies4Printing = new int[combinations][combinations];
      
      int incrementor = combinations/2;
      for (int row = 0; row < combinations; row++){
        for (int column = 0; column < combinations; column++){
          
        }
        incrementor /= 2;
      }
      
      // appending combinations of arguments.. 
      for (int i = 0; i<combinations; i++){
        for(int type_i = 0; type_i<types.length; type_i++){
          Type arguentTypes = types[type_i];
          String appendKey = null;
          if (arguentTypes.toString().toLowerCase().indexOf("int") != -1){
            appendKey = Integer.toString(Domain.INT[i%Domain.INT.length]);
          } else if (arguentTypes.toString().toLowerCase().indexOf("boolean") != -1){
            appendKey = Boolean.toString(Domain.BOOLEAN[i%Domain.BOOLEAN.length]);    
          } else if (arguentTypes.toString().toLowerCase().indexOf("object") != -1){
            appendKey = Domain.OBJECT[i%Domain.OBJECT.length].toString();
          } else if (arguentTypes.toString().toLowerCase().indexOf("string") != -1){
            appendKey = Domain.STRING[i%Domain.STRING.length];
            if (appendKey == ""){ appendKey = "\"\""; }
            if (appendKey == "0"){appendKey = "\"0\""; }
          }
          
          if (appended2[i]){
            sbs[i].append(", " + appendKey);
          } else {
            sbs[i].append(appendKey);
            appended2[i] = true;
          }
        }
      }
      
      
      for (int i = 0; i<sbs.length; i++){
        sbs[i].append("); }");
        output.append(sbs[i].toString() + '\n');
      }
    }
    
    
    return output.toString();
  }
  

  public static void main(String[] args) throws ClassNotFoundException {
   System.out.println(new TestGenerator().createTests("pset2.Demo"));
	  
	
  }
  
  public static String txtExpand(ArrayList<String> objs, int index){
	  String ret = "";
	  if (objs.size() == 0){
		  return ret;
	  }
	  
	  String type = objs.get(0);
	  if (type.equals("int")){
		  if (index == 2)
			  objs.remove(0);
	  } else if (type.equals("boolean")){
		  if (index == 1)
			  objs.remove(0);
	  } else if (type.equals("object")){
		  if (index==1){
			  objs.remove(0);
		  }
	  } else if (type.equals("string")){
		  if (index==3)
			  objs.remove(0);
	  }
	  
	  if (type.equals("int")){
		  if (objs.size() > 1){
			  ret += Integer.toString(Domain.INT[index]);
			  String ta = objs.get(0);
			  int count = 0;
			  if (ta.equals("int")){
				  count = Domain.INT.length;
			  } else if (ta.equals("boolean")){
				  count = Domain.BOOLEAN.length;
			  } else if(ta.equals("object")){
				  count = Domain.OBJECT.length;
			  } else if(ta.equals("string")){
				  count = Domain.STRING.length;
			  }
			  
			  for(int d = 0; d<count; d++){
				  ret += txtExpand(objs, d);
			  }
			  
		  } else{
			  return Integer.toString(Domain.INT[index]) + '\n';
		  }
			  
	  } else if (type.equals("boolean")){
		  if (objs.size() > 1){
			  ret += Boolean.toString(Domain.BOOLEAN[index]);
			  String ta = objs.get(0);
			  int count = 0;
			  if (ta.equals("int")){
				  count = Domain.INT.length;
			  } else if (ta.equals("boolean")){
				  count = Domain.BOOLEAN.length;
			  } else if(ta.equals("object")){
				  count = Domain.OBJECT.length;
			  } else if(ta.equals("string")){
				  count = Domain.STRING.length;
			  }
			  
			  for(int d = 0; d<count; d++){
				  ret += txtExpand(objs, d);
			  }
			  
		  } else{
			  return Boolean.toString(Domain.BOOLEAN[index]) + '\n';
		  }
	  } else if (type.equals("object")){
		  if (objs.size() > 1){
			  ret += Domain.OBJECT[index];
			  String ta = objs.get(0);
			  int count = 0;
			  if (ta.equals("int")){
				  count = Domain.INT.length;
			  } else if (ta.equals("boolean")){
				  count = Domain.BOOLEAN.length;
			  } else if(ta.equals("object")){
				  count = Domain.OBJECT.length;
			  } else if(ta.equals("string")){
				  count = Domain.STRING.length;
			  }
			  
			  for(int d = 0; d<count; d++){
				  ret += txtExpand(objs, d);
			  }
			  
		  } else{
			  return Domain.OBJECT[index] + '\n';
		  }
	  } else if (type.equals("string")){
		  if (objs.size() > 1){
			  ret += Domain.STRING[index];
			  String ta = objs.get(0);
			  int count = 0;
			  if (ta.equals("int")){
				  count = Domain.INT.length;
			  } else if (ta.equals("boolean")){
				  count = Domain.BOOLEAN.length;
			  } else if(ta.equals("object")){
				  count = Domain.OBJECT.length;
			  } else if(ta.equals("string")){
				  count = Domain.STRING.length;
			  }
			  
			  for(int d = 0; d<count; d++){
				  ret += txtExpand(objs, d);
			  }
			  
		  } else{
			  return Domain.STRING[index] + '\n';
		  }
	  }
		  
	  
    return null;
    
  }
  
  public static int str2isze(Object obj[]){
	  return obj.length;
  }
}
