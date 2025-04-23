package com.partner.contract.category.client;

import com.partner.contract.category.client.dto.ExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("agreement-service")
public interface AgreementFeignClient {

    @GetMapping("/agreements/internal/categories/check/{categoryId}")
    ExistsResponse existsByCategory(@PathVariable("categoryId") Long categoryId);
}
