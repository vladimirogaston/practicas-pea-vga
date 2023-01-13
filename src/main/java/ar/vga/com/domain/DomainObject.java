package ar.vga.com.domain;

public class DomainObject {
    private Long id;

    public DomainObject(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected void markNew() {
        UnitOfWork.getCurrent().registerNew(this);
    }

    protected void markClean() {
        UnitOfWork.getCurrent().registerClean(this);
    }

    protected void markDirty() {
        UnitOfWork.getCurrent().registerDirty(this);
    }

    protected void markRemoved() {
        UnitOfWork.getCurrent().registerRemoved(this);
    }
}
