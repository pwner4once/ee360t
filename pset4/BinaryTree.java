package pset4;


import gov.nasa.jpf.jvm.Verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BinaryTree {
	Node root;
	int size;
	
	static class Node {
		Node left, right;
	}
	
	public boolean repOk(){
		if(root==null) return size ==0; // empty tree has size 0
		Set<Node> visited = new HashSet<Node>(); visited.add(root);
		List<Node> workList = new LinkedList<Node>(); workList.add(root);
		while(!workList.isEmpty()){
			Node current = (Node) workList.remove(0); 
			if (current.left != null){
				if (!visited.add(current.left)) return false; // acyclicity
				workList.add(current.left);
			}
			if (current.right != null){
				if (!visited.add(current.right)) return false; // acyclicity
				workList.add(current.right);
			}
		}
		
		if (visited.size() != size) return false; // consistency of size
		return true;
		
	}
	
	void filterBasedGenerator(int n){
		
		// allocate objects
		BinaryTree l = new BinaryTree();
		BinaryTree.Node[] nodes = new Node[n]; // null counts as valid 'node'
		int thisNode = Verify.getInt(0, n-1);
		nodes[thisNode] = new Node();
		
		// set field domain
		ArrayList<Node> nodeDomain = new ArrayList<Node>();
		nodeDomain.add(null);
		for (Node node: nodes){
			nodeDomain.add(node);
		}
 
		// assign field values non-deterministically
		l.root = nodeDomain.get(Verify.getInt(0, n+1));
		l.size = Verify.getInt(0, n);
		nodes[thisNode].left  = nodeDomain.get(Verify.getInt(0, n+1));
		nodes[thisNode].right = nodeDomain.get(Verify.getInt(0, n+1));
		
		
		// run repOk to check validity and output if valid
		if (l.repOk()){
			// print out valid nodes?
		}
	}
}


