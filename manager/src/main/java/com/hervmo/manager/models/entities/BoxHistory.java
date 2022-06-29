package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxHistory extends History {

    private String oldCustomerNumber;
    private String oldCustomerName;
    private String newCustomerNumber;
    private String newCustomerName;
}
