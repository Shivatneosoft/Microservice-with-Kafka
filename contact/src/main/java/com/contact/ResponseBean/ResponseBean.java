package com.contact.ResponseBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean {

    public String message;

    public Boolean status;

    public Object data;

}
