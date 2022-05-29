package model;

import lombok.Getter;

public enum StringVariable {
    undefined("",""),
    hotel("made in" ,""),
    status("which are of \"","\" status"),
    customerType("made by "," clients"),
    countryCode("made by clients from","");

    @Getter
    private final String prefix;
    @Getter
    private final String postfix;


    StringVariable(String prefix, String postfix) {
        this.prefix=prefix;
        this.postfix=postfix;
    }

}
