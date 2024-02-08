package org.bambrikii.examples.spark3.ds;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class CountriesMetadata implements Serializable {
    @Getter
    @Setter
    private String path;
}
