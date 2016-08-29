package com.github.mygreen.expression.el.tld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * カスタムタグの定義
 *
 * @since 1.5
 * @author T.TSUCHIE
 *
 */
public class Taglib implements Serializable {
    
    /** serialVersionUID */
    private static final long serialVersionUID = -1333913629208678989L;
    
    private String description;
    
    private String displayName;
    
    private String tlibVersion;
    
    private String shortName;
    
    private String uri;
    
    private List<Function> functions = new ArrayList<>();
    
    public Taglib() {
        
    }
    
    public void add(Function function) {
        this.functions.add(function);
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getTlibVersion() {
        return tlibVersion;
    }
    
    public void setTlibVersion(String tlibVersion) {
        this.tlibVersion = tlibVersion;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public List<Function> getFunctions() {
        return functions;
    }
    
    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }
}
