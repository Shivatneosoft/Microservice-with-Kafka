package com.contact.service;

import com.contact.ResponseBean.ResponseBean;
import com.contact.dto.request.ContactRequestBean;
import com.contact.dto.response.ContactResponseBean;
import com.contact.entity.Contact;
import com.contact.enums.ContactStatus;
import com.contact.mapper.ContactMapper;
import com.contact.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public ResponseBean addContact(ContactRequestBean contactRequestBean){
        Optional<Contact> existingContact = contactRepository.findByContactName(contactRequestBean.getContactName());
        if(existingContact.isEmpty()) {
            existingContact = Optional.ofNullable(contactMapper.requestEntityMapperCreate(contactRequestBean));
            existingContact.get().setStatus(ContactStatus.Active);
            existingContact = Optional.ofNullable(contactRepository.saveAndFlush(existingContact.get()));
            return ResponseBean.builder().message("Contact is saved").status(Boolean.TRUE).data(contactMapper.entityResponseMapper(existingContact.get())).build();
        }
        else
            return ResponseBean.builder().message("Contact Name Already exist").status(Boolean.TRUE).build();
    }

    public ResponseBean findAllContact(){
        List<Contact> existing = contactRepository.findAll();
        if(existing.isEmpty()) {
            return ResponseBean.builder().status(Boolean.TRUE).message("No data Found").build();
        }
        return ResponseBean.builder().status(Boolean.TRUE).data(existing.stream().map(data-> contactMapper.entityResponseMapper(data))).build();
    }

    public ResponseBean findContactById(Long contactId){
        Optional<Contact> existing = contactRepository.findById(contactId);
        if(existing.isPresent())
            return ResponseBean.builder().status(Boolean.TRUE).data(contactMapper.entityResponseMapper(existing.get())).build();
        return ResponseBean.builder().status(Boolean.TRUE).message("No data Found").build();
    }

    public ResponseBean updateContact(ContactRequestBean contact){
        Optional<Contact> existing = contactRepository.findById(contact.getContactId());
        if(existing.isEmpty())
            return ResponseBean.builder().message("No Contact found to update").status(Boolean.TRUE).build();
        else {
            existing = Optional.ofNullable(contactMapper.requestEntityMapperUpdate(contact,existing.get()));
            existing = Optional.ofNullable(contactRepository.saveAndFlush(existing.get()));
            return ResponseBean.builder().message("Contact has been updated Successfully").status(Boolean.TRUE).data(contactMapper.entityResponseMapper(existing.get())).build();
        }
    }

    public ResponseBean deleteContact(Long contactId){
        Optional<Contact> existing = contactRepository.findById(contactId);
        if(existing.isEmpty())
            return ResponseBean.builder().message("No Contact found to delete").status(Boolean.TRUE).build();
        else
            existing.get().setStatus(ContactStatus.Deleted);
            existing = Optional.ofNullable(contactRepository.saveAndFlush(existing.get()));
            return ResponseBean.builder().message("Contact has been Deleted Successfull").status(Boolean.TRUE).data(contactMapper.entityResponseMapper(existing.get())).build();
    }

    //Feign Client
    public List<ContactResponseBean> findAllByIds(List<Long> contactIds) {
        List<Contact> contacts = this.contactRepository.findAllById(contactIds);
        return this.contactMapper.entityResponseMapperList(contacts);
    }

    public List<ContactResponseBean> saveContact(List<ContactRequestBean> contactRequestBeans) {
        List<Contact> contacts = this.contactMapper.requestEntityMapperCreateList(contactRequestBeans);
        List<Contact> contactInDB = new ArrayList<>();
        for (Contact contact: contacts) {
            Optional<Contact> existingContact = contactRepository.findByContactNameAndNumber(contact.getContactName(), contact.getNumber());
            if(existingContact.isEmpty()) {
                contact.setStatus(ContactStatus.Active);
                Contact savedContact = this.contactRepository.save(contact);
                contactInDB.add(savedContact);
            }
            else
                contactInDB.add(existingContact.get());
        }
        List<ContactResponseBean> contactResponseBeans = this.contactMapper.entityResponseMapperList(contactInDB);
        return contactResponseBeans;
    }


    public List<ContactResponseBean> getAllContacts(){
        List<Contact> allContacts = this.contactRepository.findAll();
        return this.contactMapper.entityResponseMapperList(allContacts);
    }
}
