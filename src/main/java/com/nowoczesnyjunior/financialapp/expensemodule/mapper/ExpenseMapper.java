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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryMapper.class})
public interface ExpenseMapper {

    @Mapping(target = "date", source = "expenseDate")
    ExpenseDto toDto(Expense expense);

    @InheritInverseConfiguration
    Expense toModel(ExpenseDto expenseDto);

    List<ExpenseDto> toDtoList(List<Expense> expenseList);
}
