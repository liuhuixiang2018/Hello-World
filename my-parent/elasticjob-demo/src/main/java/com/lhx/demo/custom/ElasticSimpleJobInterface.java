package com.lhx.demo.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义接口
 *
 * @Retention注解：使用该注解来声明自定义注解的生命周期（3种可选：
 * RetentionPolicy.RUNTIME:始终不会丢弃，运行期间也保留，因此可以使用反射来获取该注解的信息
 * RetentionPolicy.CLASS:在类加载时丢弃，在字节码的处理文件中有用，默认使用此种方式
 * RetentionPolicy.SOURCE：在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码）
 *
 * @Target注解:使用该注解来声明自定义注解的使用范围（类、类成员变量、方法、参数上，，，，8种可选：
 * ElementType.TYPE:用于描述类、接口或enum声明
 * ElementType.FIELD:成员变量、对象、属性
 * ElementType.METHOD:用于描述方法
 * ......
 * ......
 * ......
 *
 * ）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElasticSimpleJobInterface {

    String jobName() default "";

    int shardingTotalCount() default 0;

    String cron() default "";


}

