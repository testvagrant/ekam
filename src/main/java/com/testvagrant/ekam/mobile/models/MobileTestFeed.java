package com.testvagrant.ekam.mobile.models;

import com.testvagrant.ekam.mobile.AppiumTimeCapabilities;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if(capability.equals("otherApps")){
                ArrayList<String>otherApps= (ArrayList<String>) property.getValue();
                ArrayList<String>parsedApps=new ArrayList<>();
                for (String app : otherApps)
                    parsedApps.add(SystemPropertyParser.parse(app));
                parsedCapabilities.put(capability,parsedApps);
                continue;
            }
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
