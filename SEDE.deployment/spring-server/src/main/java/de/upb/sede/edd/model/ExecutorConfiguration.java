package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * ExecutorConfiguration
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class ExecutorConfiguration   {
  @JsonProperty("base")
  private String base = null;

  @JsonProperty("workers")
  private Long workers = null;

  public ExecutorConfiguration base(String base) {
    this.base = base;
    return this;
  }

  /**
   * Get base
   * @return base
  **/
  @ApiModelProperty(example = "JAVA_EXECUTOR", value = "")

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public ExecutorConfiguration workers(Long workers) {
    this.workers = workers;
    return this;
  }

  /**
   * Get workers
   * @return workers
  **/
  @ApiModelProperty(example = "4", value = "")

  public Long getWorkers() {
    return workers;
  }

  public void setWorkers(Long workers) {
    this.workers = workers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExecutorConfiguration executorConfiguration = (ExecutorConfiguration) o;
    return Objects.equals(this.base, executorConfiguration.base) &&
        Objects.equals(this.workers, executorConfiguration.workers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(base, workers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExecutorConfiguration {\n");

    sb.append("    base: ").append(toIndentedString(base)).append("\n");
    sb.append("    workers: ").append(toIndentedString(workers)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
