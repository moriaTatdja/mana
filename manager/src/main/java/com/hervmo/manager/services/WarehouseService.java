package com.hervmo.manager.services;

import com.hervmo.manager.models.entities.*;
import com.hervmo.manager.models.entities.models.WarehouseGenerateModel;

import java.util.List;

public interface WarehouseService {
    BoxArticlesMap addArticleInbox(Articles a, Boxes b, int quantity);

    BoxArticlesMap removeArticleInbox(Articles a, Boxes b, int quantity);

    Warehouse addWarehouse(WarehouseGenerateModel warehouseGenerateModel);

    List<Row> generatedRow(Warehouse wr, WarehouseGenerateModel warehouseGenerateModel, String separator);

    List<ColumnOfRow> generatedColumnOfRow(Row r, WarehouseGenerateModel warehouseGenerateModel, String separator);

    void generatedBoxes(ColumnOfRow columnOfRow, WarehouseGenerateModel warehouseGenerateModel, String separator);

    List<Warehouse> getWarehouse(Long id);

    Boxes addBoxToWarehouse(Boxes b, Warehouse wr);

    List<Boxes> getBoxes(Long boxId, Long customerId);

}
