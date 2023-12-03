package edu.hw7.Task3;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockedPersonDatabase extends PersonDatabaseImpl {

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        rwl.writeLock().lock();
        try {
            super.add(person);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        rwl.writeLock().lock();
        try {
            super.delete(id);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        rwl.readLock().lock();
        List<Person> result;
        try {
            result = super.findByName(name);
        } finally {
            rwl.readLock().unlock();
        }
        return result;
    }

    @Override
    public List<Person> findByAddress(String address) {
        rwl.readLock().lock();
        List<Person> result;
        try {
            result = super.findByAddress(address);
        } finally {
            rwl.readLock().unlock();
        }
        return result;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        rwl.readLock().lock();
        List<Person> result;
        try {
            result = super.findByPhone(phone);
        } finally {
            rwl.readLock().unlock();
        }
        return result;
    }

}
