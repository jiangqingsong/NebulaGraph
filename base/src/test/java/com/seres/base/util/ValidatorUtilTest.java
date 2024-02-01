package com.seres.base.util;

import com.seres.base.exception.AppException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;

/**
 * 验证测试
 * @author ：liyu
 * @date ：2023/4/26 16:54
 */
@Slf4j
public class ValidatorUtilTest {

	@Test
	public void test(){
		log.info("{}", ValidatorUtil.check(new User()));
		log.info("{}", ValidatorUtil.checkToString(new User()));
		log.info("{}", ValidatorUtil.check(new User(){{
			setAge(200);
		}}));
		log.info("{}", ValidatorUtil.check(new User(){{
			setName("123456");
			setAge(10);
		}}));
		log.info("{}", ValidatorUtil.check(new User(){{
			setId(1);
			setName("zs");
			setAge(10);
			setAddr(new Addr());
		}}));
		Assertions.assertNull(ValidatorUtil.check(new User(){{
			setId(1);
			setName("zs");
			setAge(10);
			setAddr(new Addr(){{
				setCode("110");
				setName("cq");
			}});
		}}));
	}

	@Test
	public void testException(){
		ValidatorUtil.validate(new User(){{
			setId(1);
			setName("zs");
			setAge(10);
			setAddr(new Addr(){{
				setCode("110");
				setName("cq");
			}});
		}});
		try {
			ValidatorUtil.validate(new User());
		}catch (AppException e){
			log.error("{} -> {}", e.getCode(), e.getMessage());
		}
		Assertions.assertThrows(AppException.class, ()-> ValidatorUtil.validate(new User()));
	}

	@Test
	public void testProperty(){
		Assertions.assertNull(ValidatorUtil.check(new User(), "id"));
		log.info("{}", ValidatorUtil.check(new User(), "age"));
		log.info("{}", ValidatorUtil.check(new User(){{
			setId(1);
			setName("zs");
			setAge(10);
			setAddr(new Addr());
		}}, "addr.code"));
	}

	@Test
	public void testGroup(){
		log.info("{}", ValidatorUtil.check(new User(), AddGroup.class));
		log.info("{}", ValidatorUtil.check(new User(), UpdateGroup.class));
		log.info("{}", ValidatorUtil.check(new User(), Default.class, AddGroup.class));
		log.info("{}", ValidatorUtil.check(new User(){{
			setAddr(new Addr());
		}}, Default.class, AddGroup.class));
		Assertions.assertNull(ValidatorUtil.check(new User(){{
			setName("zs");
			setAge(10);
			setAddr(new Addr(){{
				setCode("110");
				setName("cq");
			}});
		}}, Default.class, AddGroup.class));
	}

	@Data
	class User {

		@NotNull(groups = UpdateGroup.class) // 特定组下不能为空，不指定则为默认组Default.class
		private Integer id;

		@NotEmpty
		@Size(min = 1, max = 5)
		private String name;

		@Min(0)
		@Max(150)
		@NotNull
		private Integer age;

		@Valid  // 添加子对象嵌套验证，缺省不嵌套验证
		@NotNull(groups = {AddGroup.class, UpdateGroup.class}) // 特定组下不能为空
		private Addr addr;

	}

	@Data
	class Addr {

		@NotNull
		private String code;
		@NotEmpty
		private String name;
	}

	// 定义组
	public interface AddGroup {
	}
	public interface UpdateGroup {
	}

}
