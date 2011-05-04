


import gov.nasa.jpf.jvm.Verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BinaryTree {
	Node root;
	int size;
	
	public static void main(String args[]){
		(new BinaryTree()).filterBasedGenerator(3);
		
	}
	
	static class Node {
		Node left, right;
		String name = null; 
	}
	
	public boolean repOk(){
		if(root==null) return size == 0; // empty tree has size 0
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
	/*
	 * part b generated 164 repOk'ed results frmo 3 nodes
	 */
	public void filterBasedGenerator(int n){
		
		Verify.resetCounter(0);
		// allocate objects
		BinaryTree header = new BinaryTree();
		BinaryTree.Node[] nodes = new Node[n]; // null counts as valid 'node'
		int thisNode = Verify.getInt(0, n-1);
		for (int i = 0; i<nodes.length; i++){
			nodes[i] = new Node();
			nodes[i].name = Character.toString((char)('A' + i));
		}
		
		// set field domain
		ArrayList<Node> nodeDomain = new ArrayList<Node>();
		nodeDomain.add(null);
		for (Node node: nodes){
			nodeDomain.add(node);
		}
		
		// assign field values non-deterministically
		int maxIndex = nodeDomain.size() - 1;
		header.root = nodeDomain.get(Verify.getInt(0, maxIndex));
		header.size = Verify.getInt(0, n);
		nodes[thisNode].left  = nodeDomain.get(Verify.getInt(0, maxIndex));
		nodes[thisNode].right = nodeDomain.get(Verify.getInt(0, maxIndex));
		
		
		// run repOk to check validity and output if valid
		
		if (header.repOk()){
			System.out.println(Verify.getCounter(0));
			System.out.println("root\tsize");
			if (header.root == null){
				System.out.println("null" + "\t" + header.size);
			} else {
				System.out.println(header.root.name + "\t" + header.size);
			}
			
			
			System.out.println("node\tleft\tright");
			for (Node aa: nodes){
				System.out.print(aa.name + "\t");
				if (aa.left!= null){
					System.out.print(aa.left.name);
				} else {
					System.out.print("null");
				}
				System.out.print("\t");
				if (aa.right!= null){
					System.out.print(aa.right.name);
				} else {
					System.out.print("null");
				}
				System.out.println();

			}
			Verify.incrementCounter(0);
		}
	}
}


