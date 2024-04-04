package com.zz.psmback.common.utils.psmAnnotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthProjects {
    AuthProject[] value();
}
