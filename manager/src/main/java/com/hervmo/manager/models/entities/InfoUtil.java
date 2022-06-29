package com.hervmo.manager.models.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InfoUtil {
    @Column(name = "created_at", nullable = false)
    protected Date createdAt;
    @Column(name = "update_at")
    protected Date updateAt;
    @Column(name = "update_by")
    protected String updateBy;
    @Column(name = "active", nullable = false, columnDefinition = "boolean default false")
    protected boolean active;

    public void setCreatedAt() {
        createdAt = new Date();
    }

    public void setUpdateAt() {
        updateAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
