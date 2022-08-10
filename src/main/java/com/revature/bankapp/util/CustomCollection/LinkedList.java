package com.revature.bankapp.util.CustomCollection;

public class LinkedList<E> implements List<E> { //implements requires this class to use and provide concrete implementations from the interface List and Collection since List extended Collection
    private int size; // this will be the size or length of the LinkedList
    private Node<E> head; // the Node class is defined below, this creates an attribute that is the object type Node called head.
    private Node<E> tail; // this node is last in linked list

    private static class Node<T> {
        T data;
        Node<T> nextNode; // this holds the nextNode

        public Node(T data) {
            this.data = data;
        } //This constructor sets the data attribute
    }

    @Override
    public boolean add(E element) { // this method  takes in an element

        if (element == null)
            return false; // this prevents adding a null element which also prevents the following code from executing

        Node<E> newNode = new Node<>(element);
        if (head == null) tail = head = newNode; // makes sure the tail becomes the newNode
        else {
            tail.nextNode = newNode;
            tail = newNode;
        }
        size++; //increase by one since adding a new node to the LinkedList
        return true;
    }

    @Override
    public boolean contains(E element) {
        Node<E> runner = head; // create a Node called runner, set it to head

        while (runner != null) { // keeps going until it finds null or no match resulted
            if (runner.data.equals(element))
                return true;

            runner = runner.nextNode;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(E element) {
        Node<E> prevNode = null;
        Node<E> currentNode = head;

        if (size == 0 || element == null) return false;

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
            prevNode = currentNode; // this re-assignment must come first so the currentNode is saved as the previous
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) { //first check if the index we are looking for is negative or bigger than or equal to size.
            throw new IndexOutOfBoundsException(); //only if above statement is true
        }

        Node<E> currentNode = head;
        for (int i = 0; i <= index; i++) { //
            // keep moving through the LinkedList to the next node an index amount of times
            if (i == index) {
                return currentNode.data; // once reached the index ammt end, returns the data
            }
            currentNode = currentNode.nextNode;
        }

        return null;
    }


}