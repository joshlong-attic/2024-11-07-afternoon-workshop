package smart;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Logged {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean name in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";

}
