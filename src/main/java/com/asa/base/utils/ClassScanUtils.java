package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author andrew_asa
 * @date 2021/10/25.
 */
public class ClassScanUtils {

    private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private static String resourcePattern = "**/*.class";

    /**
     * 所有类放行
     */
    public static Predicate<Class<?>> ALL_CLASS_ACCEPT_FILTER = aClass -> true;

    /**
     * 所有字符串放行
     */
    public static Predicate<String> ALL_STRING_ACCEPT_FILTER = s -> true;

    private static Consumer<Class<?>> DO_NOTHING_CONSUMER = aClass -> {
    };

    private static ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private static CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

    public static Set<Class<?>> getClasses(String basePackage) throws Exception {

        return getClasses(basePackage, ALL_CLASS_ACCEPT_FILTER);
    }

    /**
     * @param basePackage
     * @param filter
     * @return
     * @throws Exception
     */
    public static Set<Class<?>> getClasses(String basePackage, Predicate<Class<?>> filter) throws Exception {

        Set<Class<?>> ret = new HashSet<Class<?>>();
        traverseClass(basePackage, filter, aClass -> ret.add(aClass));
        return ret;
    }

    public static void traverseClass(String basePackage, Consumer<Class<?>> consumer) throws Exception {

        traverseClass(basePackage, ALL_CLASS_ACCEPT_FILTER, consumer);
    }

    /**
     * 遍历jar包中的类
     *
     * @param basePackage
     * @param filter      过滤器
     * @param consumer    消费器
     * @throws Exception
     */
    public static void traverseClass(String basePackage,
                                     Predicate<Class<?>> filter,
                                     Consumer<Class<?>> consumer) throws Exception {

        String searchPath = getPackageSearchPath(basePackage);
        Resource[] resources = resolver.getResources(searchPath);
        if (filter == null) {
            filter = ALL_CLASS_ACCEPT_FILTER;
        }
        if (consumer == null) {
            consumer = DO_NOTHING_CONSUMER;
        }
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = ClassUtils.forName(className, beanClassLoader);
                    if (filter.test(clazz)) {
                        consumer.accept(clazz);
                    }
                } catch (Throwable ex) {
                    LoggerFactory.getLogger().error(
                            "Failed to read candidate component class: " + resource, ex);
                }
            } else {
                LoggerFactory.getLogger().info("Ignored because not readable: " + resource);
            }
        }
    }

    public static String getPackageSearchPath(String basePackage) {

        String packageSearchPath = "classpath*:" + resolveBasePackage(basePackage) + "/" + resourcePattern;
        return packageSearchPath;
    }

    public static String resolveBasePackage(String basePackage) {

        StandardEnvironment environment = new StandardEnvironment();
        return com.asa.base.utils.ClassUtils.classNameToResourcePath(environment.resolveRequiredPlaceholders(basePackage));
    }
}
