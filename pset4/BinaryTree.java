


import gov.nasa.jpf.jvm.Verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BinaryTree {
	// variable for pruning
	int size; boolean size_accessed = false;
	int max_size = 0;
	Node header; boolean header_accessed = false;
	ArrayList<Node> nodeDomain = null;
	Node current; 
	
	public static void main(String args[]){
		//(new BinaryTree()).filterBasedGenerator(3);
		(new BinaryTree()).pruningBasedGenerator(3);
	}
	
	static class Node {
		Node left; boolean left_accessed = false;
		Node right; boolean right_accessed = false;
		String name = null; 
	}
	
	public boolean repOk(){
		if(header==null) return size == 0; // empty tree has size 0
		Set<Node> visited = new HashSet<Node>(); visited.add(header);
		List<Node> workList = new LinkedList<Node>(); workList.add(header);
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
	
	Node header(){
		if (!this.header_accessed){
			this.header_accessed = true;
			this.header = this.nodeDomain.get(Verify.getInt(0, nodeDomain.size() - 1));
		}
		
		return this.header;
	}
	
	int size(){
		if (!this.size_accessed){
			this.size_accessed = true;
			this.size = Verify.getInt(0, this.max_size);
		}
		return this.size;
	}
	
	Node left(){
		if (!this.current.left_accessed){
			this.current.left_accessed = true;
			this.current.left = nodeDomain.get(Verify.getInt(0, nodeDomain.size() - 1));
		}
		return this.current.left;
	}
	
	Node right(){
		if (!this.current.right_accessed){
			this.current.right_accessed = true;
			this.current.right = nodeDomain.get(Verify.getInt(0, nodeDomain.size() - 1));
		}
		return this.current.right;
	}
	
	public boolean repOk_pruning(){
		if(header()==null) return size() == 0; // empty tree has size 0
		Set<Node> visited = new HashSet<Node>(); visited.add(header());
		List<Node> workList = new LinkedList<Node>(); workList.add(header());
		while(!workList.isEmpty()){
			this.current = (Node) workList.remove(0); 
			if (left() != null){
				if (!visited.add(left())) return false; // acyclicity
				workList.add(left());
			}
			if (right() != null){
				if (!visited.add(right())) return false; // acyclicity
				workList.add(right());
			}
		}
		
		if (visited.size() != size()) return false; // consistency of size
		return true;
		
	}
	
	/*
	 * part b generated 164 repOk'ed results frmo 3 nodes
	 */
	public void filterBasedGenerator(int n){ 
		BinaryTree header = null;
		BinaryTree.Node[] nodes = null;
		
		Verify.resetCounter(0);
		// allocate objects
		header = new BinaryTree();
		nodes = new Node[n]; // null counts as valid 'node'
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
		header.header = nodeDomain.get(Verify.getInt(0, maxIndex));
		header.size = Verify.getInt(0, n);
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].left  = nodeDomain.get(Verify.getInt(0, maxIndex));
			nodes[i].right = nodeDomain.get(Verify.getInt(0, maxIndex));
		}

		
		// run repOk to check validity and output if valid
		//if (header.repOk()){
		if (header.repOk()){
			System.out.println("Count => " + Verify.getCounter(0));
			System.out.println("root\tsize");
			if (header.header == null){
				System.out.println("null" + "\t" + header.size);
			} else {
				System.out.println(header.header.name + "\t" + header.size);
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
	
	/*
	 * pruning based generator
	 */
	public void pruningBasedGenerator(int n){
		max_size = n;
		Verify.resetCounter(0);
		// allocate objects
		this.header = null;
		Node[] nodes = new Node[n]; // null counts as valid 'node'
		for (int i = 0; i<nodes.length; i++){
			nodes[i] = new Node();
			nodes[i].name = Character.toString((char)('A' + i));
		}
		
		// set field domain
		this.nodeDomain = new ArrayList<Node>();
		nodeDomain.add(null);
		for (Node node: nodes){
			nodeDomain.add(node);
		}
		
		// assign field values non-deterministically
		
		// run repOk to check validity and output if valid
		//if (header.repOk()){
		if (repOk_pruning()){
			System.out.println("Count => " + Verify.getCounter(0));
			System.out.println("root\tsize");
			if (header() == null){
				System.out.println("null" + "\t" + size());
			} else {
				System.out.println(header().name + "\t" + size());
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
