package com.jindreamit.practice.annotation;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

@RequirePermission(permissions = {"user:add"})
public class RequirePermissionTest {

    @Test
    public void test(){
        System.out.println(RequirePermissionTest.class.isAnnotationPresent(RequirePermission.class));
        RequirePermission requirePermission=RequirePermissionTest.class.getAnnotation(RequirePermission.class);
        System.out.println(Arrays.toString(requirePermission.permissions()));

        Method []methods=RequirePermissionTest.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequirePermission.class)) {
                RequirePermission requirePermission1 = method.getAnnotation(RequirePermission.class);
                System.out.println(Arrays.toString(requirePermission1.permissions()));
            }
        }
    }

    @RequirePermission(permissions = {"admin:del"})
    public void add(){

    }

}
