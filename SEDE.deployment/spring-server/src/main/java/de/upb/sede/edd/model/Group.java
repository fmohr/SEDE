package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * Group name. Regex &#x3D; &#x60;([A-Za-z0-9_])+&#x60;
 */
@ApiModel(description = "Group name. Regex = `([A-Za-z0-9_])+`")
@Validated
public class Group   {
  @JsonProperty("path")
  private String path = null;

  @JsonProperty("connection")
  private String connection = null;

  @JsonProperty("gateway")
  private String gateway = null;

  public Group path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(value = "")

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Group connection(String connection) {
    this.connection = connection;
    return this;
  }

  /**
   * Get connection
   * @return connection
  **/
  @ApiModelProperty(value = "")

  public String getConnection() {
    return connection;
  }

  public void setConnection(String connection) {
    this.connection = connection;
  }

  public Group gateway(String gateway) {
    this.gateway = gateway;
    return this;
  }

  /**
   * Get gateway
   * @return gateway
  **/
  @ApiModelProperty(value = "")

  public String getGateway() {
    return gateway;
  }

  public void setGateway(String gateway) {
    this.gateway = gateway;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Group group = (Group) o;
    return Objects.equals(this.path, group.path) &&
        Objects.equals(this.connection, group.connection) &&
        Objects.equals(this.gateway, group.gateway);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, connection, gateway);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Group {\n");
    
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    connection: ").append(toIndentedString(connection)).append("\n");
    sb.append("    gateway: ").append(toIndentedString(gateway)).append("\n");
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
