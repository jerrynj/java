package com.neo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Base {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
