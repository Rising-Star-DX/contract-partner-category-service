package com.partner.contract.category.client;

import com.partner.contract.category.client.dto.DocumentCountResponse;
import com.partner.contract.category.client.dto.DocumentExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("agreement-service")
public interface AgreementFeignClient {

    @GetMapping("/agreements/internal/categories/{categoryId}/exists")
    DocumentExistsResponse doesAgreementExistByCategoryId(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/agreements/internal/count-by-category")
    List<DocumentCountResponse> getAgreementsCountByCategoryId();
}
