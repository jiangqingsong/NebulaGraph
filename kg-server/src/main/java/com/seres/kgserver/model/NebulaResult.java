package com.seres.kgserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author jiangqs
 * @version 1.0
 * @date 2024/2/7 9:01
 */
@Data
public class NebulaResult<T> implements Serializable {

    private List<Error> errors;
    private List<Result<T>> results;

    @JsonIgnore
    public boolean isSuccess() {
        return (errors != null && errors.size() == 1 && errors.get(0).getCode() == 0);
    }

    @Data
    public static class Error implements Serializable {
        private int code;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result<T> implements Serializable {
        private String spaceName;
        private List<Element<T>> data;
        private List<String> columns;
        private Error errors;
        private long latencyInUs;
    }

    @Data
    public static class Element<T> implements Serializable {
        private List<Meta<T>> meta;
        private List<Serializable> row;
    }

    @Data
    public static class Meta<T> implements Serializable {
        private String type;
        private T id;
    }
}
