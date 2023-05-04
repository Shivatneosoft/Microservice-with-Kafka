package com.audit.service;

import com.audit.entity.Audit;
import com.audit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void saveAudit(String message){
        Audit audit = Audit.builder().message(message).build();
        this.auditRepository.save(audit);
    }

}
