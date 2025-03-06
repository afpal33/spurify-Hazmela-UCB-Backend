package com.hazmelaucb.ms_user.api;

import com.hazmelaucb.ms_user.bl.UserCommonBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserCommonController {

    @Autowired
    private UserCommonBL userCommonBL;

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable("id") UUID userId) {
        userCommonBL.disableUser(userId);
        return ResponseEntity.noContent().build();
    }
}
