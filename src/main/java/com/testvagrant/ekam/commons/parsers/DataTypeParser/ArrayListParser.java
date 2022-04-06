package com.testvagrant.ekam.commons.parsers.DataTypeParser;

import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;

import java.util.ArrayList;

public class ArrayListParser implements Parser {

    @Override
    public Object parse(Object object) {
        return SystemPropertyParser.parse(((ArrayList<String>) object));
    }
}
