package com.testvagrant.ekam.commons.parsers.DataTypeParser;

import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;

public class StringParser implements Parser {
    @Override
    public Object parse(Object object) {
        return SystemPropertyParser.parse(object.toString());
    }
}
