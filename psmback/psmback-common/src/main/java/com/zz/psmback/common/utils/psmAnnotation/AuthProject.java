package com.zz.psmback.common.utils.psmAnnotation;

import java.lang.annotation.*;

@Repeatable(AuthProjects.class)
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthProject {
    String value();
}
