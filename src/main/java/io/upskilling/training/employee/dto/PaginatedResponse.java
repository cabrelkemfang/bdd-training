package io.upskilling.training.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Generic paginated response wrapper")
public record PaginatedResponse<T>(
        @Schema(description = "List of items in the current page")
        List<T> response,

        @Schema(description = "Total number of items across all pages", example = "42")
        long totalElements,

        @Schema(description = "Total number of pages", example = "5")
        int totalPages,

        @Schema(description = "Current page number (1-based)", example = "1")
        int currentPage,

        @Schema(description = "Number of items per page", example = "10")
        int pageSize) {
}
