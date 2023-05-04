package com.contact.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactRequestBean {

    @JsonProperty(value = "contact_id")
    private Long contactId;

    @JsonProperty(value = "contact_name")
    private String contactName;

    @JsonProperty(value = "contact_number")
    private String number;


}
