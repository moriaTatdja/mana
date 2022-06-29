package com.hervmo.manager.models.entities.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseGenerateModel {
    private String warehouseName;
    private RowType rowTypeName;
    private int numberOfRow;
    private int numberOfColumn;
    private int numberOfBox;
    private boolean activeWarehouse;

    public boolean getActiveWarehouse() {
        return this.activeWarehouse;
    }
}
