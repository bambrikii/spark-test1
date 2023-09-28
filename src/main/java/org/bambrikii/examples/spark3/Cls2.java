package org.bambrikii.examples.spark3;

import java.io.Serializable;

class Cls2 implements Serializable {
    private String str1 = "123";

    @Override
    public String toString() {
        return "Cls2 " + str1;
    }
}
