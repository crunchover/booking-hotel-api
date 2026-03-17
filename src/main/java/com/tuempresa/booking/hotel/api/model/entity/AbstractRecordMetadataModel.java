package com.tuempresa.booking.hotel.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractRecordMetadataModel implements Serializable {
  @Serial private static final long serialVersionUID = 1053842443021848949L;

  @NotNull
  @Column(name = "created", columnDefinition = "TIMESTAMP")
  private LocalDateTime created;

  @Column(name = "removed", columnDefinition = "TIMESTAMP")
  private LocalDateTime removed;

  @Column(name = "updated", columnDefinition = "TIMESTAMP")
  private LocalDateTime updated;

  @PrePersist
  public void onCreated() {
    this.created = LocalDateTime.now();
    this.updated = this.created;
  }

  @PreUpdate
  public void onUpdated() {
    this.updated = LocalDateTime.now();
  }

  public @NotNull LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(@NotNull LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getRemoved() {
    return removed;
  }

  public void setRemoved(LocalDateTime removed) {
    this.removed = removed;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }
}
