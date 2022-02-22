package com.testvagrant.ekam.mobile.models;

import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileTestFeed {
  @Builder.Default private String appDir = "app";

  @Builder.Default
  private List<Map<String, Object>> desiredCapabilities =
      new ArrayList<Map<String, Object>>() {
        {
          add(new HashMap<>());
        }
      };
    public Map<String,Object> parseSystemProperty(Map<String,Object>capabilities){
        Map<String,Object>parsedCapabilities=new HashMap<>();
        for(Map.Entry<String,Object>property:capabilities.entrySet())
            parsedCapabilities.put(property.getKey(), SystemPropertyParser.parse(property.getValue().toString()));
        return parsedCapabilities;
    }

  @Builder.Default private List<String> serverArguments = new ArrayList<>();
}
