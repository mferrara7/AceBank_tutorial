package com.revature.bankapp.util.CustomCollection;

public class LinkedList<E> implements List<E> { //implements requires this class to use and provide concrete implementations from the interface List and Collection since List extended Collection
    private int size; // this will be the size or length of the LinkedList
    private Node<E> head; // the Node class is defined below, this creates an attribute that is the object type Node called head.
    private Node<E> tail; // this node is the last node in the linked list

    private static class Node<T> { // this class only lives in LinkedList since it will only be used here
        T data;
        Node<T> nextNode; // this holds the nextNode

        public Node(T data) {
            this.data = data;
        } //This constructor sets the data attribute to whatever is inputted as data.
    }

    @Override
    public boolean add(E element) { // this method  takes in an element, da

        if (element == null)
            return false; // this prevents adding a null element which also prevents the following code from executing

        Node<E> newNode = new Node<>(element); // creates a new Node called newNode with the element input which immediately becomes its data attribute.
        if (head == null) tail = head = newNode; // if no head exists set head and tail to newNode
        else {
            tail.nextNode = newNode; // current tail now has a nextNode which is this newNode we are adding to the LikedList
            tail = newNode; // the tail is now reassigned to be the newNode as it is now at the end of the LinkedList
        }
        size++; //increase the size by one since we have successfully  added a new node to the LinkedList
        return true;
    }

    @Override
    public boolean contains(E element) {
        Node<E> runner = head; // create a Node called runner, set it to head

        while (runner != null) { // until it finds null, which could happen if the LinkedList is empty or reaches the end without finding a match.
            if (runner.data.equals(element))
                return true; // if the runners's data equals the element which is the input, return True

            runner = runner.nextNode;// otherwise the runner is reassigned to be the current runner's next node.
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    } //returns the boolean True if the size of the LinkedList is 0, otherwise false

    @Override
    public boolean remove(E element) {
        Node<E> prevNode = null;
        Node<E> currentNode = head;

        if (size == 0 || element == null) return false;

        // parse the linkedlist to find the right one, how? while currentNode != null, or forLoop of size
        // currentNode == null

        for (int i = 0; i < size; i++) {
            if (currentNode.data != null && currentNode.data.equals(element)) {
                if (currentNode == head) {
                    head = currentNode.nextNode; // replace the head that will be removed with the next head in the linked list
                } else if (currentNode == tail) {
                    tail = prevNode;
                    prevNode.nextNode = null;
                } else {
                    prevNode.nextNode = currentNode.nextNode;
                }
                size--;
                return true;
            }
            prevNode = currentNode; // this re-assignment must come first so the currentNode is save as the previous
            currentNode = currentNode.nextNode; // then we move onto the next node
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    } // literally returns the size of the LinkedList

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) { //first check if the index we are looking for is negative or bigger than or equal to size.
            throw new IndexOutOfBoundsException(); //throw this exception if the above condition is true
        }

        Node<E> currentNode = head; // create a new node called currentNode and set it equal to head node
        for (int i = 0; i <= index; i++) { // keep traversing the LinkedList to the next node an index amount of times
            if (i == index) {
                return currentNode.data; // if we reach the index amount, return that nodes data
            }
            // the currentNode is reassigned to the currentNode's nextNode, this is akin to jumping to the nextNode in the LinkedList
            currentNode = currentNode.nextNode;
        }

        return null;
    }


}