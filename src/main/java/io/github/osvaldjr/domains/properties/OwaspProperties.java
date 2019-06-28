package io.github.osvaldjr.domains.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class OwaspProperties {

  private String server = "localhost";
  private Integer port = 8090;
  private boolean externalTarget = false;
  private String overwriteDataFolder = "";
}
