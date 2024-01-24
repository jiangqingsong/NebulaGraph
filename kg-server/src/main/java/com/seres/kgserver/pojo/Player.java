package com.seres.kgserver.pojo;

import lombok.Data;
import org.nebula.contrib.ngbatis.annotations.Space;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/1/24 16:04
 */
@Data
@Table(name = "player")
@Space(name = "demo_basketballplayer")
public class Player {
    @Id
    private String id;
    private String name;
    private Integer age;
}
