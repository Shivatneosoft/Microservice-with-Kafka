package com.user.ResponseBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean {

    public String message;

    public Boolean status;

    public Object data;

}
