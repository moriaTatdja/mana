package com.hervmo.manager.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "warehouse")
@Data
public class Warehouse extends InfoUtil {
    @Id
    @SequenceGenerator(
            name = "warehouse_sequence",
            sequenceName = "warehouse_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "warehouse_sequence"
    )
    @Column(nullable = false)
    private Long warehouseId;
    @Column(nullable = false)
    private String warehouseName;
    @Column(nullable = false)
    private String warehouseType;

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouse_id=" + warehouseId +
                ", warehouse_name='" + warehouseName + '\'' +
                ", active_warehouse=" + active +
                ", warehouse_type='" + warehouseType + '\'' +
                ", warehouse_created_at=" + createdAt +
                ", warehouse_update_at=" + updateAt +
                ", warehouse_update_by='" + updateBy + '\'' +
                '}';
    }
}
