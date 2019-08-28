package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * ServiceFulfillment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class ServiceFulfillment   {
  @JsonProperty("service")
  private String service = null;

  @JsonProperty("executorAddress")
  private String executorAddress = null;

  @JsonProperty("executor")
  private Executor executor = null;

  public ServiceFulfillment service(String service) {
    this.service = service;
    return this;
  }

  /**
   * Get service
   * @return service
  **/
  @ApiModelProperty(value = "")

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public ServiceFulfillment executorAddress(String executorAddress) {
    this.executorAddress = executorAddress;
    return this;
  }

  /**
   * Get executorAddress
   * @return executorAddress
  **/
  @ApiModelProperty(value = "")

  public String getExecutorAddress() {
    return executorAddress;
  }

  public void setExecutorAddress(String executorAddress) {
    this.executorAddress = executorAddress;
  }

  public ServiceFulfillment executor(Executor executor) {
    this.executor = executor;
    return this;
  }

  /**
   * Get executor
   * @return executor
  **/
  @ApiModelProperty(value = "")

  @Valid
  public Executor getExecutor() {
    return executor;
  }

  public void setExecutor(Executor executor) {
    this.executor = executor;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceFulfillment serviceFulfillment = (ServiceFulfillment) o;
    return Objects.equals(this.service, serviceFulfillment.service) &&
        Objects.equals(this.executorAddress, serviceFulfillment.executorAddress) &&
        Objects.equals(this.executor, serviceFulfillment.executor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(service, executorAddress, executor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceFulfillment {\n");

    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    executorAddress: ").append(toIndentedString(executorAddress)).append("\n");
    sb.append("    executor: ").append(toIndentedString(executor)).append("\n");
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
