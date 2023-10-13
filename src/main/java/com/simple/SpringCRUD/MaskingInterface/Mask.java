package com.simple.SpringCRUD.MaskingInterface;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotationsInside
@JsonSerialize(using = MaskingSerializer.class) // Implementation of this Annotation in done in this class.
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mask {
}
