package com.seres.kgserver.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "serve")
public class Serve {
    private String startYear;
    private String endYear;
}
