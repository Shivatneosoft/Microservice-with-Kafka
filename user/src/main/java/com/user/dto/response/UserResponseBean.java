package com.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseBean {

    @JsonProperty(value = "user_id")
    private long userId;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "user_gender")
    private String userGender;

    @JsonProperty(value = "contacts")
    private List<ContactResponseBean> contacts;

}