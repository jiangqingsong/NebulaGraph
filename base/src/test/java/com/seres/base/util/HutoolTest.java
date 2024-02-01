package com.seres.base.util;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class HutoolTest {

	@Test
	public void collUtil() {
		List<String> aa = null;
		log.info("{}", CollUtil.isEmpty(aa));

		List<String> bb = Collections.emptyList();
//		bb.add("bb"); // 空集合不能添加元素
		log.info("{}", bb);
		Collection<String> list = CollUtil.create(String.class);
		log.info("{}", CollUtil.isEmpty(list));
		list.add("1");
		log.info("{}", CollUtil.isEmpty(list));
		CollUtil.addAll(list, new String[]{"2", "3"});
		log.info("{}", list);

		log.info("{}", CollUtil.toList(1, 2));
		log.info("{}", CollUtil.max(CollUtil.toList(3, 1, 2)));
		log.info("{}", CollUtil.toList(new String[]{"3", "4"}));
	}

	@Test
	public void collStreamUtil() {
		List<Integer> list = CollUtil.toList(1, 2);
		List<String> l2 = CollStreamUtil.toList(list, String::valueOf);
		log.info("{}", l2);
		Map<String, Integer> map = CollStreamUtil.toMap(list, String::valueOf, Function.identity());
		log.info("{}", map);
		Map<String, Integer> map2 = CollStreamUtil.toIdentityMap(list, String::valueOf); // 等价上面的toMap
		log.info("{}", map2);

	}


	@Data
	class Obj {
		private Integer id;
		private Integer org;
		private String name;
	}

}
