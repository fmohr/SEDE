package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * ExecutorContactInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class ExecutorContactInfo   {
  @JsonProperty("httpAddress")
  private String httpAddress = null;

  public ExecutorContactInfo httpAddress(String httpAddress) {
    this.httpAddress = httpAddress;
    return this;
  }

  /**
   * Get httpAddress
   * @return httpAddress
  **/
  @ApiModelProperty(example = "localhost:9000", value = "")

  public String getHttpAddress() {
    return httpAddress;
  }

  public void setHttpAddress(String httpAddress) {
    this.httpAddress = httpAddress;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExecutorContactInfo executorContactInfo = (ExecutorContactInfo) o;
    return Objects.equals(this.httpAddress, executorContactInfo.httpAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(httpAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExecutorContactInfo {\n");

    sb.append("    httpAddress: ").append(toIndentedString(httpAddress)).append("\n");
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
