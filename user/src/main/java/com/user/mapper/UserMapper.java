package com.user.mapper;

import com.user.ResponseBean.ResponseBean;
import com.user.dto.request.UserRequestBean;
import com.user.dto.response.ContactResponseBean;
import com.user.dto.response.UserResponseBean;
import com.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper
{

    public User requestEntityMapperUpdate(UserRequestBean userRequestBean, User user){
        return user.builder()
                .userId(user.getUserId())
                .userName(userRequestBean.getUserName() != null ? userRequestBean.getUserName(): user.getUserName())
                .userGender(userRequestBean.getGender() != null ? userRequestBean.getGender() : user.getUserGender())
                .build();
    }

    public UserResponseBean entityResponseMapper(User user, List<ContactResponseBean> contacts){
        return UserResponseBean.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userGender(user.getUserGender())
                .contacts(contacts)
                .build();
    }

    public User requestEntityMapperCreate(UserRequestBean userRequestBean) {
        return User.builder()
                .userName(userRequestBean.getUserName())
                .userGender(userRequestBean.getGender())
                .build();
    }
}
