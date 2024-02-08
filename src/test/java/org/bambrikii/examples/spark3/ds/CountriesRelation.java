package org.bambrikii.examples.spark3.ds;

import lombok.Getter;
import lombok.Setter;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountriesRelation extends BaseRelation implements Serializable, TableScan {
    @Setter
    private SQLContext sqlContext;
    @Setter
    private StructType schema;
    @Getter
    @Setter
    private RecursiveExtensionFilteredLister lister;

    @Override
    public SQLContext sqlContext() {
        return sqlContext;
    }

    @Override
    public StructType schema() {
        return schema;
    }

    @Override
    public RDD<Row> buildScan() {
        schema();
        var table = collectData();
        var sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        var rowRDD = sparkContext
                .parallelize(table)
                .map(file -> getRowFromBean(schema, file));
        return rowRDD.rdd();

    }

    private Row getRowFromBean(StructType schema, CountriesMetadata photo) {
        var arr = Arrays.stream(schema.fields()).map(StructField::name).toList().toArray();
//        return RowFactory.create("1", "2", "3", "4", "5");
        return RowFactory.create(arr);
//        return RowFactory.create(photo.getPath());
    }

    private List<CountriesMetadata> collectData() {
        List<File> files = this.lister.getFiles();
        List<CountriesMetadata> list = new ArrayList<>();
        for (File file : files) {
            var photo = new CountriesMetadata();
            photo.setPath(file.getAbsolutePath());
            list.add(photo);
        }
        return list;
    }
}
