import resourcetable.ResourceTable;

import java.util.Arrays;

/**
 * by yidi on 5/9/19
 */

public class LamdaTest {
    public static void main(String[] args) {
        String test = "test";
        String[] result = test.split("");
        Arrays.stream(result).forEach(e -> System.out.println(e + "*"));

        ResourceTable.queryFileNameByKeyword("hello world");
    }
}
