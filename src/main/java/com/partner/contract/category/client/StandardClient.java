package com.partner.contract.category.client;

import com.partner.contract.category.client.dto.StandardExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "standard-service")
public interface StandardClient {

    @GetMapping("/standards/internal/categories/check/{categoryId}")
    StandardExistsResponse existsByCategory(@PathVariable("categoryId") Long categoryId);
}
