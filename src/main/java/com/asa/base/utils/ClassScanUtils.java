package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.HashSet;
import java.util.Set;

/**
 * @author andrew_asa
 * @date 2021/10/25.
 */
public class ClassScanUtils {

    private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private static String resourcePattern = "**/*.class";

    private static ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private static CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

    public static Set<Class<?>> getClasses(String basePackage) throws Exception {

        Set<Class<?>> ret = new HashSet<Class<?>>();
        String searchPath = getPackageSearchPath(basePackage);
        Resource[] resources = resolver.getResources(searchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = ClassUtils.forName(className, beanClassLoader);
                    ret.add(clazz);
                } catch (Throwable ex) {
                    LoggerFactory.getLogger().error(
                            "Failed to read candidate component class: " + resource, ex);
                }
            } else {
                LoggerFactory.getLogger().info("Ignored because not readable: " + resource);
            }
        }
        return ret;
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
