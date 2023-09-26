package org.com.jsfspring.enums;

/**
 * должности 
 */
public enum Position {
    BACK("бэкенд"),
    FRONT("фронт"),
    PM("ПМ"),
    DESIGNER("Дизайнер"),
    QA("QA"),
    DATA_ANALITIK("Дата аналитик"),
    INTERN("Стажер");

    private String name;

    Position(String name){
        this.name = name;
    }

    private String getName(){
        return name;
    }
}
