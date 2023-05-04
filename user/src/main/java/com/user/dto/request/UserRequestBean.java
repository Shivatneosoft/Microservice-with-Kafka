package com.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.dto.response.ContactResponseBean;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestBean {

    @JsonProperty(value = "user_id")
    private long userId;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "user_gender")
    private String gender;

    @JsonProperty(value = "contacts")
    private List<ContactRequestBean> contacts;

}