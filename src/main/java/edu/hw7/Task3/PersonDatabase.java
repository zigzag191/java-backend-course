package edu.hw7.Task3;

import java.util.List;

public interface PersonDatabase {

    long size();

    void add(Person person);

    void delete(int id);

    List<Person> findByName(String name);

    List<Person> findByAddress(String address);

    List<Person> findByPhone(String phone);

}