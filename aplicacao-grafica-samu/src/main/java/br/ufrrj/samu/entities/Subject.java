package br.ufrrj.samu.entities;

import br.ufrrj.samu.services.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subject {

    private static final Logger LOGGER = LogManager.getLogger(Subject.class);

    private String name;
    private String description;
    private String code;
    private String schedule;

    private ArrayList<String> prerequisites;


    public Subject(String name, String description, String code, String schedule) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.schedule = schedule;
        this.prerequisites = new ArrayList<>();
    }

    public Subject(String name, String description, String code, List<String> prerequisite, String schedule) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.schedule = schedule;

        // IDK if is better to create than copy the old arraylist
        this.prerequisites = new ArrayList<>(prerequisite);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrerequisitesList() {

        StringBuilder stringBuilder = new StringBuilder();

        for(String preReq : this.prerequisites) {
            stringBuilder.append(preReq + ",");
        }

        if(!stringBuilder.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    public String getProfessor() {
        return "Filipe Braida do Carmo";
    }

    public String getSchedule() {
        return schedule;
    }

    //TODO: Maybe in the future we need to get prereqs informations (like name) maybe a method to do this would be a good thing

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", prerequisites=" + prerequisites +
                '}';
    }
}
