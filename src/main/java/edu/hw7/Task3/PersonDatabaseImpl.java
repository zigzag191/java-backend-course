package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDatabaseImpl implements PersonDatabase {

    protected final Map<Integer, Person> persons = new HashMap<>();
    protected final Map<String, List<Person>> nameIndex = new HashMap<>();
    protected final Map<String, List<Person>> addressIndex = new HashMap<>();
    protected final Map<String, List<Person>> phoneIndex = new HashMap<>();

    protected final List<Person> getFromIndex(Map<String, List<Person>> index, String value) {
        var result = index.get(value);
        if (result == null) {
            return List.of();
        }
        return Collections.unmodifiableList(result);
    }

    protected final void addToIndex(Map<String, List<Person>> index, String value, Person person) {
        index.computeIfAbsent(value, k -> new ArrayList<>()).add(person);
    }

    protected final void deleteFromIndex(Map<String, List<Person>> index, String value, Person person) {
        var indexedPersons = index.get(value);
        if (indexedPersons != null) {
            indexedPersons.remove(person);
        }
    }

    @Override
    public long size() {
        return persons.size();
    }

    @Override
    public void add(Person person) {
        persons.put(person.id(), person);
        addToIndex(nameIndex, person.name(), person);
        addToIndex(addressIndex, person.address(), person);
        addToIndex(phoneIndex, person.phoneNumber(), person);
    }

    @Override
    public void delete(int id) {
        var deletedPerson = persons.remove(id);
        if (deletedPerson != null) {
            deleteFromIndex(nameIndex, deletedPerson.name(), deletedPerson);
            deleteFromIndex(addressIndex, deletedPerson.address(), deletedPerson);
            deleteFromIndex(phoneIndex, deletedPerson.phoneNumber(), deletedPerson);
        }
    }

    @Override
    public List<Person> findByName(String name) {
        return getFromIndex(nameIndex, name);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return getFromIndex(addressIndex, address);
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return getFromIndex(phoneIndex, phone);
    }

}
