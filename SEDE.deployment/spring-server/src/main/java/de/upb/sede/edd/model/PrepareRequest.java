package de.upb.sede.edd.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * PrepareRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
public class PrepareRequest   {
    @JsonProperty("update")
    private Boolean update = false;

    @JsonProperty("register")
    private Boolean register = true;

  public PrepareRequest update(Boolean update, Boolean register) {
    this.update = update;
    this.register = register;
    return this;
  }

  /**
   * Get update
   * @return update
  **/
  @ApiModelProperty(value = "")

  public Boolean isUpdate() {
    return update;
  }

  public void setUpdate(Boolean update) {
    this.update = update;
  }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrepareRequest prepareRequest = (PrepareRequest) o;
    return Objects.equals(this.update, prepareRequest.update) &&
            Objects.equals(this.register, prepareRequest.register);
  }

  @Override
  public int hashCode() {
    return Objects.hash(update & register);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrepareRequest {\n");

      sb.append("    update: ").append(toIndentedString(update)).append("\n");

      sb.append("    register: ").append(toIndentedString(register)).append("\n");
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
