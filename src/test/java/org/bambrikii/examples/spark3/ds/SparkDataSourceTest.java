package org.bambrikii.examples.spark3.ds;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeoutException;

public class SparkDataSourceTest {
    @Test
    public void shouldReadCustomFormat() {
        var spark = SparkSession
                .builder()
                .config("spark.dynamicAllocation.enabled", "true")
                .appName("Application Name")
                .config("spark.master", "local")
                .config("some-config", "some-value")
                .getOrCreate();

        var targetFile = "/home/asd/temp/1/temp.csv";
        var ds = spark
                .read()
                .format("org.bambrikii.examples.spark3.ds")
                .load(targetFile);

        ds.show();
    }

    @Test
    public void shouldReadCustomFormat2() throws InterruptedException, TimeoutException, StreamingQueryException {
        var spark = SparkSession
                .builder()
                .config("spark.dynamicAllocation.enabled", "true")
                .appName("Application Name")
                .config("spark.master", "local")
                .config("some-config", "some-value")
                .getOrCreate();

        var schema = new StructType(new StructField[]{
                new StructField("country", DataTypes.StringType, true, Metadata.empty()),
                new StructField("latitude", DataTypes.StringType, true, Metadata.empty()),
                new StructField("longitude", DataTypes.StringType, true, Metadata.empty()),
                new StructField("name", DataTypes.StringType, true, Metadata.empty()),
        });
        String targetFile = "/home/asd/workspace/spark-test1/src/test/resources/countries.csv";
        var ds = spark
                .read()
                .schema(schema)
                .format("csv")
                .load(targetFile);
//        var query1 = ds.start();
//        query1
        ds.show();
//        var query = ds.writeStream().format("console").start();
//        query.awaitTermination();
    }


    @Test
    public void shouldTestCustom() {
        var targetFile = "/home/asd/workspaceSpark/spark-test1/src/test/resources/countries.csv";
        var spark = SparkSession
                .builder()
                .config("spark.dynamicAllocation.enabled", "true")
                .appName("Application Name")
                .config("spark.master", "local")
                .config("some-config", "some-value")
                .getOrCreate();

        var schema = new StructType(new StructField[]{
                new StructField("country", DataTypes.StringType, true, Metadata.empty()),
                new StructField("latitude", DataTypes.StringType, true, Metadata.empty()),
                new StructField("longitude", DataTypes.StringType, true, Metadata.empty()),
                new StructField("name", DataTypes.StringType, true, Metadata.empty()),
        });

        SparkBeanUtils.setSchema(CountriesMetadata.class, schema);

        Dataset<Row> df = spark
                .read()
//                .format("org.bambrikii.examples.spark3.ds.CountriesDataSourceAdvertiser")
                .format("countries")
                .option("schema", CountriesMetadata.class.getName())
                .option("path", targetFile)
                .load();

        df.show();
    }
}
