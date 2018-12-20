package com.ge.digital.gearbox.util;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ge.digital.gearbox.SpringUtil;

public class ArchiveUtils {

	/**
	 * 动态创建对应的Bean对象
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	public static Object getJpaBean(String beanName) throws Exception {
		Class claszz = Class.forName("com.ge.digital.gearbox.entity.archive.Jpa" + beanName);
		Object object = claszz.newInstance();
		return object;
	}

	/**
	 * 动态获取JpaRepository
	 * @param beanName 
	 * @return
	 * @throws Exception
	 */
	public static <T> JpaRepository<T, Long> getJpaRepository(String beanName) throws Exception {
		JpaRepository jpa = (JpaRepository) SpringUtil.getBean("jpa" + beanName + "Repository", JpaRepository.class);
		return jpa;
	}
}
