package dmeyers.engine;

import java.util.*;
import cs195n.Vec2i;

public class Graph {

	Vec2i[] directions = {new Vec2i(1,0), new Vec2i(-1,0), new Vec2i(0,1), new Vec2i(0,-1)};

	protected HashMap<Vec2i,Node> allNodes = new HashMap<Vec2i,Node>();
	
	
	public Graph(Vec2i[] connections) {
		directions = connections;
	}
	
	public void addNode(Vec2i loc, float cost){
		allNodes.put(loc, new Node(loc, cost));
	}

	public void updateGraph(Vec2i goal){

		for (Node n : allNodes.values()) {
			n.neighbors=  new ArrayList<Node>();
			n.g = Float.POSITIVE_INFINITY;
			n.visited = false;
			Vec2i thisLoc = n.location;
	        if (n.h != Float.POSITIVE_INFINITY) n.h = (float) Math.sqrt(((goal.x - thisLoc.x)*(goal.x - thisLoc.x))+((goal.y - thisLoc.y)*(goal.y - thisLoc.y)));
		}
	}
	
	public static Vec2i pollNextLocationFromList(ArrayList<Node> al){
		if (al.isEmpty()) return null;
		Node last = al.get(al.size() -1);
		al.remove(al.size() - 1);
		return last.location;
	}
	

	public static Vec2i peekNextLocationFromList(ArrayList<Node> al) {
		if (al.isEmpty()) return null;
		Node last = al.get(al.size() -1);
		return last.location;
	}
	
	public Vec2i getNextDirection(Vec2i start, Vec2i goal) {
		ArrayList<Node> path = AStar(start,goal);
		Node firstStep = path.get(path.size() - 2);
		
		return firstStep.location.minus(start);
	}
	
	public Vec2i getNextLocation(Vec2i start, Vec2i goal) {
		ArrayList<Node> path = AStar(start,goal);
		return path.get(path.size() - 2).location;
	}
	
	public ArrayList<Node> AStar(Vec2i start, Vec2i goal){
		
		if (start.x == goal.x && start.y == goal.y) {
			return null;
		}
		
		updateGraph(goal);
				
		HashMap<Node,Node> past = new HashMap<Node,Node>();
		
		PriorityQueue<Node> open = new PriorityQueue<Node>();
		open.add(allNodes.get(start));
		

		Node current = open.peek();
		current.g = 0;

		while (!open.isEmpty()){
				current = open.poll();

			if (current.location.equals(goal))  {
				return pathFind(past, current);
			}
			current.visited = true;
			current.getNeighbors();
			for (Node n : current.neighbors){
				float gNew = current.g + n.cost;
				if (n.visited && gNew >= n.g) break;
				else if (!n.visited && gNew < n.g){
					past.put(n,current);
					n.g = gNew;
					if (!open.contains(n)) open.add(n);
					else {
						open.remove(n);
						open.add(n);
					}
				}				
			}
		}
		
		try {
			throw new AStarException("A* didnt finish.");
		} catch (AStarException e) {
			System.out.println(e.getMessage());
		}
				
		return pathFind(past, current);
		
	}
	
	public ArrayList<Node> pathFind(HashMap<Node,Node> map, Node latest){
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(latest);

		while (map.get(latest) != null) {
			latest = map.get(latest);
			path.add(latest);
		}
		
		
		return path;
	}
	
	public Node getNode(Vec2i vec){
		return allNodes.get(vec);
		
	}
	
	public class Node implements Comparable<Node>{
		
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		public Vec2i location;
		
		 float cost;
		
		public float h;
		
		float g = Float.POSITIVE_INFINITY;
		
		boolean visited = false;


		public Node(Vec2i vec2i, float c){
			this.location = vec2i;
			this.cost = c;
		}

		
		public ArrayList<Node> getNeighbors(){
			for (int i = 0; i < directions.length; i++){
				Node n = allNodes.get(directions[i].plus(location));

				if (n != null && n.visited == false && n.cost != Float.POSITIVE_INFINITY){
					this.neighbors.add(n);
				}
			}
			return neighbors;
		}
		
		@Override
		public int compareTo(Node o) {
			return (int) ((this.g + this.h) - (o.g + o.h));
		}
		
		@Override
	    public boolean equals(Object obj) {
	        if (obj == null) return false;
	        if (obj == this) return true;
	        if (!(obj instanceof Node)) return false;

	        Node comparing = (Node) obj;
	        
	        return (comparing.location.x == this.location.x && comparing.location.y == this.location.y);
	    }

		@Override
		public String toString(){
			return this.location.toString();
		}
	}

	public void setNodeCost(Vec2i location, float cost) {
		allNodes.get(location).cost = cost;
	}

	public void setNodeH(Vec2i location, float h) {
		allNodes.get(location).h = h;
		
	}


}
