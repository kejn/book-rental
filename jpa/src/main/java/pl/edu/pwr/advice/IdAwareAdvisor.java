package pl.edu.pwr.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.edu.pwr.annotation.NullableId;
import pl.edu.pwr.common.IdAware;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.UserBookLibraryEntity;
import pl.edu.pwr.exception.NotNullIdException;

@Aspect
@Component
public class IdAwareAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(IdAwareAdvisor.class);

	private void logBeforeAdvice(JoinPoint joinPoint, Object object) {
		logger.info("Advice before {}.{}() method - arg0 [{}]", joinPoint.getTarget().getClass().getSimpleName(),
		    joinPoint.getSignature().getName(), object.toString());
	}

	private void logAfterAdvice() {
		logger.info("No interruptions.");
	}

	@Pointcut("execution(** pl.edu.pwr..*.*(..))")
	public void anyMethodInPackage() {
	}

	@Pointcut(value = "anyMethodInPackage() && args(object,..) && @annotation(nullableId)", argNames = "object,nullableId")
	public void nullableIdMethodExecution(Object object, NullableId nullableId) {
	}

	/**
	 * Called before method annotated with <b>nullableId</b> annotation is called.
	 * Checks if its IdAware argument contains unassigned (null) member field
	 * <b>id</b>
	 *
	 * @param joinPoint
	 *          join point. let determine eg. method name before which this method
	 *          is executed
	 *
	 * @param object
	 *          first argument the annotated method was called with
	 * 
	 * @param nullableId
	 *          NullableId annotation wrapping method being called
	 */
	@Before("nullableIdMethodExecution(object,nullableId)")
	public void beforeNullableId(JoinPoint joinPoint, Object object, NullableId nullableId) throws Throwable {
		logBeforeAdvice(joinPoint, object);
		if ((object instanceof IdAware<?>) && !(object instanceof BookLibraryEntity) && !(object instanceof UserBookLibraryEntity)) {
			IdAware<?> idAware = (IdAware<?>) object;
			checkNotNullId(idAware);
		}
		logAfterAdvice();
	}

	/**
	 * Checks if idAware has assigned <b>id</b> member field
	 * 
	 * @param idAware
	 *          some IdAware instance
	 */
	private void checkNotNullId(IdAware<?> idAware) {
		if (idAware.getId() != null) {
			throw new NotNullIdException();
		}
	}

}
