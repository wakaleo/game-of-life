package com.huilian.hlej.jet.common.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

public class BeanCopierUtil {

    public static void copy(Object source, Object target) {
        BeanCopier bc = BeanCopier.create(source.getClass(), target.getClass(), false);
        bc.copy(source, target, null);
    }

    public static void copy(Object source, Object target, Converter converter) {
        BeanCopier bc = BeanCopier.create(source.getClass(), target.getClass(), true);
        bc.copy(source, target, converter);
    }

}
