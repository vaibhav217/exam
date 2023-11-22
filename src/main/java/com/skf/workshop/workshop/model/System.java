package com.skf.workshop.workshop.model;

import java.io.Serializable;


public class System  implements Serializable {
    private static final long serialVersionUID = 0L;
    private String property;
    private String value;
    
    public System(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
