package cn.caijiajia.credittools.service.factory_pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用 @Autowired 实现工场模式
 *
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)//这个必须使用junit4.9以上才有
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
/**
 * 使用 @Autowired & 对象数组的方法实现 - 工厂模式
 *
 */
public class FactoryTest {

	@Autowired 
	private Provider[] providers;

	@Test
	public void factory() {

		for (Provider provider: providers) {
			provider.action();
		}

		System.out.println();

		Drinking drinking = (Drinking) providers[0];
		drinking.action();
		Eating eating = (Eating) providers[1];
		eating.action();
	}

	/* <--------------------------------------->
		drinking....
		eating...


		drinking....
		eating...
	   <---------------------------------------> */

}
