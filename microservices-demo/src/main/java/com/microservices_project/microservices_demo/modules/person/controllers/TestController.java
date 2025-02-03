package com.microservices_project.microservices_demo.modules.person.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping()
    public String test(@RequestParam(value = "name", defaultValue="test") String name){
        int[] array = {5,3,4,6,2,1};
        for(int i = 0; i < array.length-1; i++) {
            int smallestIndex = getIndexOfTheSmallestOne(array, i+1);
            if(array[i] > array[smallestIndex]) {
                int placeholder = array[i];
                array[i] = array[smallestIndex];
                array[smallestIndex] = placeholder;
            }
            
        }
        return "test " + name;
    }
    
    private int getIndexOfTheSmallestOne(int[] array, int currentIndex){
        int smallest =array[currentIndex];
        int index = currentIndex;
        for(int i = currentIndex + 1; i < array.length; i++){
            if(smallest > currentIndex) {
                smallest = array[i];
                index = i;
            }
        }
        
        return index;
    }
}
