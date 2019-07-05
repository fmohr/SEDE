package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Executor
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class Executor   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("services")
  private Services services = null;

  @JsonProperty("config")
  private ExecutorConfiguration config = null;

  @JsonProperty("contact")
  private ExecutorContactInfo contact = null;

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

  public Executor services(Services services) {
    this.services = services;
    return this;
  }

  /**
   * Get services
   * @return services
  **/
  @ApiModelProperty(value = "")

  @Valid
  public Services getServices() {
    return services;
  }

  public void setServices(Services services) {
    this.services = services;
  }

  public Executor config(ExecutorConfiguration config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
  **/
  @ApiModelProperty(value = "")

  @Valid
  public ExecutorConfiguration getConfig() {
    return config;
  }

  public void setConfig(ExecutorConfiguration config) {
    this.config = config;
  }

  public Executor contact(ExecutorContactInfo contact) {
    this.contact = contact;
    return this;
  }

  /**
   * Get contact
   * @return contact
  **/
  @ApiModelProperty(value = "")

  @Valid
  public ExecutorContactInfo getContact() {
    return contact;
  }

  public void setContact(ExecutorContactInfo contact) {
    this.contact = contact;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Executor executor = (Executor) o;
    return Objects.equals(this.id, executor.id) &&
        Objects.equals(this.services, executor.services) &&
        Objects.equals(this.config, executor.config) &&
        Objects.equals(this.contact, executor.contact);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, services, config, contact);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Executor {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
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
