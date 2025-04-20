package com.duox.escapenest.mapper;

import com.duox.escapenest.dto.response.RuleResponse;
import com.duox.escapenest.entity.Rule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RuleMapper {
    RuleResponse toResponse(Rule rule);
}
