package org.bambrikii.examples.spark3;

import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.api.java.UDF5;

import java.io.Serializable;
import java.text.MessageFormat;

public class SomeSparkService implements Serializable {
    private Cls1 cls1 = new Cls1();

    public UDF5<String, String, String, String, String, String> concatRow() {
        return (String country, String latitude, String longitude, String name, String cls2) -> {
            return MessageFormat.format("{0} {1} {2} {3}", country, latitude, longitude, name) + new Cls1() + cls1 + cls2;
        };
    }

    public UDF2<String, String, Double> closeCoords() {
        return (latitude, longitude) -> latitude == null ? 0 : ((int) Double.parseDouble(latitude) / 10) * 10.0;
    }
}
