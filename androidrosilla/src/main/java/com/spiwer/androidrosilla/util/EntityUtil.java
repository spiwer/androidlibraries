package com.spiwer.androidrosilla.util;

import com.spiwer.androidrosilla.template.EntityManager;
import com.spiwer.androidstandard.exception.AppException;
import com.spiwer.androidstandard.template.IGenericMessage;
import com.spiwer.androidstandard.util.DataUtil;

public class EntityUtil {

    public static <T extends EntityManager<?>> T init(IGenericMessage message, T entity) throws AppException {
        if (DataUtil.isEmpty(entity)) {
            throw new AppException(message);
        }
        return entity;
    }
}
