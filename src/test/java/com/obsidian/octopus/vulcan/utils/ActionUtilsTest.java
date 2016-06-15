package com.obsidian.octopus.vulcan.utils;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.annotation.Parameter;
import com.obsidian.octopus.vulcan.annotation.Response;
import com.obsidian.octopus.vulcan.core.Action;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class ActionUtilsTest {

    public ActionUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of inject method, of class ActionUtils.
     */
    @Test
    public void testInject() {
        System.out.println("inject");
        ActionTest action = new ActionTest();
        JSONObject param = new JSONObject();
        param.put("i", 1);
        param.put("jj", 2);
        param.put("ll", 3);
        ActionUtils.inject(action, param);
        assertEquals(1, action.i);
        assertEquals(2, action.j);
        assertEquals(null, action.k);
        assertEquals(3, action.l);
    }

    public static class ActionTest implements Action {

        @Parameter
        private int i;
        @Parameter(name = "jj")
        private int j;
        @Parameter(optional = true)
        private Integer k;
        @Parameter(name = "ll", optional = true)
        private int l;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public Integer getK() {
            return k;
        }

        public void setK(Integer k) {
            this.k = k;
        }

        public int getL() {
            return l;
        }

        public void setL(int l) {
            this.l = l;
        }

        @Override
        public boolean execute() throws Exception {
            return true;
        }

        @Response
        public String getResult() {
            return "haha";
        }

        @Response(name = "test")
        public String getResult2() {
            return "haha2";
        }

    }

}
