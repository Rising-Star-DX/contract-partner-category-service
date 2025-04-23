package com.partner.contract.category.client;

import com.partner.contract.category.client.dto.DocumentCountResponseDto;
import com.partner.contract.category.client.dto.DocumentExistsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("agreement-service")
public interface AgreementFeignClient {

    @GetMapping("/agreements/internal/categories/{categoryId}/exists")
    DocumentExistsResponseDto doesAgreementExistByCategoryId(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/agreements/internal/count-by-category")
    List<DocumentCountResponseDto> getAgreementsCountByCategoryId();
}
