package com.man.vin.truck.batch.file.dataformat;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", skipFirstLine = false)
public class CsvItem {

    @DataField(pos=1)
    private String vin;
    @DataField(pos=2)
    private String code;
    private String fileName;

    public CsvItem(){};

    public CsvItem(String vin, String code){
        this.vin = vin;
        this.code = code;
    };

    public void setVin(String vin){
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public String getCode() {
        return code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
