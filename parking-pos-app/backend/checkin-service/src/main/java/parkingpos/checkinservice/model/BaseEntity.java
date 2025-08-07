package parkingpos.checkinservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
    @Getter
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @Getter
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at") 
    @Getter
    private ZonedDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

}
