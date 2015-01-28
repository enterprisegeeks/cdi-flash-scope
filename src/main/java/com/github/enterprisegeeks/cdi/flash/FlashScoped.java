package com.github.enterprisegeeks.cdi.flash;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Scope;

/**
 * FlashScope CDI Scope Anotation.
 * 
 * this scope is Active from first request 
 * to next redirect request.
 * Or if first request is not redirect, scope is active firest request.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface FlashScoped {}
