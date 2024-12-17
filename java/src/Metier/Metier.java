package src.Metier;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Metier{
    private ArrayList<Notion>    lstNotions;
    private ArrayList<Ressource> lstRessources;
    private ArrayList<Question>  lstQuestions;

	// Constructeur
	/**
	 * Constructeur de Métier
	 */
    public Metier(){
        this.lstNotions    = new ArrayList<>();
        this.lstRessources = new ArrayList<>();
        this.lstQuestions  = new ArrayList<>();
    }

	// Methode

	/**
	 * Méthode ajouterNotion
	 * @param notion La notion à ajouter
	 * @return		 Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterNotion(Notion notion){
        if (notion != null){
            this.lstNotions.add(notion);
            return true;
        }
        return false;
    }

	/**
	 * Méthode ajouterRessource
	 * @param ressource La ressource à ajouter
	 * @return		 	Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterRessource(Ressource ressource){
        if (ressource != null){
            this.lstRessources.add(ressource);
            return true;
        }
        return false;
    }

	/**
	 * Méthode ajouterQuestion
	 * @param question La question à ajouter
	 * @return		   Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterQuestion(Question question){
        if (question != null){
            return this.lstQuestions.add(question);
        }
        return false;
    }

	/**
	 * Méthode supprimerNotion
	 * @param notion La notion à supprimer
	 * @return		 Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerNotion(Notion notion){
		if (notion != null){
			return this.lstNotions.remove(notion);
		}
		return false;
	}

	/**
	 * Méthode supprimerRessource
	 * @param ressource La ressource à supprimer
	 * @return		 	Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerRessource(Ressource ressource){
		if (ressource != null){
			return this.lstRessources.remove(ressource);
		}
		return false;
	}

	/**
	 * Méthode supprimerQuestion
	 * @param question La question à supprimer
	 * @return		   Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerQuestion(Question question){
		if (question != null){
			return this.lstQuestions.remove(question);
		}
		return false;
	}

	// Sauvegardes

	/**
	 * Methode saveNotions
	 * @param path Le chemin du fichier texte à écrire
	 */
    public void saveNotions(String path){
        try{
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"notions.csv");// PROBLEME ICICICICICICICICICICICICICICCICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICICCICIICICCICICICICIICICICICIICICICIICICIC
            for (Notion notion : this.lstNotions){
                writer.write(notion.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

	/**
	 * Methode saveRessources
	 * @param path Le chemin du fichier texte à écrire
	 */
    public void saveRessources(String path){
        try{
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"ressources.csv");
            for (Ressource ressource : this.lstRessources){
                writer.write(ressource.getAsData() + "\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

	/**
	 * Methode saveQuestions
	 * @param path Le chemin du fichier texte à écrire
	 */
	public void saveQuestions(String path){
		try{
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileWriter writer = new FileWriter(path+"questions.csv");
			for (Question question : this.lstQuestions){
				if (question instanceof QCM){
					writer.write(((QCM)question).getAsData() + "\n");
				}else if (question instanceof EliminationReponse){
					writer.write(((EliminationReponse)question).getAsData() + "\n");
				}else if (question instanceof AssociationElement){
					writer.write(((AssociationElement)question).getAsData() + "\n");
				}
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// Getter
	public ArrayList<Notion>    getNotions()   {return this.lstNotions; }
	public ArrayList<Ressource> getRessources(){return this.lstRessources; }
	public ArrayList<Question>  getQuestions() {return this.lstQuestions; }

	// Bazar aubin

	public String getNomNotion(Notion notion)
	{
		return notion.getNom();
	}

	public boolean ajouterQuestionQCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points, boolean vraiOuFaux, HashMap<String, Boolean> reponses) {
		QCM questionQCM = new QCM(intitule, difficulte, notion, temps, points, vraiOuFaux);

		// Ajout des réponses avec leurs booléens
		for (HashMap.Entry<String, Boolean> entry : reponses.entrySet()) {
			questionQCM.ajouterReponse(entry.getKey(), entry.getValue());
		}
		this.lstQuestions.add(questionQCM);
		System.out.println(this.lstQuestions);

		return true;
	}

	public Difficulte getDifficulteByIndice(int indice)
	{
		return Difficulte.getDifficulteByIndice(indice);
	}

	public Ressource getRessourceById(String id){
		for (Ressource ressource : this.lstRessources){
			if (ressource.getId().equals(id)){
				return ressource;
			}
		}
		return null;
	}

	public Notion getNotionByNom(String nom){
		for (Notion notion : this.lstNotions){
			if (notion.getNom().equals(nom)){
				return notion;
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

	public Question getFromDataQuestion(String line){
		String type = line.substring(0, line.indexOf(";"));
		switch (type){
			case "QCM"-> {return QCM.getAsInstance(line,this);}
			case "ER" -> {return EliminationReponse.getAsInstance(line,this);}
			case "AE" -> {return AssociationElement.getAsInstance(line,this);}
		}
		return null;
	}

	public void getRessourcesFromData(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path+"ressources.csv"));
			String line;
			while ((line = reader.readLine()) != null){
				this.lstRessources.add(Ressource.getFromData(line));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void getNotionsFromData(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path+"notions.csv"));
			String line;
			while ((line = reader.readLine()) != null){
				this.lstNotions.add(Notion.getFromData(line, this));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void getQuestionFromData(String path){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path+"questions.csv"));
			String line;
			while ((line = reader.readLine()) != null){
				this.lstQuestions.add(getFromDataQuestion(line));
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public ArrayList<Question> getQuestionsParNotion(Notion notion){
		ArrayList<Question> questionsAssociees = new ArrayList<>();
	
		for (Question question : this.lstQuestions) 
		{
			if (question.getNotion().equals(notion))
			{
				questionsAssociees.add(question);
				System.out.println(question);
			}
		}
	
		return questionsAssociees;
	}

	public ArrayList<Notion> getNotionsParRessource(Ressource ressource){
		ArrayList<Notion> notionsAssociees = new ArrayList<>();

		for (Notion notion : this.lstNotions)
		{
			if (notion.getRessourceAssociee().equals(ressource))
			{
				notionsAssociees.add(notion);
			}
		}

		return notionsAssociees;
	}
}