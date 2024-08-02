package com.recreo.utils;

import com.recreo.exceptions.RecreoApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {

    @Autowired
    private MessageUtil messageUtil;

    public void validatePageNumberAndSize(int page, int size) throws RecreoApiException {
        if (page < 0) {
            throw new RecreoApiException(messageUtil.getMessage("pageable.less.zero"));
        }

        if (size < 0) {
            throw new RecreoApiException(messageUtil.getMessage("number.less.zero"));
        }

        if (size > AppConstant.MAX_PAGE_SIZE) {
            throw new RecreoApiException(messageUtil.getMessage("pageable.must", AppConstant.MAX_PAGE_SIZE));
        }
    }
}
