package com.contact.mapper;

import com.contact.dto.request.ContactRequestBean;
import com.contact.dto.response.ContactResponseBean;
import com.contact.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    public Contact requestEntityMapperUpdate(ContactRequestBean contactRequestBean, Contact contact){
        return contact.builder()
                .contactId(contactRequestBean.getContactId() != null ? contactRequestBean.getContactId() : contact.getContactId())
                .contactName(contactRequestBean.getContactName() != null ? contactRequestBean.getContactName(): contact.getContactName())
                .number(contactRequestBean.getNumber() != null ? contactRequestBean.getNumber() : contact.getNumber())
                .build();
    }

    public ContactResponseBean entityResponseMapper(Contact contact){
        return ContactResponseBean.builder()
                .contactId(contact.getContactId())
                .contactName(contact.getContactName())
                .number(contact.getNumber())
                .build();
    }

    public Contact requestEntityMapperCreate(ContactRequestBean contactRequestBean) {
        return Contact.builder()
                .contactName(contactRequestBean.getContactName())
                .number(contactRequestBean.getNumber())
                .build();
    }

    public List<ContactResponseBean> entityResponseMapperList(List<Contact> contacts) {
        return contacts.stream()
                .map(contact ->
                    ContactResponseBean.builder()
                            .contactId(contact.getContactId())
                            .contactName(contact.getContactName())
                            .number(contact.getNumber())
                            .build()
                ).collect(Collectors.toList());
    }

    public List<Contact> requestEntityMapperCreateList(List<ContactRequestBean> contacts) {
        return contacts.stream()
                .map(contact ->
                        Contact.builder()
                                .contactId(contact.getContactId())
                                .contactName(contact.getContactName())
                                .number(contact.getNumber())
                                .build()
                ).collect(Collectors.toList());
    }
}
