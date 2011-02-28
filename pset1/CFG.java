package pset1;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class CFG {
  public Set<Node> nodes = new HashSet<Node>();
  public Map<Node, Set<Node>> edges = new HashMap<Node, Set<Node>>();

  static class Node {
    int position;
    Method method;
    JavaClass clazz;

    Node(int p, Method m, JavaClass c) {
      position = p;
      method = m;
      clazz = c;
    }

    public boolean equals(Object o){
      if (!(o instanceof Node)) return false;
      Node n = (Node) o;

      return (position == n.position) &&
              method.equals(n.method) && clazz.equals(n.clazz);
    }

    public int hashCode(){
      return position += method.hashCode() + clazz.hashCode();
    }

  }

  public void addNode(int p, Method m, JavaClass c){
    Node node = new Node(p, m, c);
    
    if (nodes.contains(node))
    	return;
    
    // add new node to nodes.
    // stupid .contains function doesn't work??
    boolean contains = false;
    for (Object o: nodes.toArray()) {
    	Node n = (Node)o;
    	if (n.equals(node)){
    		contains = true;
    	}
	}
    
    if (!contains){
    	nodes.add(node);
    }
    
    // edges business
    if (edges.containsKey(node)){
      Set<Node> nodes = edges.get(node);
      if (nodes == null){
        nodes = new HashSet<Node>();
      }
    } else { // add new node to corresponding edges
      edges.put(node, new HashSet<Node>());
    }   
  }

  public void addEdge(int p1, Method m1, JavaClass c1,
                      int p2, Method m2, JavaClass c2){
    this.addNode(p1, m1, c1);
    this.addNode(p2, m2, c2);
    Node n = new Node(p1,m2,c1);
    
    Set<Node> sn = this.edges.get(n);
//    this.edges.get(new Node(p1,m1,c1)).add(new Node(p2,m2,c2));
  }
  
  public boolean isReachable(int p1, Method m1, JavaClass c1,
                 int p2, Method m2, JavaClass c2){
    return this.isReachable(new Node(p1, m1, c1),
                            new Node(p2, m2, c2),
                            new HashSet<Node>());
  }
  
  public boolean isReachable(Node curNode, Node targetNode, Set<Node> visitedNodes){
    // If the start node or the end node are not in the graph,
    // the method returns false.
    if (!nodes.contains(curNode) || !nodes.contains(targetNode) ||
        !edges.containsKey(curNode) || !edges.containsKey(targetNode)) {
      return false;
    }
    
    // keep track of visited nodes so I don't go in circles..
    visitedNodes.add(curNode);
    Set<Node> childrenNodes = edges.get(curNode);
    
    // base case..?
    if (childrenNodes.contains(targetNode)){
      return true; // found target node..
    }
    
    boolean hashResult = false;
    
    for(Node node: childrenNodes){
      // path-find through none-visited nodes
      if (!visitedNodes.contains(node)){
        hashResult |= isReachable(node, targetNode, visitedNodes);
      }
    }
    
    return hashResult;
  }
}
