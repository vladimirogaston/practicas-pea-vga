package ar.vga.com.domain;

import ar.vga.com.mapper.ApplicationException;
import ar.vga.com.mapper.MapperRegistry;

import java.util.LinkedList;
import java.util.List;

public class UnitOfWork {
    private static ThreadLocal current = new ThreadLocal();
    private List<DomainObject> newObjects;
    private List<DomainObject> dirtyObjects;
    private List<DomainObject> removedObjects;

    public UnitOfWork() {
        newObjects = new LinkedList<>();
        dirtyObjects = new LinkedList<>();
        removedObjects = new LinkedList<>();
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    public static void newCurrent() {
        current.set(new UnitOfWork());
    }

    public static void clean() {
        current.set(null);
    }

    public void registerNew(DomainObject domainObject) {
        if (newObjects.contains(domainObject)) {
            throw new ApplicationException("Object already registered new");
        }
        if (dirtyObjects.contains(domainObject)) {
            throw new ApplicationException("Dirty object");
        }
        if (removedObjects.contains(domainObject)) {
            throw new ApplicationException("Removed object");
        }
        this.newObjects.add(domainObject);
    }

    public void registerDirty(DomainObject domainObject) {
        if (removedObjects.contains(domainObject)) {
            throw new ApplicationException("Removed object");
        }
        if(this.newObjects.contains(domainObject)) {
            this.newObjects.remove(domainObject);
        }
        if (!dirtyObjects.contains(domainObject)) {
            this.dirtyObjects.add(domainObject);
        }
    }

    public void registerRemoved(DomainObject domainObject) {
        if (newObjects.remove(domainObject)) {
            return;
        }
        dirtyObjects.remove(domainObject);
        if (!removedObjects.contains(domainObject)) {
            removedObjects.add(domainObject);
        }
    }

    public void commit() {
        insertNew();
        udpateDirty();
        deleteRemoved();
    }

    private void insertNew() {
        newObjects.stream().forEach(domainObject -> {
            MapperRegistry.getInstance().getMapper(domainObject.getClass()).insert(domainObject);
        });
    }

    private void udpateDirty() {
        dirtyObjects.stream().forEach(domainObject -> {
            MapperRegistry.getInstance().getMapper(domainObject.getClass()).update(domainObject);
        });
    }

    private void deleteRemoved() {
        removedObjects.stream().forEach(domainObject -> {
            MapperRegistry.getInstance().getMapper(domainObject.getClass()).delete(domainObject.getId());
        });
    }
}