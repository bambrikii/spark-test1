package org.bambrikii.examples.spark3.ds;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecursiveExtensionFilteredLister implements Serializable {
    @Getter
    @Setter
    private List<File> files = new ArrayList<>();
}
