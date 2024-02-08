package org.bambrikii.examples.spark3.ds;

import org.apache.spark.sql.types.StructType;

import java.util.HashMap;
import java.util.Map;

public class SparkBeanUtils {
    private SparkBeanUtils() {
    }

    private static final Map<Class<?>, StructType> schemas = new HashMap<>();

    public static StructType getSchema(Class<?> cls) {
        return schemas.get(cls);
    }

    public static StructType setSchema(Class<?> cls, StructType schema) {
        return schemas.put(cls, schema);
    }
}
