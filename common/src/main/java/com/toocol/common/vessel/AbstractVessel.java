package com.toocol.common.vessel;

import com.toocol.common.utils.SpringGetter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this abstract vessel is provided for product side.
 *
 * @author Joezeo
 * @date 2021/7/31 23:41
 */
public abstract class AbstractVessel {

    public void init() {
        Class<?> superClass = this.getClass();
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        } while (superClass != null);

        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                field.set(this, SpringGetter.getBean(field.getType()).orElse(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("inject field:" + field.getName() + " into vessel failed, stop the server.");
                System.exit(-1);
            }
        });
    }

}
