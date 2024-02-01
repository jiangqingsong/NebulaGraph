package com.seres.base.util;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于注解的对象属性值验证工具
 * @author ：liyu
 * @date ：2023/4/26 16:49
 */
public class ValidatorUtil {
	// 线程安全，spring boot项目也可直接注入使用 @Autowired private Validator validator;
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 检查对象属性是否满足注解要求，返回null则表示验证通过，否则返回验收失败的所有属性及其失败描述
	 * @param obj
	 * @param groups 缺省为Default.class，否则验证指定组下的规则，多个则需同时满足
	 * @param <T>
	 * @return 为null则表示验证通过
	 */
	public static <T> Map<String, String> check(T obj, Class<?>... groups){
		Set<ConstraintViolation<T>> cvs = VALIDATOR.validate(obj, groups);
		if(null == cvs || cvs.isEmpty()){
			return null;
		}
		Map<String,String> errorMap = new HashMap<>();
		cvs.forEach(cv->{
			errorMap.put(cv.getPropertyPath().toString(), cv.getMessage());
		});
		return errorMap;
	}

	/**
	 * 检查对象属性是否满足注解要求，返回null则表示验证通过，否则返回验收失败的所有属性及其失败描述
	 * @param obj
	 * @param groups 缺省为Default.class，否则验证指定组下的规则，多个则需同时满足
	 * @param <T>
	 * @return 为null则表示验证通过，否则返回如下格式：name=不能为空,id=不能为null,addr=不能为null
	 */
	public static <T> String checkToString(T obj, Class<?>... groups){
		Map<String, String> msg = check(obj, groups);
		if(null == msg || msg.isEmpty()){
			return null;
		}
		return msg.entrySet().stream().map(en-> en.getKey() + "=" + en.getValue()).collect(Collectors.joining(","));
	}

	/**
	 * 检查对象特定属性是否满足注解要求，返回null则表示验证通过，否则返回失败信息
	 * @param obj
	 * @param propertyName
	 * @param groups 缺省为Default.class，否则验证指定组下的规则，多个则需同时满足
	 * @param <T>
	 * @return 为null则表示验证通过，否则返回失败信息
	 */
	public static <T> String check(T obj, String propertyName, Class<?>... groups){
		Set<ConstraintViolation<T>> cvs = VALIDATOR.validateProperty(obj, propertyName, groups);
		if(null == cvs || cvs.isEmpty()){
			return null;
		}
		return cvs.stream().findFirst().get().getMessage();
	}

	/**
	 * 验证对象属性是否满足注解要求，不通过则抛出异常，异常描述如：参数校验失败：name=不能为空,id=不能为null,addr=不能为null
	 * @param obj
	 * @param groups 缺省为Default.class，否则验证指定组下的规则，多个则需同时满足
	 * @param <T>
	 */
	public static <T> void validate(T obj, Class<?>... groups){
		String err = checkToString(obj, groups);
		if(StringUtil.isNotEmpty(err)){
			throw new AppException(BaseErrCode.PARAM_VALIDATE_ERR, err, "：");
		}
	}

	/**
	 * 验证对象属性是否满足注解要求，不通过则抛出异常，异常描述如：参数校验失败：name=不能为空,id=不能为null,addr=不能为null
	 * @param obj
	 * @param groups 缺省为Default.class，否则验证指定组下的规则，多个则需同时满足
	 * @param <T>
	 */
	public static <T> void validate(T obj, String propertyName, Class<?>... groups){
		String err = check(obj, propertyName, groups);
		if(StringUtil.isNotEmpty(err)){
			throw AppException.build(BaseErrCode.PARAM_VALIDATE_ERR, err, "：");
		}
	}

}
