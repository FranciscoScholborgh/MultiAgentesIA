/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataFormat.converter;

import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author frank
 */
public class ArrayListConverter {
    
    public static ArrayList<?> convertObservableListToArrayList (ObservableList<?> toConvert) { 
        ArrayList<String> result = new ArrayList<>();
        toConvert.forEach((element) -> {
            result.add((String) element);
        });
        return result;
    }  
}
