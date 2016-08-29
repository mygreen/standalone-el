package com.github.mygreen.expression.el.tld;

import java.io.Serializable;

/**
 * EL関数の定義。
 * 
 * @since 1.5
 * @author T.TSUCHIE
 *
 */
public class Function implements Serializable {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 4256125161276603691L;
    
    private String description;
    
    private String name;
    
    private String functionClass;
    
    private String functionSignature;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFunctionClass() {
        return functionClass;
    }
    
    public void setFunctionClass(String functionClass) {
        this.functionClass = functionClass;
    }
    
    public String getFunctionSignature() {
        return functionSignature;
    }
    
    public void setFunctionSignature(String functionSignature) {
        this.functionSignature = functionSignature;
    }
}
