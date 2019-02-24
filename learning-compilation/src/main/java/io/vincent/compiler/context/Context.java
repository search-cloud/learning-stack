package io.vincent.compiler.context;

import io.vincent.compiler.config.Configuration;

/**
 * 编译环境上下文
 *
 * @author Vincent
 * @since 1.0, 12/12/18
 */
public interface Context {

	/**
	 * 获取环境配置信息
	 *
	 * @see Configuration
	 * @return Configuration
	 */
	Configuration configuration();
}
