package com.testvagrant.ekam.commons.parsers.DataTypeParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParserStrategyCreator {

    public Object parse(Object object){
        Parser parser = getStrategy(object).get(object.getClass());
        if(parser == null)
            parser=getDefaultStrategy(object);
        return new DataTypeContext(parser).parse(object);
    }


    private Map<Class<?> , Parser> getStrategy(Object object) {
        Map<Class<?>, Parser>parser=new HashMap<>();
        parser.put(ArrayList.class,new ArrayListParser());
        parser.put(Double.class,new DoubleParser());
        parser.put(String.class,new StringParser());
        return parser;
    }

    private Parser getDefaultStrategy(Object object) {
        return new StringParser();
    }

}
