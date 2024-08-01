package com.recreo.utils;

import com.recreo.exceptions.RecreoApiException;

public class AppUtils {
    public static void validatePageNumberAndSize(int page, int size) throws RecreoApiException {
        if (page < 0) {
            throw new RecreoApiException("El paginado no puede ser menor de cero.");
        }

        if (size < 0) {
            throw new RecreoApiException("El numero del tamaño no puede ser menor de cero.");
        }

        if (size > AppConstant.MAX_PAGE_SIZE) {
            throw new RecreoApiException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
        }
    }
}
