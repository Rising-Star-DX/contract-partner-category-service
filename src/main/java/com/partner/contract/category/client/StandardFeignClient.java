package com.partner.contract.category.client;

import com.partner.contract.category.client.dto.DocumentCountResponseDto;
import com.partner.contract.category.client.dto.DocumentExistsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("standard-service")
public interface StandardFeignClient {

    @GetMapping("/standards/internal/categories/{categoryId}/exists")
    DocumentExistsResponseDto doesStandardExistByCategoryId(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/standards/internal/count-by-category")
    List<DocumentCountResponseDto> getStandardCountByCategoryId();
}
