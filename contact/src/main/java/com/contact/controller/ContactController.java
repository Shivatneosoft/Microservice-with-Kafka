package com.contact.controller;

import com.contact.ResponseBean.ResponseBean;
import com.contact.dto.request.ContactRequestBean;
import com.contact.dto.response.ContactResponseBean;
import com.contact.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/")
    public ResponseBean addContact(@RequestBody ContactRequestBean contact){
        return contactService.addContact(contact);
    }

    @GetMapping("/")
    public ResponseBean findAllContacts(){
        return contactService.findAllContact();
    }

    @GetMapping("/{contact_id}")
    public ResponseBean findContactById(@PathVariable("contact_id") Long contactId){
        return contactService.findContactById(contactId);
    }

    @PutMapping("/")
    public ResponseBean updateContact(@RequestBody ContactRequestBean contact){
        return contactService.updateContact(contact);
    }

    @DeleteMapping("/{contact_id}")
    public ResponseBean deleteContact(@PathVariable("contact_id") Long contactId){
        return contactService.deleteContact(contactId);
    }

    //Feign Client
    @PostMapping("/findByIdsF")
    public List<ContactResponseBean> findAllByIds(@RequestBody List<Long> contactIds){
        return this.contactService.findAllByIds(contactIds);
    }

    @PostMapping("/saveContact")
    public List<ContactResponseBean> saveContact(@RequestBody List<ContactRequestBean> contacts){
        return this.contactService.saveContact(contacts);
    }

    @GetMapping("/getAllContacts")
    List<ContactResponseBean> getAllContacts(){
        return this.contactService.getAllContacts();
    }

}
