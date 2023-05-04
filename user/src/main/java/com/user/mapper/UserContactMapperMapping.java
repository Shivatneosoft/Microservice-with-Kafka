package com.user.mapper;

import com.user.dto.response.ContactResponseBean;
import com.user.entity.User;
import com.user.entity.UserContactMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserContactMapperMapping {

    public List<UserContactMapper> userContactMapperMapping(User user, List<ContactResponseBean> contactResponseBeanList){
        return contactResponseBeanList.stream().map(contactResponseBean -> UserContactMapper.builder()
                    .userId(user.getUserId())
                    .contactId(contactResponseBean.getContactId())
                    .build())
                .collect(Collectors.toList());
    }

}
