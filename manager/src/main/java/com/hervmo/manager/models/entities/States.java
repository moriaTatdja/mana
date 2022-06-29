package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "states")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class States extends InfoUtil {
    @Id
    @SequenceGenerator(
            name = "state_sequence",
            sequenceName = "state_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "state_sequence"
    )
    private Long stateId;

    @Column(nullable = false)
    private String stateName;

}
