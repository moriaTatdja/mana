package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class History {
    @Id
    @SequenceGenerator(
            name = "history_sequence",
            sequenceName = "history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "history_sequence"
    )
    private Long historyId;
    private Date changed;
    @ManyToOne
    private Users user;


}
