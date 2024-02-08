package org.bambrikii.examples.spark3.ds;

import lombok.SneakyThrows;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.RelationProvider;
import scala.collection.immutable.Map;

import java.io.File;

import static scala.collection.JavaConverters.mapAsJavaMapConverter;

/**
 * <a href="https://livebook.manning.com/book/spark-in-action-with-examples-in-java/9-advanced-ingestion-finding-data-sources-building-your-own/v-14/205">...</a>
 * <a href="https://livebook.manning.com/book/spark-in-action-with-examples-in-java/chapter-9/133">...</a>
 */
public class CountriesDataSource implements RelationProvider {

    public static final String SCHEMA = "schema";
    public static final String PATH = "path";

    @SneakyThrows
    @Override
    public CountriesRelation createRelation(SQLContext sqlContext, Map<String, String> parameters) {
        var optionsAsJavaMap = mapAsJavaMapConverter(parameters).asJava();
        var br = new CountriesRelation(); //A
        br.setSqlContext(sqlContext); // #
        var schema = SparkBeanUtils.getSchema(Class.forName(optionsAsJavaMap.get(SCHEMA)));
        br.setSchema(schema);
        var photoLister = new RecursiveExtensionFilteredLister();
        String path = optionsAsJavaMap.get(PATH);
        photoLister.getFiles().add(new File(path));
        br.setLister(photoLister);
        return br;
    }
}
