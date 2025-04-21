package com.partner.contract.category.controller;

import com.partner.contract.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories/internal")
public class CategoryInternalController {

    private final CategoryService categoryService;
}
