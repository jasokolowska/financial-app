package com.nowoczesnyjunior.financialapp.expensemodule.mapper;

import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "id", source = "categoryId")
    @Mapping(target = "name", source = "categoryName")
    CategoryDto toDto(ExpenseCategory expenseCategory);

    @InheritInverseConfiguration
    ExpenseCategory toModel(CategoryDto categoryDto);
}
