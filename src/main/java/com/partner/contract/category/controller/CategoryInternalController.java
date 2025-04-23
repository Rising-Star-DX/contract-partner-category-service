package com.partner.contract.category.controller;

import com.partner.contract.category.dto.CategoryNameListResponseDto;
import com.partner.contract.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories/internal")
public class CategoryInternalController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryNameListResponseDto> getAllCategoryIdAndName() {
        return categoryService.findAllCategoryIdAndName();
    }
}
