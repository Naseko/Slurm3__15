package org.example;

import jakarta.json.JsonValue;
import lombok.Data;

@Data
public class CatFact {
  public String fact;
  public CatFact (JsonValue item){
    this.fact = item.toString();
  }
}
