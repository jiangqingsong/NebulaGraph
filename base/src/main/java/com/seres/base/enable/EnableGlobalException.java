package com.seres.base.enable;

import com.seres.base.exception.DevBaseGlobalExceptionHandler;
import com.seres.base.exception.NoCatchException;
import com.seres.base.exception.OtherBaseGlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DevBaseGlobalExceptionHandler.class, OtherBaseGlobalExceptionHandler.class, NoCatchException.class})
public @interface EnableGlobalException {

}
