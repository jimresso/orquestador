package com.orchestrator.saga.saga.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CheckDebtorsRequest
 */

@JsonTypeName("checkDebtors_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-04-13T12:25:25.052669800-05:00[America/Lima]")
public class CheckDebtorsRequest {

  @Valid
  private List<String> customerIds;

  public CheckDebtorsRequest customerIds(List<String> customerIds) {
    this.customerIds = customerIds;
    return this;
  }

  public CheckDebtorsRequest addCustomerIdsItem(String customerIdsItem) {
    if (this.customerIds == null) {
      this.customerIds = new ArrayList<>();
    }
    this.customerIds.add(customerIdsItem);
    return this;
  }

  /**
   * Get customerIds
   * @return customerIds
  */
  
  @Schema(name = "customerIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customerIds")
  public List<String> getCustomerIds() {
    return customerIds;
  }

  public void setCustomerIds(List<String> customerIds) {
    this.customerIds = customerIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CheckDebtorsRequest checkDebtorsRequest = (CheckDebtorsRequest) o;
    return Objects.equals(this.customerIds, checkDebtorsRequest.customerIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckDebtorsRequest {\n");
    sb.append("    customerIds: ").append(toIndentedString(customerIds)).append("\n");
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

