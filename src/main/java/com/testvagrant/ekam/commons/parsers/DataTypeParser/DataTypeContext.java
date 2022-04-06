package com.testvagrant.ekam.commons.parsers.DataTypeParser;



public class DataTypeContext {
    private Parser parser;

    public DataTypeContext(Parser parser){
        this.parser = parser;
    }
    public Object parse(Object object){
        return parser.parse(object);
    }

}
