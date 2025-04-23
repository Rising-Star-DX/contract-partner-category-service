package com.partner.contract.category.service;

import com.partner.contract.category.client.AgreementFeignClient;
import com.partner.contract.category.client.StandardFeignClient;
import com.partner.contract.category.client.dto.DocumentCountResponse;
import com.partner.contract.category.domain.Category;
import com.partner.contract.category.dto.CategoryListResponseDto;
import com.partner.contract.category.dto.CategoryNameListResponseDto;
import com.partner.contract.category.repository.CategoryRepository;
import com.partner.contract.global.exception.error.ApplicationException;
import com.partner.contract.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final StandardFeignClient standardFeignClient;
    private final AgreementFeignClient agreementFeignClient;

    public List<CategoryListResponseDto> findCategoryList(String name) {

        List<Category> categories = categoryRepository.findByNameContaining(name);

        List<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        // Bulk 요청
        List<DocumentCountResponse> standardCounts = standardFeignClient.getStandardCountByCategoryId();
        List<DocumentCountResponse> agreementCounts = agreementFeignClient.getAgreementsCountByCategoryId();

        // Map으로 변환 (성능 향상)
        Map<Long, Long> standardCountMap = standardCounts.stream()
                .collect(Collectors.toMap(DocumentCountResponse::getId, DocumentCountResponse::getCount));

        Map<Long, Long> agreementCountMap = agreementCounts.stream()
                .collect(Collectors.toMap(DocumentCountResponse::getId, DocumentCountResponse::getCount));

        return categories.stream()
                .map(category -> CategoryListResponseDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .countOfStandards(standardCountMap.getOrDefault(category.getId(), 0L))
                        .countOfAgreements(agreementCountMap.getOrDefault(category.getId(), 0L))
                        .createdAt(category.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CategoryNameListResponseDto> findCategoryNameList() {
        return categoryRepository
                .findCategoryNameListOrderByName()
                .stream()
                .map(CategoryNameListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Boolean checkStandardExistence(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        return standardFeignClient.doesStandardExistByCategoryId(id).getExists();
    }

    public Boolean checkAgreementExistence(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        return agreementFeignClient.doesAgreementExistByCategoryId(id).getExists();
    }

    public void addCategory(String categoryName) {
        Category existedCategory = categoryRepository.findByName(categoryName);

        if(existedCategory != null) {
            throw new ApplicationException(ErrorCode.CATEGORY_ALREADY_EXISTS_ERROR);
        }

        Category category = Category.builder()
                .name(categoryName)
                .build();

        categoryRepository.save(category);
    }


    public void modifyCategory(Long id, String categoryName) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        Category existedCategory = categoryRepository.findByName(categoryName);

        if(existedCategory != null) {
            throw new ApplicationException(ErrorCode.CATEGORY_ALREADY_EXISTS_ERROR);
        }

        category.update(categoryName);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND_ERROR));

        if (checkStandardExistence(id) || checkAgreementExistence(id)) {
            throw new ApplicationException(ErrorCode.CATEGORY_DOCUMENT_ALREADY_EXISTS_ERROR);
        }

        categoryRepository.deleteById(id);
    }
}
