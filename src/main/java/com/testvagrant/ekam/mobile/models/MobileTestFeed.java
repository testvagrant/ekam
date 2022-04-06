package com.testvagrant.ekam.mobile.models;

import com.testvagrant.ekam.commons.parsers.DataTypeParser.ParserStrategyCreator;
import lombok.*;

import java.util.*;

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
            parsedCapabilities.put(property.getKey(),new ParserStrategyCreator().parse(property.getValue()));
        return parsedCapabilities;
    }

  @Builder.Default private List<String> serverArguments = new ArrayList<>();
}
