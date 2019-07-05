package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Installation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class Installation   {
  @JsonProperty("serviceCollectionName")
  private String serviceCollectionName = null;

  @JsonProperty("includedServices")
  @Valid
  private List<String> includedServices = null;

  @JsonProperty("requestedServices")
  @Valid
  private List<String> requestedServices = null;

  @JsonProperty("success")
  private Boolean success = null;

  @JsonProperty("out")
  private String out = null;

    @JsonProperty("errOut")
    private String errOut = null;

    @JsonProperty("machine")
    private String machine = null;

  public Installation serviceCollectionName(String serviceCollectionName) {
    this.serviceCollectionName = serviceCollectionName;
    return this;
  }

  /**
   * Get serviceCollectionName
   * @return serviceCollectionName
  **/
  @ApiModelProperty(value = "")

  public String getServiceCollectionName() {
    return serviceCollectionName;
  }

  public void setServiceCollectionName(String serviceCollectionName) {
    this.serviceCollectionName = serviceCollectionName;
  }

  public Installation includedServices(List<String> includedServices) {
    this.includedServices = includedServices;
    return this;
  }

  public Installation addIncludedServicesItem(String includedServicesItem) {
    if (this.includedServices == null) {
      this.includedServices = new ArrayList<String>();
    }
    this.includedServices.add(includedServicesItem);
    return this;
  }

  /**
   * Get includedServices
   * @return includedServices
  **/
  @ApiModelProperty(value = "")

  public List<String> getIncludedServices() {
    return includedServices;
  }

  public void setIncludedServices(List<String> includedServices) {
    this.includedServices = includedServices;
  }

  public Installation requestedServices(List<String> requestedServices) {
    this.requestedServices = requestedServices;
    return this;
  }

  public Installation addRequestedServicesItem(String requestedServicesItem) {
    if (this.requestedServices == null) {
      this.requestedServices = new ArrayList<String>();
    }
    this.requestedServices.add(requestedServicesItem);
    return this;
  }

  /**
   * Get requestedServices
   * @return requestedServices
  **/
  @ApiModelProperty(value = "")

  public List<String> getRequestedServices() {
    return requestedServices;
  }

  public void setRequestedServices(List<String> requestedServices) {
    this.requestedServices = requestedServices;
  }

  public Installation success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
  **/
  @ApiModelProperty(value = "")

  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Installation out(String out) {
    this.out = out;
    return this;
  }

  /**
   * Get out
   * @return out
  **/
  @ApiModelProperty(value = "")

  public String getOut() {
    return out;
  }

  public void setOut(String out) {
    this.out = out;
  }

  public Installation errOut(String errOut) {
    this.errOut = errOut;
    return this;
  }

  /**
   * Get errOut
   * @return errOut
  **/
  @ApiModelProperty(value = "")

  public String getErrOut() {
    return errOut;
  }

  public void setErrOut(String errOut) {
    this.errOut = errOut;
  }


    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Installation installation = (Installation) o;
    return Objects.equals(this.serviceCollectionName, installation.serviceCollectionName) &&
        Objects.equals(this.includedServices, installation.includedServices) &&
        Objects.equals(this.requestedServices, installation.requestedServices) &&
        Objects.equals(this.success, installation.success) &&
        Objects.equals(this.out, installation.out) &&
        Objects.equals(this.errOut, installation.errOut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceCollectionName, includedServices, requestedServices, success, out, errOut);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Installation {\n");
    
    sb.append("    serviceCollectionName: ").append(toIndentedString(serviceCollectionName)).append("\n");
    sb.append("    includedServices: ").append(toIndentedString(includedServices)).append("\n");
    sb.append("    requestedServices: ").append(toIndentedString(requestedServices)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    out: ").append(toIndentedString(out)).append("\n");
    sb.append("    errOut: ").append(toIndentedString(errOut)).append("\n");
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
