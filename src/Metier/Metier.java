package Metier;

import java.io.*;
import java.util.ArrayList;

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
            return this.questions.add(question);
        }
        return false;
    }
	
	public boolean supprimerNotion(Notion notion){
		if (notion != null){
			return this.notions.remove(notion);
		}
		return false;
	}
	public boolean supprimerRessource(Ressource ressource){
		if (ressource != null){
			return this.ressources.remove(ressource);
		}
		return false;
	}
	public boolean supprimerQuestion(Question question){
		if (question != null){
			return this.questions.remove(question);
		}
		return false;
	}

	// Get 

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

	public Question getQuestionAleatoire(Notion n, Difficulte d){
		ArrayList<Question> qs = new ArrayList<>();
		for (Question question : this.questions){
			if (question.getNotion().equals(n) && question.getDifficulte().equals(d)){
				qs.add(question);
			}
		}
		if (!qs.isEmpty()){
			return qs.get((int)(Math.random()*qs.size()));
		}
		return null;
	}

	// Sauvegardes

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

	public void getRessourcesFromData(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path+"/ressources.csv"));
			String line;
			while ((line = reader.readLine()) != null){
				this.ressources.add(Ressource.getFromData(line, this));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void getNotionsFromData(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path+"/notions.csv"));
			String line;
			while ((line = reader.readLine()) != null){
				this.notions.add(Notion.getFromData(line, this));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}