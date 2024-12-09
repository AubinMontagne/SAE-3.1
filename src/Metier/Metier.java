package Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import Question.*;

public class Metier{
    private ArrayList<Notion> notions;
    private ArrayList<Ressource> ressources;
    private ArrayList<Question> questions;

    public Metier(){
        this.notions = new ArrayList<>();
        this.ressources = new ArrayList<>();
        this.questions = new ArrayList<>();
    }

    public boolean ajouterNotion(Notion notion){
        if (notion != null){
            this.notions.add(notion);
            return true;
        }
        return false;
    }
    public boolean ajouterRessource(Ressource ressource){
        if (ressource != null){
            this.ressources.add(ressource);
            return true;
        }
        return false;
    }
    public boolean ajouterQuestion(Question question){
        if (question != null){
            this.questions.add(question);
            return true;
        }
        return false;
    }

    public ArrayList<Notion> getNotions(){
        return this.notions;
    }
    public ArrayList<Ressource> getRessources(){
        return this.ressources;
    }
    public ArrayList<Question> getQuestions(){
        return this.questions;
    }

    public Notion getNotionById(int id){
        for (Notion notion : this.notions){
            if (notion.getId() == id){
                return notion;
            }
        }
        return null;
    }
    public Ressource getRessourceById(int id){
        for (Ressource ressource : this.ressources){
            if (ressource.getId() == id){
                return ressource;
            }
        }
        return null;
    }
}