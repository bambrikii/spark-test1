package org.bambrikii.examples.spark3.ds;

import org.apache.spark.sql.sources.DataSourceRegister;

public class CountriesDataSourceAdvertiser extends CountriesDataSource implements DataSourceRegister {
    @Override
    public String shortName() {
        return "countries";
    }
}
