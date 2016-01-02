package pl.edu.pwr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by IdAwareAdvisor advice to check if annotated method's first argument
 * member field <b>id</b> is unassigned (null).
 * 
 * @author KNIEMCZY
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableId {
}
