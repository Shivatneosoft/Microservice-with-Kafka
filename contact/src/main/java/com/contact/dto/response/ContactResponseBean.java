package com.contact.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponseBean {

    @JsonProperty(value = "contact_id")
    private Long contactId;

    @JsonProperty(value = "contact_name")
    private String contactName;

    @JsonProperty(value = "contact_number")
    private String number;

}
