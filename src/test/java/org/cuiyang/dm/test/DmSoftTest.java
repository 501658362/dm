package org.cuiyang.dm.test;

import org.cuiyang.dm.DmSoft;

/**
 * test
 *
 * @author cuiyang
 */
public class DmSoftTest {

    public static void main(String[] args) {
        DmSoft dm = new DmSoft();
        System.out.println("version:" + dm.ver());
        System.out.println("path:" + dm.getBasePath());
    }
}
