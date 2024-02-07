package com.seres.kgserver.pojo.demo_basketballplayer;

import lombok.Data;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "team")
@Space(name = "demo_basketballplayer")
public class Team {
    @Id
    private String name;
}
