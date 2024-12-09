package Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

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

    public void saveNotions(String path){
        try{
            FileWriter writer = new FileWriter(path+"/notions.csv");
            for (Notion notion : this.notions){
                writer.write(notion.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveRessources(String path){
        try{
            FileWriter writer = new FileWriter(path+"/ressources.csv");
            for (Ressource ressource : this.ressources){
                writer.write(ressource.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveQuestions(String path){
        try{
            FileWriter writer = new FileWriter(path+"/questions.csv");
            for (Question question : this.questions){
                writer.write(question.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}