package com.yu.usercenter.common;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception{
        Class c = Teacher.class;
      Constructor constructor= c.getDeclaredConstructor();
      constructor.setAccessible(true);
      Teacher t =(Teacher) constructor.newInstance();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName()+"===>"+field.getType());
        }
        Method[]methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName()+"==>"+method.getReturnType()+"==>"+method.getParameterCount());
        }
        Method m =c.getDeclaredMethod("getName");
    }

}
