package com.revature.bankapp.daos;

import java.lang.reflect.Member;
import java.util.List;

public interface Crudable<T> { // generic <T> we can use to assign as
    // Generics help insure type safety

    // Create
    T create(T newObject);

    //Read
    List<Member> findAll();
    T findById(String id);

    // Update
    boolean update(T updatedObject);

    //Delete
    boolean delete(String id);

}
