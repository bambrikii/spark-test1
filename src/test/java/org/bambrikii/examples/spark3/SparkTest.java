package org.bambrikii.examples.spark3;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

public class SparkTest implements Serializable {
    @Test
    public void should1() {
        SparkSession spark = SparkSession
                .builder()
                .appName("Application Name")
                .config("spark.master", "local")
                .config("some-config", "some-value")
                .getOrCreate();

        SomeSparkService someSparkService = new SomeSparkService();
        spark
                .sqlContext()
                .udf()
                .register("concatRow", someSparkService.concatRow(), DataTypes.StringType);
        spark
                .sqlContext()
                .udf().register("closeCoords", someSparkService.closeCoords(), DataTypes.DoubleType);

        Dataset<Row> csv = spark
                .read()
                .option("header", true)
                .csv("src/test/resources/countries.csv");
        csv.show();
        csv = csv
//                .filter(csv.col("_c0").ilike("AM"))
                .withColumn("concatRow", functions.callUDF("concatRow", csv.col("country"), csv.col("latitude"), csv.col("longitude"), csv.col("name"), functions.lit("new Cls2()")))
                .withColumn("closeCoords", functions.callUDF("closeCoords", csv.col("latitude"), csv.col("longitude")))
        ;
        csv.show();
        csv = csv.groupBy(csv.col("closeCoords")).count().sort(csv.col("closeCoords"));
        csv.show();
    }


}
