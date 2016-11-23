package com.hl.common.spring;

import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;

public class MyPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {

		beanFactoryToProcess
				.addBeanPostProcessor(new PropertyCfgBeanPostProcessor(
						beanFactoryToProcess.getTypeConverter(), props));

		super.processProperties(beanFactoryToProcess, props);
	}

	private class PropertyCfgBeanPostProcessor implements BeanPostProcessor {

		TypeConverter typeConverter;

		Properties props;

		public PropertyCfgBeanPostProcessor(TypeConverter typeConverter,
				Properties props) {
			super();
			this.typeConverter = typeConverter;
			this.props = props;

		}

		@Override
		public Object postProcessBeforeInitialization(Object bean,
				String beanName) throws BeansException {
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean,
				String beanName) throws BeansException {
			for (Field f : bean.getClass().getDeclaredFields()) {
				PropertyCfg cfg = f.getAnnotation(PropertyCfg.class);
				if (cfg != null && StringUtils.hasText(cfg.value())) {
					String value = props.getProperty(cfg.value());
					if (value != null) {
						f.setAccessible(true);
						Object nvalue = typeConverter.convertIfNecessary(value,
								f.getType());
						if (nvalue != null) {
							try {
								f.set(bean, nvalue);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return bean;
		}

	}
}
