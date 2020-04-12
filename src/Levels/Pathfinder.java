package Levels;

import java.awt.Point;
import java.util.ArrayList;

import Buildings.Building;
import Utility.Utility;

public class Pathfinder {
	private static final int NODE_NUMBER = 2;
	private static final int TAKEN_NUMBER = 1;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private Point startPoint, endPoint;
	private Node firstNode, finalNode;
	private int[][] buildingMap;
	
	public Pathfinder(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public ArrayList<Point> getPath(int[][] buildingMap) {
		ArrayList<Point> path = new ArrayList<Point>();
		this.buildingMap = Utility.copyArray(buildingMap);
		nodes.clear();
		firstNode = finalNode = null;
		getNodes();
		try {
		getDistances();
		} catch (NullPointerException e) {
			return path;
		}

		Node parent = finalNode;
		
		while (parent != null) {
			path.add(0, new Point(parent.x*Building.SIZE+Building.SIZE/2, parent.y*Building.SIZE+Building.SIZE/2));
			parent = parent.parentNode;
		}

		return path;
	}
	
	public void getNodes() {
		for (int y = 0; y < buildingMap[0].length; y++) {
			for (int x = 0; x < buildingMap.length; x++) {
				if (isNode(x, y)) {
					if (x == startPoint.x && y == startPoint.y) {
						firstNode = new Node(startPoint, 0);
						nodes.add(firstNode);
					} else if (x == endPoint.x && y == endPoint.y) {
						finalNode = new Node(endPoint, -1);
						nodes.add(finalNode);
					} else {
						nodes.add(new Node(x, y, -1));
					}
					buildingMap[x][y] = NODE_NUMBER;
				}
			}
		}
	}
	
	private ArrayList<Node> getNeighbours(Node inputNode) throws NullPointerException {
		ArrayList<Node> neighbours = new ArrayList<Node>();

		for (Node node : nodes) {
			if (inputNode.equals(node)) {
				continue;
			}

			if (node.x == inputNode.x) {
				if (Math.abs(node.y - inputNode.y) == 1) {
					neighbours.add(node);
				}

				for (int y = Math.min(node.y, inputNode.y) + 1; y < Math.max(node.y, inputNode.y); y++) {
					if (buildingMap[node.x][y] == TAKEN_NUMBER || buildingMap[node.x][y] == NODE_NUMBER) {
						break;
					}
					if (y == Math.max(node.y, inputNode.y) - 1) {
						neighbours.add(node);
					}
				}

			} else if (node.y == inputNode.y) {
				if (Math.abs(node.x - inputNode.x) == 1) {
					neighbours.add(node);
				}

				for (int x = Math.min(node.x, inputNode.x) + 1; x < Math.max(node.x, inputNode.x); x++) {
					if (buildingMap[x][node.y] == TAKEN_NUMBER || buildingMap[x][node.y] == NODE_NUMBER) {
						break;
					}
					if (x == Math.max(node.x, inputNode.x) - 1) {
						neighbours.add(node);
					}
				}
			}
		}

		return neighbours;
	}

	private void getDistances() throws NullPointerException {
		ArrayList<Node> unvisited = new ArrayList<Node>(nodes);
		Node currentNode = firstNode;

		while (!unvisited.isEmpty()) {

			for (Node nei : getNeighbours(currentNode)) {

				if (!unvisited.contains(nei)) {
					currentNode.parentNode = nei;
					continue;
				}

				int distance = currentNode.distance
						+ Math.max(Math.abs(nei.x - currentNode.x), Math.abs(nei.y - currentNode.y));
				if (distance < nei.distance || nei.distance < 0) {
					nei.distance = distance;
				}
			}

			unvisited.remove(currentNode);

			if (currentNode == finalNode) {
				break;
			}

			Node temp = null;
			for (Node unvisitedNode : unvisited) {
				if (unvisitedNode.distance == -1) {
					continue;
				}

				if (temp == null) {
					temp = unvisitedNode;
					continue;
				}

				if (unvisitedNode.distance < temp.distance) {
					temp = unvisitedNode;
				}
			}
			if (temp == null) {
				break;
			}
			currentNode = temp;
		}
	}
	
	private boolean isNode(int mapX, int mapY) {
		if (isOccupied(mapX, mapY)) {
			return false;
		}
		
		if ((mapX == startPoint.x && mapY == startPoint.y) || (mapX == endPoint.x && mapY == endPoint.y)) {
			return true;
		}

		int count = 0;

		if (isOccupied(mapX-1, mapY)) {
			count++;
		}

		if (isOccupied(mapX+1, mapY)) {
			count++;
		}

		if (isOccupied(mapX, mapY+1)) {
			count++;
		}

		if (isOccupied(mapX, mapY-1)) {
			count++;
		}

		if (count == 3) {
			return true;
		}

		if (!isOccupied(mapX-1, mapY) && !isOccupied(mapX, mapY-1)) {
			return true;
		}
		
		if (!isOccupied(mapX-1, mapY) && !isOccupied(mapX, mapY+1)) {
			return true;
		}
		
		if (!isOccupied(mapX+1, mapY) && !isOccupied(mapX, mapY-1)) {
			return true;
		}
		
		if (!isOccupied(mapX+1, mapY) && !isOccupied(mapX, mapY+1)) {
			return true;
		}

		return false;
	}
	
	public boolean isOccupied(int mapX, int mapY) {
		try {
		if (buildingMap[mapX][mapY] == TAKEN_NUMBER) {
			return true;
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
		
		return false;
	}
	
	static class Node {
		private int x, y, distance;
		private Node parentNode;

		public Node(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
			parentNode = null;
		}
		
		public Node(Point point, int distance) {
			this(point.x, point.y, distance);
		}
		
		public String toString() {
			return x + " " + y + " with distance " + distance;
		}

	}

}
