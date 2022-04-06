package com.testvagrant.ekam.commons.parsers.DataTypeParser;

import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;

public class DoubleParser implements Parser {
    @Override
    public Object parse(Object object) {
        String capabilityValue= SystemPropertyParser.parse(object.toString().trim());
       return Double.valueOf(capabilityValue).intValue();
    }
}
