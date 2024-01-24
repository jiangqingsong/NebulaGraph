package com.seres.kgserver.config;

import org.nebula.contrib.ngbatis.PkGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomPkGenerator implements PkGenerator {
    @Override
    public <T> T generate(String tagName, Class<T> pkType) {
        Object id = null; // 此处自行对 ID 进行设值。
        return (T) id;
    }
}
