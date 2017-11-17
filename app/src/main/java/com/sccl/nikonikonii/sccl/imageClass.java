package com.sccl.nikonikonii.sccl;

/**
 * Created by Admin on 17/11/2017.
 */

public class imageClass {
    private int resource;
    private String word;

    public imageClass(){
    }

    public imageClass(int resource, String word){
        this.resource = resource;
        this.word = word;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
