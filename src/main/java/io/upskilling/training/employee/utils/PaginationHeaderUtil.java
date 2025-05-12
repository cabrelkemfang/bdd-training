package io.upskilling.training.employee.utils;

import io.upskilling.training.employee.dto.EmployeeResponse;
import io.upskilling.training.employee.dto.PaginatedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class PaginationHeaderUtil {

    public static HttpHeaders buildPaginationHeaders(PaginatedResponse<EmployeeResponse> employee) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(employee.totalElements()));
        headers.add("X-Total-Pages", String.valueOf(employee.totalPages()));
        headers.add("X-Current-Page", String.valueOf(employee.currentPage()));
        headers.add("X-Page-Size", String.valueOf(employee.pageSize()));
        return headers;
    }
}
