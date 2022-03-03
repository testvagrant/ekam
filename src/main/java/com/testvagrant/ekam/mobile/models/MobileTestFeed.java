package com.testvagrant.ekam.mobile.models;

import com.testvagrant.ekam.mobile.AppiumTimeCapabilities;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
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
        for(Map.Entry<String,Object>property:capabilities.entrySet()) {
            String capability=property.getKey();
            String capabilityValue=SystemPropertyParser.parse(property.getValue().toString().trim());
            if(Arrays.stream(AppiumTimeCapabilities.values()).anyMatch((t) -> t.name().equals(capability))) {
                parsedCapabilities.put(capability, Double.valueOf(capabilityValue).intValue());
                continue;
            }
            parsedCapabilities.put(capability,capabilityValue);
        }
        return parsedCapabilities;
    }

  @Builder.Default private List<String> serverArguments = new ArrayList<>();
}
