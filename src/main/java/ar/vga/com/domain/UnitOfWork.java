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

    public static void setCurrent() {
        setCurrent(new UnitOfWork());
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
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
        if (DomainObjectsRegistry
                .getDomainObjectsRegistry(domainObject.getClass())
                .get(domainObject.getId()) != null) {
            DomainObjectsRegistry
                    .getDomainObjectsRegistry(domainObject.getClass())
                    .replace(domainObject.getId(), domainObject);
        }
        if (!dirtyObjects.contains(domainObject) && newObjects.contains(domainObject)) {
            this.dirtyObjects.add(domainObject);
        }
    }

    public void registerRemoved(DomainObject domainObject) {
        if (newObjects.remove(domainObject)) {
            return;
        }
        if (DomainObjectsRegistry
                .getDomainObjectsRegistry(domainObject.getClass())
                .get(domainObject.getId()) != null) {
            DomainObjectsRegistry
                    .getDomainObjectsRegistry(domainObject.getClass())
                    .remove(domainObject);
        }
        dirtyObjects.remove(domainObject);
        this.removedObjects.add(domainObject);
        if (!removedObjects.contains(domainObject)) {
            removedObjects.add(domainObject);
        }
    }

    public void registerClean(DomainObject domainObject) {
        if (DomainObjectsRegistry
                .getDomainObjectsRegistry(domainObject.getClass()).get(domainObject.getId()) == null) {
            DomainObjectsRegistry
                    .getDomainObjectsRegistry(domainObject.getClass()).put(domainObject.getId(), domainObject);
        }
    }

    public void commit() {
        insertNew();
        udpateDirty();
        deleteRemoved();
    }

    private void insertNew() {
        newObjects.stream().forEach(domainObject -> {
            MapperRegistry.get(domainObject.getClass()).insert(domainObject);
        });
    }

    private void udpateDirty() {
        newObjects.stream().forEach(domainObject -> {
            MapperRegistry.get(domainObject.getClass()).update(domainObject);
        });
    }

    private void deleteRemoved() {
        newObjects.stream().forEach(domainObject -> {
            MapperRegistry.get(domainObject.getClass()).delete(domainObject.getId());
        });
    }
}