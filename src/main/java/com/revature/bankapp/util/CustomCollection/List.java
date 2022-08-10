package com.revature.bankapp.util.CustomCollection;

public interface List<E> extends Collection<E> {
    // List extends Collections interface, Same as collection, but adds this new method get below

    E get(int index);

}
