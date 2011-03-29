package pset2;

import java.util.ArrayList;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.BranchInstruction;


public class GraphGenerator {
  CFG createCFG(String className) throws ClassNotFoundException {

    CFG cfg = new CFG();
    JavaClass jc = Repository.lookupClass(className);
    ClassGen cg = new ClassGen(jc);
    Method[] methods = cg.getMethods();
    ConstantPoolGen cpg = cg.getConstantPool();
    Constant constant = cpg.getConstant(2);

    // MethodGen mg = new MethodGen(m, cg.getClassName(), cpg);;
    int exitPosition = Integer.MAX_VALUE;
    for (Method m: cg.getMethods()){
      cfg.addNode(exitPosition, m, jc);
    }
    
    for (Method m : cg.getMethods()) {
      MethodGen mg = new MethodGen(m, cg.getClassName(), cpg);
      InstructionList il = mg.getInstructionList();
      InstructionHandle[] handles = il.getInstructionHandles();

      for (InstructionHandle ih : handles) {
        int position = ih.getPosition();
        cfg.addNode(position, m, jc);
        Instruction instr = ih.getInstruction();

        // adding edges to instructions/nodes
        // ignore branching instructions (jsr[_w], *switch)
        InstructionHandle instr_next = ih.getNext();

        // if statements
        BranchInstruction bi = null;

        // special instruction that redirects the program flow
        if ((instr instanceof BranchInstruction)) {
          bi = (BranchInstruction) instr;
          InstructionHandle if_ih = bi.getTarget();
          int npos = if_ih.getPosition();
          cfg.addEdge(position, m, jc, npos, m, jc);
        }

        if (instr instanceof INVOKESTATIC) {
          String s = ((INVOKESTATIC) instr).getMethodName(cpg);
          String sig = ((INVOKESTATIC) instr).getSignature(cpg);
          String tostring = instr.toString();
          int lIndex = tostring.lastIndexOf(' ');
          int index = Integer.parseInt(instr.toString().substring(
              lIndex + 1));
          Method invoke_method = null;
          if (index <= methods.length) {
            invoke_method = methods[index];
            cfg.addEdge(position, m, jc, 0, invoke_method, jc);
          }
        }

        // link to next instruction..
        if ((instr_next != null)
            && (instr.toString().indexOf("goto") == -1)
            && (instr.toString().indexOf("invoke") == -1)
            && (instr.toString().indexOf("return") == -1)) {
          int nextPosition = instr_next.getPosition();
          cfg.addEdge(position, m, jc, nextPosition, m, jc);
        }
        
        // redirect all returns to one EXIT node
        if (instr.toString().indexOf("return") != -1){
          cfg.addEdge(position, m, jc, exitPosition, m, jc);
        }
      }
    }

    return cfg;
  }

  public static void main(String[] a) throws ClassNotFoundException {
    CFG cfg = new GraphGenerator().createCFG("pset2.D");
    cfg.printDB();
  }
}
