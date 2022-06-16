package com.linwu.nacos.model.req;

import lombok.Data;

@Data
public class TestConfigBean extends BaseConfigBean {
    private int intVal;
    private Integer IntegerVal;

    private String stringVal;

    private long longVal;
    private Long upLongVal;

    private Float upFloatVal;
    private float floatVal;

    private double doubleVal;
    private Double upDoubleVal;

    private boolean booleanVal;
    private Boolean upBooleanVal;
}
