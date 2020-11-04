package com.man.vin.truck.batch.file.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "vinCodes")
public class VinDocument {

    @Id
    private String vin;
    private Set<String> hardCode;
    private Set<String> softCode;


    public VinDocument(String vin){
        this.vin = vin;
        this.hardCode = new HashSet<>();
        this.softCode = new HashSet<>();
    }
    public VinDocument(){
        this.hardCode = new HashSet<>();
        this.softCode = new HashSet<>();
    }

    public void setHardCode(Set<String> hardCode) {
        this.hardCode = hardCode;
    }
    public void setSoftCode(Set<String> softCode) {
        this.softCode = softCode;
    }

    public void addHardCode(Set<String> codes){
        this.hardCode.addAll(codes);
    }

    public void addHardCode(String code){
        this.hardCode.add(code);
    }
    public void addSoftCode(Set<String> codes){
        this.softCode.addAll(codes);
    }

    public void addSoftCode(String code){
        this.softCode.add(code);
    }

    public Set<String> getHardCode() {
        return hardCode;
    }

    public Set<String> getSoftCode() {
        return softCode;
    }
}
