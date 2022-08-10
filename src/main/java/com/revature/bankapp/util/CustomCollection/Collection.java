package com.revature.bankapp.util.CustomCollection;

public interface Collection<E> {// e for element, a generic type since elements in our list and linked list

    boolean add(E element);// these methods are an interface class, overrided later and the body will be filled.

    boolean contains(E element);

    boolean isEmpty();

    boolean remove(E element);

    int size();

}
