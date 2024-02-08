package org.bambrikii.examples.spark3.ds;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SparkSession;

public class MyDataFrameReader extends DataFrameReader {

    public MyDataFrameReader(SparkSession sparkSession) {
        super(sparkSession);
    }


}
