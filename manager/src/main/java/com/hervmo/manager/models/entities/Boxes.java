package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "boxes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boxes extends InfoUtil {

    @SequenceGenerator(
            name = "box_sequence",
            sequenceName = "box_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "box_sequence"
    )
    @Id
    private Long boxId;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private Warehouse warehouse;
    // @Todo refactoring for table  move articleList and Boxlist into a  Tabelle. customer Aricle are always in box therefore

    @ManyToOne(cascade = CascadeType.ALL)
    private ColumnOfRow columnOfRowId;
    @OneToMany(
            mappedBy = "boxId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BoxArticlesMap> articleList = new ArrayList<>();
    @Column(name = "box_name", nullable = false)
    private String boxName;

    @ManyToOne
    private States boxStatus;

}
