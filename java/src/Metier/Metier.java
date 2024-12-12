package src.Metier;

import java.io.*;
import java.util.ArrayList;

public class Metier{
    private ArrayList<Notion> lstNotions;
    private ArrayList<Ressource> lstRessources;
    private ArrayList<Question> lstQuestions;

    public Metier(){
        this.lstNotions = new ArrayList<>();
        this.lstRessources = new ArrayList<>();
        this.lstQuestions = new ArrayList<>();
    }

    public boolean ajouterNotion(Notion notion){
        if (notion != null){
            this.lstNotions.add(notion);
            return true;
        }
        return false;
    }
    public boolean ajouterRessource(Ressource ressource){
        if (ressource != null){
            this.lstRessources.add(ressource);
            return true;
        }
        return false;
    }
    public boolean ajouterQuestion(Question question){
        if (question != null){
            return this.lstQuestions.add(question);
        }
        return false;
    }
	
	public boolean supprimerNotion(Notion notion){
		if (notion != null){
			return this.lstNotions.remove(notion);
		}
		return false;
	}
	public boolean supprimerRessource(Ressource ressource){
		if (ressource != null){
			return this.lstRessources.remove(ressource);
		}
		return false;
	}
	public boolean supprimerQuestion(Question question){
		if (question != null){
			return this.lstQuestions.remove(question);
		}
		return false;
	}

	// Get 

    public ArrayList<Notion> getNotions(){
        return this.lstNotions;
    }
    public ArrayList<Ressource> getRessources(){
        return this.lstRessources;
    }
    public ArrayList<Question> getQuestions(){
        return this.lstQuestions;
    }

    public Notion getNotionById(int id){
        for (Notion notion : this.lstNotions){
            if (notion.getId() == id){
                return notion;
            }
        }
        return null;
    }
    public Ressource getRessourceById(int id){
        for (Ressource ressource : this.lstRessources){
            if (ressource.getId() == id){
                return ressource;
            }
        }
        return null;
    }

	public Question getQuestion(Question question){
		for (Question q : this.lstQuestions){
			if (q.equals(question)){
				return q;
			}
		}
		return null;
	}
	public Notion getNotion(Notion notion){
		for (Notion n : this.lstNotions){
			if (n.equals(notion)){
				return n;
			}
		}
		return null;
	}
	public Ressource getRessource(Ressource ressource){
		for (Ressource r : this.lstRessources){
			if (r.equals(ressource)){
				return r;
			}
		}
		return null;
	}

	public Question getQuestionAleatoire(Notion n, Difficulte d){
		ArrayList<Question> qs = new ArrayList<>();
		for (Question question : this.lstQuestions){
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
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"/notions.csv");
            for (Notion notion : this.lstNotions){
                writer.write(notion.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveRessources(String path){
        try{
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"/ressources.csv");
            for (Ressource ressource : this.lstRessources){
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
				this.lstRessources.add(Ressource.getFromData(line, this));
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
				this.lstNotions.add(Notion.getFromData(line, this));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public ArrayList<Question> getQuestionsParIdNotion(int notionId) 
	{
		ArrayList<Question> questionsAssociees = new ArrayList<>();
	
		for (Question question : this.lstQuestions) 
		{
			if (question.getNotion().getId() == notionId) 
			{
				questionsAssociees.add(question);
				System.out.println(question);
			}
		}
	
		return questionsAssociees;
	}

}