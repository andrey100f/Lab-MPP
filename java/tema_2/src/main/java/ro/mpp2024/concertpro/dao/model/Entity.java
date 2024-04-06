package ro.mpp2024.concertpro.dao.model;

public interface Entity<ID> {
    ID getId();
    void setId(ID id);
}
