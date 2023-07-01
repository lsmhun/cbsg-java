package com.acme.cbsg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CbsgResourceUtil {

    public List<String> stringList(final String resourceFileName){
        Scanner scanner = getScannerForResourceFile(resourceFileName);
        List<String> res = new ArrayList<>();
        while (scanner.hasNext()){
            res.add(scanner.nextLine());
        }
        return res;
    }

    private Scanner getScannerForResourceFile(String resourceFileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(resourceFileName);
        Scanner scanner = new Scanner(is);
        //todo: enable this null check
//        if(is == null){
//            scanner = new Scanner("");
//        } else {
//         scanner = new Scanner(is);
//        }
        return scanner;
    }
}
