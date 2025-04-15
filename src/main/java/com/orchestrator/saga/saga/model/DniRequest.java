package com.orchestrator.saga.saga.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * DniRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-04-15T10:18:43.527012100-05:00[America/Lima]")
public class DniRequest {

  private String dni;

  /**
   * Default constructor
   * @deprecated Use {@link DniRequest#DniRequest(String)}
   */
  @Deprecated
  public DniRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DniRequest(String dni) {
    this.dni = dni;
  }

  public DniRequest dni(String dni) {
    this.dni = dni;
    return this;
  }

  /**
   * Get dni
   * @return dni
  */
  @NotNull 
  @Schema(name = "dni", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dni")
  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DniRequest dniRequest = (DniRequest) o;
    return Objects.equals(this.dni, dniRequest.dni);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dni);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DniRequest {\n");
    sb.append("    dni: ").append(toIndentedString(dni)).append("\n");
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

