package com.revature.bankapp.daos;

import java.util.List;

public interface Crudable<T> { // generic <T> we can use to assign as
    // Generics help insure type safety

    // Create
    T create(T newObject);

    //Read
    List<T> findAll();
    T findById(String id);

    // Update
    boolean update(T updatedObject);

    //Delete
    boolean delete(String id);

}
