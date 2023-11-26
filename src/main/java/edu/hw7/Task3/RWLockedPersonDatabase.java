package edu.hw7.Task3;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockedPersonDatabase extends PersonDatabaseImpl {

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        rwl.writeLock().lock();
        super.add(person);
        rwl.writeLock().unlock();
    }

    @Override
    public void delete(int id) {
        rwl.writeLock().lock();
        super.delete(id);
        rwl.writeLock().unlock();
    }

    @Override
    public List<Person> findByName(String name) {
        rwl.readLock().lock();
        var result = super.findByName(name);
        rwl.readLock().unlock();
        return result;
    }

    @Override
    public List<Person> findByAddress(String address) {
        rwl.readLock().lock();
        var result = super.findByAddress(address);
        rwl.readLock().unlock();
        return result;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        rwl.readLock().lock();
        var result = super.findByPhone(phone);
        rwl.readLock().unlock();
        return result;
    }

}
