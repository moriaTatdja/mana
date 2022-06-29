package com.hervmo.manager.services.imp;

import com.hervmo.manager.events.warehouse.WarehouseAddArticleInBoxEvent;
import com.hervmo.manager.models.entities.*;
import com.hervmo.manager.models.entities.models.RowType;
import com.hervmo.manager.models.entities.models.WarehouseGenerateModel;
import com.hervmo.manager.models.repositories.*;
import com.hervmo.manager.services.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarehouseServiceImp implements WarehouseService {

    @Autowired
    protected ApplicationEventPublisher publisher;
    @Autowired
    protected WarehouseRepo warehouseRepo;
    @Autowired
    protected BoxesRepository boxesRepository;
    @Autowired
    protected ColumnOfRowRepository columnOfRowRepository;
    @Autowired
    protected RowRepository rowRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected BoxArticlesMapRepository boxArticlesMapRepository;


    @Override
    public BoxArticlesMap addArticleInbox(Articles a, Boxes b, int quantity) {
        try {
            if (a != null && b != null) {
                BoxArticlesMap boxArticle = boxArticlesMapRepository.findByArticleIdAndBoxId(a, b).orElse(new BoxArticlesMap());
                boxArticle.setArticleId(a);
                boxArticle.setBoxId(b);
                boxArticle.setQuantity(quantity);
                boxArticle = boxArticlesMapRepository.saveAndFlush(boxArticle);
                publisher.publishEvent(new WarehouseAddArticleInBoxEvent(a, b, boxArticle));
                return boxArticle;
            }
        } catch (Exception exception) {
            log.error("error while save box Article", exception);
        }
        return null;
    }

    @Override
    public BoxArticlesMap removeArticleInbox(Articles a, Boxes b, int quantity) {
        Optional<BoxArticlesMap> boxArticlesMap = boxArticlesMapRepository.findByArticleIdAndBoxId(a, b);
        if (boxArticlesMap.isPresent()) {
            if (quantity < boxArticlesMap.get().getQuantity()) {
                boxArticlesMap.get().setQuantity(boxArticlesMap.get().getQuantity() - quantity);
                return boxArticlesMapRepository.saveAndFlush(boxArticlesMap.get());
            }
            if (boxArticlesMap.get().getQuantity() == quantity) {
                boxArticlesMapRepository.delete(boxArticlesMap.get());
            }
        }
        return null;
    }

    @Override
    public Warehouse addWarehouse(WarehouseGenerateModel warehouseGenerateModel) {
        try {
            Warehouse ws = this.getWarehouseMappedToWarehouseGeneratedModel(warehouseGenerateModel);
            String separator = "-";
            ws.setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            ws = warehouseRepo.saveAndFlush(ws);// save(ws);
            ArrayList<Row> listOfRow = generatedRow(ws, warehouseGenerateModel, separator);
            for (Row row : listOfRow) {
                List<ColumnOfRow> listOfColumns = generatedColumnOfRow(row, warehouseGenerateModel, separator);
                for (ColumnOfRow cl : listOfColumns) {
                    generatedBoxes(cl, warehouseGenerateModel, separator);
                }
            }
            return ws;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }

        return null;
    }

    @Override
    public ArrayList<Row> generatedRow(Warehouse wr, WarehouseGenerateModel warehouseGenerateModel, String separator) {
        ArrayList<Row> listOfRows = new ArrayList<>();
        ArrayList<String> designtation = generateRowDesignation(warehouseGenerateModel);
        int i = 1;
        for (String designtationItem : designtation) {
            String rowName = designtationItem + separator + i;
            Row row = new Row();
            row.setRowName(rowName);
            row.setCreatedAt(new Date());
            row.setActive(true);
            row.setWarehouse(wr);
            row = rowRepository.saveAndFlush(row);
            i++;
            listOfRows.add(row);
        }
        return listOfRows;
    }

    @Override
    public List<ColumnOfRow> generatedColumnOfRow(Row r, WarehouseGenerateModel warehouseGenerateModel, String separator) {
        ArrayList<ColumnOfRow> listOfColumn = new ArrayList<>();
        for (int column = 1; column <= warehouseGenerateModel.getNumberOfColumn(); column++) {
            String columnName = r.getRowName() + separator + column;
            ColumnOfRow columnOfRow = new ColumnOfRow();
            columnOfRow.setDesignation(columnName);
            columnOfRow.setActive(true);
            columnOfRow.setCreatedAt(new Date());
            columnOfRow.setRowId(r);
            columnOfRow = columnOfRowRepository.saveAndFlush(columnOfRow);
            listOfColumn.add(columnOfRow);
        }
        return listOfColumn;
    }

    @Override
    public void generatedBoxes(ColumnOfRow columnOfRow, WarehouseGenerateModel warehouseGenerateModel, String separator) {
        for (int box = 1; box <= warehouseGenerateModel.getNumberOfBox(); box++) {
            Boxes boxes = new Boxes();
            boxes.setBoxName(columnOfRow.getDesignation() + separator + box);
            boxes.setColumnOfRowId(columnOfRow);
            boxes.setActive(true);
            boxes.setCreatedAt(new Date());
            boxes.setWarehouse(columnOfRow.getRowId().getWarehouse());
            boxesRepository.save(boxes);
        }
    }

    @Override
    public List<Warehouse> getWarehouse(Long id) {
        if (id == 0) {
            return warehouseRepo.findAll();
        }
        List<Warehouse> list = new ArrayList<>();
        Optional<Warehouse> op = warehouseRepo.findById(id);
        op.ifPresent(list::add);
        return list;
    }

    @Override
    public Boxes addBoxToWarehouse(Boxes b, Warehouse wr) {
        if (b != null) {


            if (wr.getWarehouseId() == null) {
                wr = warehouseRepo.saveAndFlush(wr);
            }
            b.setWarehouse(wr);
            return boxesRepository.saveAndFlush(b);
        }
        return null;
    }

    @Override
    public List<Boxes> getBoxes(Long boxId, Long customerId) {
        Set<Boxes> listOfBox = new HashSet<>();
        if (customerId != null) {
            customerRepository.findById(customerId).ifPresent(cs -> listOfBox.addAll(cs.getBoxesList()));
        }
        if (boxId != null) {
            if (boxId == 0) {
                listOfBox.addAll(boxesRepository.findAll());
            } else {
                listOfBox.add(boxesRepository.findById(boxId).orElse(null));
            }
        }
        return new ArrayList<>(listOfBox);
    }

    private Warehouse getWarehouseMappedToWarehouseGeneratedModel(WarehouseGenerateModel wgm) {
        Warehouse ws = new Warehouse();
        ws.setWarehouseType("WAREHOUSE");
        ws.setWarehouseName(wgm.getWarehouseName());
        ws.setActive(wgm.getActiveWarehouse());
        ws.setCreatedAt(new Date());
        return ws;
    }

    private ArrayList<String> generateRowDesignation(WarehouseGenerateModel wgm) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> designation = new ArrayList<String>();
        if (wgm.getRowTypeName() == RowType.ALPHABETICAL) {
            List<String> alphabet = new ArrayList<String>();
            for (char letter = 'A'; letter <= 'Z'; ++letter) {
                alphabet.add(letter + "");
            }

            String startLetter = "";
            int startIndex = 0;
            for (int i = 0; i < wgm.getNumberOfRow(); i++) {

                if (startIndex < (alphabet.size() - 1)) {
                    startIndex = 0;
                }
                if (i != 0 && i % alphabet.size() == 0) {
                    int index = (i / alphabet.size());
                    index = index > 0 ? index - 1 : index;
                    startLetter = designation.get(index);
                }
                designation.add(startLetter + alphabet.get(startIndex));
                sb.append(" # ");
                sb.append(startLetter);
                sb.append(alphabet.get(startIndex));
                sb.append(" #");
                startIndex++;
            }
        } else {
            for (int i = 1; i <= wgm.getNumberOfRow(); i++) {
                designation.add(i + "");

            }
        }
        log.info(sb.toString(), "designation");

        return designation;

    }

}
