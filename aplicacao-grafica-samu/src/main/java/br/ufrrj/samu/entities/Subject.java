package br.ufrrj.samu.entities;

import br.ufrrj.samu.services.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Subject {

    private static final Logger LOGGER = LogManager.getLogger(Subject.class);

    private String name;
    private String description;
    private String code;

    private HashMap<String, Subject> prerequisite;

    public Subject(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;

        this.prerequisite = new HashMap<String,Subject>();
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

    public void addPrerequisite(Subject subject) {
        this.prerequisite.put(subject.getCode(), subject);
    }

    public HashMap<String, Subject> getPrerequisite() {
        return prerequisite;
    }

    public String getPrerequisiteCodes() {

        StringBuilder stringBuilder = new StringBuilder();

        for (HashMap.Entry<String,Subject> pair : this.prerequisite.entrySet()) {
            stringBuilder.append(pair.getKey() + ",");
        }

        if(!stringBuilder.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
