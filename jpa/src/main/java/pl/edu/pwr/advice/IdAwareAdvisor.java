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
import pl.edu.pwr.exception.NotNullIdException;

@Aspect
@Component
public class IdAwareAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(IdAwareAdvisor.class);

	@Pointcut("execution(** pl.edu.pwr.dao..*.*(..))")
	public void daoMethodExecution() {
	}

	/**
	 * Called before method annotated with <b>nullableId</b> annotation is
	 * called. Checks if its IdAware argument contains unassigned (null) member
	 * field <b>id</b>
	 *
	 * @param joinPoint
	 *            join point. let determine eg. method name before which
	 *
	 * @param object
	 *            first argument the annotated method was called with
	 * 
	 * @param nullableId
	 *            NullableId annotation wrapping method being called
	 */
	@Before(value = "daoMethodExecution() && args(object,..) && @annotation(nullableId)", argNames = "object,nullableId")
	public void beforeNullableId(JoinPoint joinPoint, Object object, NullableId nullableId) throws Throwable {
		logger.info("Advice before {}.{}() method - arg0 [{}]",
				joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), object.toString());
		if (object instanceof IdAware<?>) {
			IdAware<?> idAware = (IdAware<?>) object;
			checkNotNullId(idAware);
		}
		logger.info("No interruptions.");
	}

	/**
	 * Checks if idAware has assigned <b>id</b> member field
	 * 
	 * @param idAware
	 *            some IdAware instance
	 */
	private void checkNotNullId(IdAware<?> idAware) {
		if (idAware.getId() != null) {
			throw new NotNullIdException();
		}
	}

}
