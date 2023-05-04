package com.contact.repository;

import com.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByContactName(String contactName);

    Optional<Contact> findByContactNameAndNumber(String contactName, String number);
}
