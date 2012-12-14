package bean;

import java.util.ArrayList;

import tools.WordProPair;

/**
 * 
 * 实现自动排序且有容量限制的栈用于存放临时翻译结果
 * @author LiuChunyang
 * 
 */

public class Stack {
	Node head;
	int maxSize;
	int size;
	
	private class Node {
		WordProPair text;
		Node next;
		Node(WordProPair text) {
			this.text = text;
			next = null;
		}
	}
	
	Stack(int maxSize) {
		this.maxSize = maxSize;
		head = null;
		size = 0;
	}
	
	void add(WordProPair text) {
		if(size == 0) {
			Node node = new Node(text);
			head = node;
		}
		else {
			Node node = head;
			if(text.Prob > node.text.Prob) {
				Node newNode = new Node(text);
				newNode.next = head;
				head = newNode;
			}
			else {
				while(node.next != null && node.next.text.Prob > text.Prob)
					node = node.next;
				Node newNode = new Node(text);
				newNode.next = node.next;
				node.next = newNode;
			}
		}
		size++;
		if(size > maxSize) {
			Node node = head;
			for(int i = 0; i < maxSize - 1; i++) {
				node = node.next;
			}
			node.next = null;
			size--;
		}
	}
	
	ArrayList<WordProPair> getList() {
		ArrayList<WordProPair> list = new ArrayList<WordProPair>();
		Node node = head;
		while(node != null) {
			list.add(node.text);
			node = node.next;
		}
		return list;
	}
}