package com.hazmelaucb.ms_rating.bl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-anuncios")
public interface AdClient {

    @GetMapping("/ads/exists/{id}")
    boolean adExists(@PathVariable("id") Long id);
}
