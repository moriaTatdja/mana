package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rows_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Row extends InfoUtil {
    @Id
    @SequenceGenerator(
            name = "row_sequence",
            sequenceName = "row_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "row_sequence"
    )
    private Long rowId;

    @Column(nullable = false)
    private String rowName;


    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private Warehouse warehouse;


}
