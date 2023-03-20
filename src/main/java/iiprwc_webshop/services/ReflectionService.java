package iiprwc_webshop.services;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class ReflectionService {

    public static <T> T patchEntity (T oldData, Map<Object, Object> data, Class<T> entity) {
        data.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(entity, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, oldData, value);
        });

        return oldData;
    }

}

