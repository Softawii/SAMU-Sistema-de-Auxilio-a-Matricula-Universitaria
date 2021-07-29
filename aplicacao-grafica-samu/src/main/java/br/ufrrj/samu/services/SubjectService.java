package br.ufrrj.samu.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import br.ufrrj.samu.entities.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SubjectService {

    private HashMap<String, Subject> subjects;
    private static final Logger LOGGER = LogManager.getLogger(SubjectService.class);

    public SubjectService() {

        this.subjects = new HashMap<String,Subject>();

        this.addSubject(new Subject("Arquitetura de Computadores II", " - ", "TM409"));
        this.addSubject(new Subject("Calculo Aplicado", " - ", "TM406"));
        this.addSubject(new Subject("Fisica para Computacao", " - ", "TM407"));

        Subject pre = new Subject("Circuitos Digitais", " - ", "TM301");
        Subject pre2 = new Subject("Arquitetura de Computadores I", " - ", "TM302");
        this.subjects.get("TM409").addPrerequisite(pre);
        this.subjects.get("TM409").addPrerequisite(pre2);

    }

    public void addSubject(Subject subject) {
        subjects.put(subject.getCode(), subject);
    }

    public Subject getSubject(String code) {
        return subjects.get(code);
    }

    public static void main(String[] args) {

        SubjectService subjectService = new SubjectService();

        LOGGER.debug(subjectService.subjects.get("TM409").getPrerequisiteCodes());
    }
}
