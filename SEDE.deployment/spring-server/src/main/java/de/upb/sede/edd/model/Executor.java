package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Executor
 */
@Validated
public class Executor   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("entities")
  private Entities entities = null;

  @JsonProperty("basis")
  private ExecutorConfiguration basis = null;

  public Executor id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Executor entities(Entities entities) {
    this.entities = entities;
    return this;
  }

  /**
   * Get entities
   * @return entities
  **/
  @ApiModelProperty(value = "")

  @Valid
  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Executor basis(ExecutorConfiguration basis) {
    this.basis = basis;
    return this;
  }

  /**
   * Get basis
   * @return basis
  **/
  @ApiModelProperty(value = "")

  @Valid
  public ExecutorConfiguration getBasis() {
    return basis;
  }

  public void setBasis(ExecutorConfiguration basis) {
    this.basis = basis;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Executor executor = (Executor) o;
    return Objects.equals(this.id, executor.id) &&
        Objects.equals(this.entities, executor.entities) &&
        Objects.equals(this.basis, executor.basis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, entities, basis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Executor {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
    sb.append("    basis: ").append(toIndentedString(basis)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
