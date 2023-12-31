package com.nowoczesnyjunior.financialapp.expensemodule.mapper;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryDto.class, ExpenseCategory.class})
public interface ExpenseMapper {

    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "date", source = "expenseDate")
    @Mapping(target = "category",source = "category" )
    @Mapping(target = "description", source = "description")
    ExpenseDto expenseToExpenseDto(Expense expense);

    @InheritInverseConfiguration
    Expense dtoToModel(ExpenseDto expenseDto);

    List<ExpenseDto> expensesToDtos(List<Expense> expenseList);

    @Mapping(target = "id", source = "categoryId")
    @Mapping(target = "name", source = "categoryName")
    CategoryDto categoryToDto(ExpenseCategory expenseCategory);

    @InheritInverseConfiguration
    ExpenseCategory categoryDtoToModel(CategoryDto categoryDto);
}
