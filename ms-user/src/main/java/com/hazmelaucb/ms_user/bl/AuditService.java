package com.hazmelaucb.ms_user.bl;


import com.hazmelaucb.ms_user.dao.UserAuditRepository;
import com.hazmelaucb.ms_user.entity.UserAudit;
import com.hazmelaucb.ms_user.utils.ServiceException;
import com.hazmelaucb.ms_user.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditService {

    @Autowired
    private UserAuditRepository userAuditRepository;

    @Autowired
    private ServiceUtil serviceUtil;

    public void createAudit(UUID userId, String action, String previousData) {
        try {
            String address = serviceUtil.getServiceAddress();
            UserAudit audit = new UserAudit();
            audit.setUserId(userId);
            audit.setAction(action);
            // Si previousData no es nulo, envolvemos el valor en comillas para que sea una cadena JSON válida.
            if (previousData != null) {
                audit.setPreviousData("\"" + previousData + "\"");
            } else {
                audit.setPreviousData(null);
            }
            audit.setDeviceAddress(address);
            audit.setIpAddress(address);
            userAuditRepository.save(audit);
        } catch (Exception e) {
            throw new ServiceException("Error al registrar la auditoría", e);
        }
    }
}
