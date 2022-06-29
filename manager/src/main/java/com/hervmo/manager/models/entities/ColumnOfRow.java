package com.hervmo.manager.models.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "columnOfRow")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColumnOfRow extends InfoUtil {
    @Id
    @SequenceGenerator(
            name = "column_of_row_sequence",
            sequenceName = "column_of_row_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "column_of_row_sequence"
    )
    private Long columnOfRowId;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private Row rowId;
    private String designation;

}
