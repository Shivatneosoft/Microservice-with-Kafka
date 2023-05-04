package com.user.service;

import com.user.ResponseBean.ResponseBean;
import com.user.dto.request.ContactRequestBean;
import com.user.dto.request.UserRequestBean;
import com.user.dto.response.ContactResponseBean;
import com.user.dto.response.UserResponseBean;
import com.user.entity.User;
import com.user.entity.UserContactMapper;
import com.user.feignclient.ContactFeignClient;
import com.user.mapper.UserContactMapperMapping;
import com.user.mapper.UserMapper;
import com.user.repository.UserContactMapperRepository;
import com.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Value("${kafka.name}")
    private String topicName;

    private UserRepository userRepository;

    private UserContactMapperRepository userContactMapperRepository;

    private ContactFeignClient contactFeignClient;

    private UserMapper userMapper;

    private UserContactMapperMapping userContactMapperMapping;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserService(UserRepository userRepository, UserContactMapperRepository userContactMapperRepository, ContactFeignClient contactFeignClient, UserMapper userMapper, UserContactMapperMapping userContactMapperMapping, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.userContactMapperRepository = userContactMapperRepository;
        this.contactFeignClient = contactFeignClient;
        this.userMapper = userMapper;
        this.userContactMapperMapping = userContactMapperMapping;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseBean addUser(UserRequestBean userRequestBean) {
        Boolean exists = this.userRepository.existsByUserNameAndUserGender(userRequestBean.getUserName(), userRequestBean.getGender());
        if (!exists) {
            List<ContactRequestBean> contacts = userRequestBean.getContacts();
            List<ContactResponseBean> contactResponseBeans = this.contactFeignClient.saveContact(contacts);
            User user = this.userMapper.requestEntityMapperCreate(userRequestBean);
            User userSave = this.userRepository.save(user);
            log.info("{}", userSave.toString());
            List<UserContactMapper> userContactMappers = this.userContactMapperMapping.userContactMapperMapping(userSave, contactResponseBeans);
            this.userContactMapperRepository.saveAll(userContactMappers);
            UserResponseBean userResponseBean = this.userMapper.entityResponseMapper(userSave, contactResponseBeans);
            this.kafkaTemplate.send(this.topicName,"User Data Saved Successfully");
            return ResponseBean.builder().status(Boolean.TRUE).data(userResponseBean).build();
        }
        this.kafkaTemplate.send(this.topicName,"User Data Saved failed");
        return ResponseBean.builder().status(Boolean.TRUE).message(userRequestBean.getUserName() + " Already Exist").build();
    }

    public ResponseBean findAllUsers() {
        List<ContactResponseBean> contacts = this.contactFeignClient.getAllContacts();
        List<User> users = this.userRepository.findAll();
        log.info("{}", contacts.toString());
        log.info("{}", users.toString());
        List<UserContactMapper> userContactMappers = this.userContactMapperRepository.findAll();
        List<UserResponseBean> userResponseBeans = new ArrayList<>();
        for (User userSearch : users) {
            List<ContactResponseBean> contactResponseBean = new ArrayList<>();
            for (UserContactMapper userContactMapper : userContactMappers) {
                if (userSearch.getUserId() == userContactMapper.getUserId()) {
                    for (ContactResponseBean contactResponseBeanSearch : contacts) {
                        if (contactResponseBeanSearch.getContactId() == userContactMapper.getContactId())
                            contactResponseBean.add(contactResponseBeanSearch);
                    }
                }
            }
            userResponseBeans.add(this.userMapper.entityResponseMapper(userSearch, contactResponseBean));
        }
        this.kafkaTemplate.send(this.topicName,"All User Data Received");
        return ResponseBean.builder().status(Boolean.TRUE).data(userResponseBeans).build();
    }

    public ResponseBean findUserById(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            this.kafkaTemplate.send(this.topicName,"User with used Id "+ userId +" Received Failed");
            return ResponseBean.builder().status(Boolean.TRUE).message("No user found with id " + userId).build();
        }
        User user = userOptional.get();
        List<UserContactMapper> userContactMappers = this.userContactMapperRepository.findAllByUserId(user.getUserId());
        List<Long> contactIds = new ArrayList<>();
        for (UserContactMapper userContactMapper : userContactMappers) {
            contactIds.add(userContactMapper.getContactId());
        }
        List<ContactResponseBean> contactResponseBeans = this.contactFeignClient.findAllByIds(contactIds);
        UserResponseBean userResponseBean = this.userMapper.entityResponseMapper(user, contactResponseBeans);
        this.kafkaTemplate.send(this.topicName,"User with used Id "+ userId +" Received Successfully");
        return ResponseBean.builder().data(userResponseBean).status(Boolean.TRUE).build();
    }

    public ResponseBean updateUser(UserRequestBean userRequestBean) {
        //fetching Data
        User user = this.userRepository.findByUserName(userRequestBean.getUserName()).get();
        User userUpdated = this.userMapper.requestEntityMapperUpdate(userRequestBean, user);
        log.info("{}",user);

        //Updating Contact
        List<ContactResponseBean> contactResponseBeans = this.contactFeignClient.saveContact(userRequestBean.getContacts());

        // Updating User
        log.info("{}",userUpdated);
        User UserSaved = this.userRepository.save(userUpdated);

        // Updating UserContactMapper
        this.userContactMapperRepository.deleteAllByUserId(user.getUserId());
        List<UserContactMapper> userContactMappers = this.userContactMapperMapping.userContactMapperMapping(UserSaved, contactResponseBeans);
        this.userContactMapperRepository.saveAll(userContactMappers);

        // Mapping to userResponse Bean
        UserResponseBean userResponseBean = this.userMapper.entityResponseMapper(UserSaved, contactResponseBeans);
        this.kafkaTemplate.send(this.topicName,"User with used Id "+ user.getUserName() +" Updated Successfully");
        return ResponseBean.builder().status(Boolean.TRUE).data(userResponseBean).build();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
