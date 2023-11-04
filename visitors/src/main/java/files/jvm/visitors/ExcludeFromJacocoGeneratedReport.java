package files.jvm.visitors;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Annotation that removes a method from Code Coverage Report.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeFromJacocoGeneratedReport {
}