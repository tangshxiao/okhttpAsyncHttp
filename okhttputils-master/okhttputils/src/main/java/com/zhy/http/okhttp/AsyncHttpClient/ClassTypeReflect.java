package com.zhy.http.okhttp.AsyncHttpClient;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class ClassTypeReflect {

    static Type getModelClazz(Class<?> subclass) {
        Type type = getGenericType(0, subclass);
        return type;
    }

    private static Type getGenericType(int index, Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        return params[index];
    }

}
