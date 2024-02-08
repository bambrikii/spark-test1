package org.bambrikii.examples.spark3;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class Spark2Test {
    @Test
    public void shouldReadAndWrite() {
        var targetFile = "/home/asd/temp/1/temp.csv";
        try {
            FileUtils.deleteDirectory(Paths.get(targetFile).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var start = System.currentTimeMillis();
        int instances = Runtime.getRuntime().availableProcessors();
        var spark = SparkSession
                .builder()
                .config("spark.dynamicAllocation.enabled", "true")
                .config("spark.executor.instances", instances)
                .config("spark.executor.cores", instances)
                .config("spark.dynamicAllocation.minExecutors", instances)
                .config("spark.dynamicAllocation.maxExecutors", instances)
                .appName("Application Name")
                .config("spark.master", "local")
                .config("some-config", "some-value")
                .getOrCreate();

        var someSparkService = new SomeSparkService();
        spark
                .sqlContext()
                .udf()
                .register("concatRow", someSparkService.concatRow(), DataTypes.StringType);
        spark
                .sqlContext()
                .udf().register("closeCoords", someSparkService.closeCoords(), DataTypes.DoubleType);

        spark
                .read()
                .option("header", true)
                .csv("/home/asd/temp/1/revised.csv")
                .coalesce(instances)
                .withColumn("time_ref", functions.col("time_ref"))
                .withColumn("account", functions.col("account"))
                .withColumn("code", functions.col("code"))
                .withColumn("country_code", functions.col("country_code"))
                .withColumn("product_type", functions.col("product_type"))
                .withColumn("value", functions.col("value"))
                .withColumn("status", functions.col("status"))
                .coalesce(1)
                .write()
                .csv(targetFile);

        long diff = System.currentTimeMillis() - start;
        System.out.println("Processed in " + diff + " millis");
        log.info("Processed in {0} millis", diff);
    }
}
