package org.bambrikii.examples.spark3;

import java.io.Serializable;

class Cls1 implements Serializable {
    private String str1 = "123";

    @Override
    public String toString() {
        return "Cls1 " + str1;
    }
}
