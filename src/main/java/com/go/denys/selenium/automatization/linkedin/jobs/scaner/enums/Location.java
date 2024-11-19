package com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums;

public enum Location {
    AUSTRIA("at"),
    LUXEMBOURG("lu"),
    BELGIUM("be"),
    ALL("bg,hr,cy,dk,ee,fi,el,hu,is,ie,it,lv,li,lt,lu,mt,no,pl,pt,ro,sk,si,es,ch,NS"),
    DE("de"),
    EN("en"),
    FR("fr"),
    ES("es"),
    IT("it"),
    JP("jp"),
    NL("nl"),
    PL("pl"),
    PT("pt"),
    RO("ro"),
    RU("ru"),
    SV("sv"),
    TR("tr"),
    UA("ua"),
    CZ("cz");


    private String code;

    private Location(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
