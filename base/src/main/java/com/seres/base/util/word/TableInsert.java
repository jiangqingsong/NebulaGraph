package com.seres.base.util.word;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInsert {
    /**
     * 某个表格需要插入的数据
     */
    private List<String[]> table = new ArrayList<>();
}
