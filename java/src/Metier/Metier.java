package src.Metier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  Metier{
    private ArrayList<Notion>    lstNotions;
    private ArrayList<Ressource> lstRessources;
    private ArrayList<Question>  lstQuestions;

	// Constructeur
	/**
	 * Constructeur de Métier
	 */
    public Metier()
	{
        this.lstNotions    = new ArrayList<>();
        this.lstRessources = new ArrayList<>();
        this.lstQuestions  = new ArrayList<>();
    }

	// Getter

	public ArrayList<Notion>    getNotions()   {return this.lstNotions; }
	public ArrayList<Ressource> getRessources(){return this.lstRessources; }
	public ArrayList<Question>  getQuestions() {return this.lstQuestions; }

	public Difficulte getDifficulteByIndice(int indice)
	{
		return Difficulte.getDifficulteByIndice(indice);
	}

	public Ressource getRessourceById(String id)
	{
		for (Ressource ressource : this.lstRessources)
		{
			if (ressource.getId().equals(id))
			{
				return ressource;
			}
		}
		return null;
	}

	public Notion getNotionByNom(String nom)
	{
		for (Notion notion : this.lstNotions)
		{
			if (notion.getNom().equals(nom))
			{
				return notion;
			}
		}
		return null;
	}

	public static Question getQuestionAleatoire(Notion n, Difficulte d, ArrayList<Question> lstQuestion){
		if (!lstQuestion.isEmpty())
		{
			return lstQuestion.get((int)(Math.random()*lstQuestion.size()));
		}
		return null;
	}

	public ArrayList<Question> getQuestionsParRessource(Ressource ressource)
	{
		ArrayList<Question> lstQuestion = new ArrayList<Question>();
		for(Question question : this.getQuestions())
		{
			if(question.getNotion().getRessourceAssociee().equals(ressource)){lstQuestion.add(question);}
		}
		return lstQuestion;
	}

	public Question getFromDataQuestion(String line)
	{
		String type = line.substring(0, line.indexOf(";"));
		switch (type)
		{
			case "QCM" -> {return QCM               .getAsInstance(line,this);}
			case "ER"  -> {return EliminationReponse.getAsInstance(line,this);}
			case "AE"  -> {return AssociationElement.getAsInstance(line,this);}
		}
		return null;
	}

	public void getRessourcesFromData(String path)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path+"ressources.csv"));
			String line;
			while ((line = reader.readLine()) != null)
			{
				this.lstRessources.add(Ressource.getFromData(line));
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getNotionsFromData(String path)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path+"notions.csv"));
			String line;
			while ((line = reader.readLine()) != null)
			{
				this.lstNotions.add(Notion.getFromData(line, this));
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getQuestionFromData(String path)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path+"questions.csv"));
			String line;
			while ((line = reader.readLine()) != null)
			{
				this.lstQuestions.add(getFromDataQuestion(line));
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<Question> getQuestionsParNotion(Notion notion)
	{
		ArrayList<Question> questionsAssociees = new ArrayList<>();

		for (Question question : this.lstQuestions)
		{
			if (question.getNotion().equals(notion))
			{
				questionsAssociees.add(question);
			}
		}
		return questionsAssociees;
	}

	public ArrayList<Question> getQuestionsParNotionEtDifficulte(Notion notion, Difficulte difficulte)
	{
		ArrayList<Question> questionsAssociees = new ArrayList<>();

		for (Question question : this.lstQuestions)
		{
			if (question.getNotion().equals(notion) && question.getDifficulte().equals(difficulte))
			{
				questionsAssociees.add(question);
			}
		}
		return questionsAssociees;
	}

	public ArrayList<Notion> getNotionsParRessource(Ressource ressource)
	{
		if(ressource == null) {return new ArrayList<>();}
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

	// Setters

	/**
	 * Méthode ajouterNotion
	 * Cette méthode ajoute, si la notion est pas nul, une notion a la List de notions
	 * @param notion La notion à ajouter
	 * @return		 Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterNotion(Notion notion)
	{
        if ( notion != null && ( !notion.getNom().equals(" ") ) )
		{
            this.lstNotions.add(notion);
			this.saveNotions("java/data/");
            return true;
        }
        return false;
    }

	/**
	 * Méthode ajouterRessource
	 * Cette méthode ajoute, si la ressource est pas nul, une ressource a la List de ressources
	 * @param ressource La ressource à ajouter
	 * @return		 	Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterRessource(Ressource ressource)
	{
        if ( ressource != null && ( !ressource.getId().equals(" ") ) )
		{
            this.lstRessources.add(ressource);
			this.saveRessources("java/data/");
            return true;
        }
        return false;
    }

	/**
	 * Méthode ajouterQuestion
	 * Cette méthode ajouteQuestion, si la question est pas nul, une question a la List de questions
	 * @param question La question à ajouter
	 * @return		   Vrai si l'ajout a réussi, sinon faux
	 */
    public boolean ajouterQuestion(Question question)
	{
        if (question != null)
		{
            this.lstQuestions.add(question);
			this.saveQuestions("java/data/");
			return true;
        }
        return false;
    }

	/**
	 * Méthode supprimerNotion
	 * Cette méthode supprime, si la notion est pas nul, une notion de la List de notions
	 * @param notion La notion à supprimer
	 * @return		 Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerNotion(Notion notion)
	{
		if (notion != null)
		{
			this.lstNotions.remove(notion);
			this.saveNotions("java/data/");
			return true;
		}
		return false;
	}

	/**
	 * Méthode supprimerRessource
	 * Cette méthode supprime, si la ressource est pas nul, une ressouce de la List de ressources
	 * @param ressource La ressource à supprimer
	 * @return		 	Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerRessource(Ressource ressource)
	{
		if (ressource != null)
		{
			this.lstRessources.remove(ressource);
			this.saveRessources("java/data/");
			return true;
		}
		return false;
	}

	/**
	 * Méthode supprimerQuestion
	 * Cette méthode supprime, si la question est pas nul, une question de la List de questions
	 * @param question La question à supprimer
	 * @return		   Vrai si la suppression a réussi, sinon faux
	 */
	public boolean supprimerQuestion(Question question)
	{
		if (question != null)
		{
			question.supprimerQuestionDossier();
			this.lstQuestions.remove(question);
			this.saveQuestions("java/data/");
			return true;
		}
		return false;
	}

	public boolean ajouterQuestionQCM(String dossierChemin, Difficulte difficulte, Notion notion, int temps, int points, boolean vraiOuFaux, HashMap<String, Boolean> reponses, String cheminImg, List<String> lstLiens, int id)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}
		QCM questionQCM = new QCM(
				dossierChemin,
				difficulte,
				notion,
				temps,
				points,
				vraiOuFaux,
				cheminImg2 ,
				id
		);

		// Ajout des réponses avec leurs booléens
		for (HashMap.Entry<String, Boolean> entry : reponses.entrySet())
		{
			questionQCM.ajouterReponse(entry.getKey(), entry.getValue());
		}

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(dossierChemin + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( dossierChemin + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionQCM.ajouterFichier(nomFichier);
			numero++;
		}
		this.lstQuestions.add(questionQCM);
		this.saveQuestions("java/data/");

		return true;
	}

	public boolean ajouterQuestionEntiteAssociation(String cheminDossier, Difficulte difficulte, Notion notion, int temps, int points, HashMap<String, String> associations, String cheminImg, List<String> lstLiens, int id)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}

		AssociationElement questionAE = new AssociationElement(cheminDossier, difficulte, notion, temps, points,cheminImg2, id );

		// Ajout des associations
		for (String gauche : associations.keySet())
		{
			questionAE.ajouterAssociation(gauche, associations.get(gauche));
		}

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(cheminDossier + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( cheminDossier + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionAE.ajouterFichier(nomFichier);
			numero++;
		}

		this.lstQuestions.add(questionAE);
		this.saveQuestions("java/data/");

		return true;
	}

	public boolean ajouterQuestionElimination(String cheminDossier, Difficulte difficulte, Notion notion, int temps, int points, HashMap<String,Double[]> reponses, String reponseCorrecte, String cheminImg, List<String> lstLiens, int id)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}

		EliminationReponse questionER = new EliminationReponse(cheminDossier, difficulte, notion, temps, points, cheminImg2, id);

		// Ajout des réponses
		for (String reponse : reponses.keySet())
		{
			questionER.ajouterReponse(reponse, reponses.get(reponse)[0], reponses.get(reponse)[1]);
		}
		questionER.setReponseCorrecte(reponseCorrecte);

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(cheminDossier + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( cheminDossier + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionER.ajouterFichier(nomFichier);
			numero++;
		}
		this.lstQuestions.add(questionER);
		this.saveQuestions("java/data/");

		return true;
	}

	public Boolean modifQuestionQCM(boolean estModeUnique, HashMap<String, Boolean> reponses, String cheminImg, List<String> lstLiens, Question q)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}
		QCM questionQCM = new QCM(q.getDossierChemin(), q.getDifficulte(), q.getNotion(), q.getTemps(), q.getPoint(), estModeUnique,cheminImg2 ,q.getId());

		// Ajout des réponses avec leurs booléens
		for (HashMap.Entry<String, Boolean> entry : reponses.entrySet())
		{
			questionQCM.ajouterReponse(entry.getKey(), entry.getValue());
		}

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionQCM.ajouterFichier(nomFichier);
			numero++;
		}
		this.lstQuestions.set(this.lstQuestions.indexOf(q),questionQCM);
		this.saveQuestions("java/data/");

		return true;
	}
	public Boolean modifQuestionEntiteAssociation( HashMap<String, String> hmEntiteAssociation, String cheminImg, List<String> lstLiens, Question q)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}

		AssociationElement questionAE = new AssociationElement(q.getDossierChemin(), q.getDifficulte(), q.getNotion(), q.getTemps(), q.getPoint(),cheminImg2, q.getId() );

		// Ajout des associations
		for (String gauche : hmEntiteAssociation.keySet())
		{
			questionAE.ajouterAssociation(gauche, hmEntiteAssociation.get(gauche));
		}

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try

			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionAE.ajouterFichier(nomFichier);
			numero++;
		}

		this.lstQuestions.set(this.lstQuestions.indexOf(q),questionAE);
		this.saveQuestions("java/data/");

		return true;
	}
	public Boolean modifQuestionElimination(HashMap<String, Double[]> hmReponses, String reponseCorrecte, String cheminImg, List<String> lstLiens, Question q)
	{
		String cheminImg2;
		if (cheminImg == null || cheminImg.isEmpty())
		{
			cheminImg2 = null;
		}
		else
		{
			cheminImg2 = "fic00000"+cheminImg.substring(cheminImg.lastIndexOf("."));
		}

		EliminationReponse questionER = new EliminationReponse(
				q.getDossierChemin(),
				q.getDifficulte(),
				q.getNotion(),
				q.getTemps(),
				q.getPoint(),
				cheminImg2,
				q.getId()
		);

		if(!( cheminImg == null || cheminImg.isEmpty()))
		{
			String nomFichier = "fic00000" + cheminImg.substring(cheminImg.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(cheminImg);
				Path fileDest = Paths.get(q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
		}

		int numero = 1;
		for (String fichierChemin: lstLiens)
		{
			String nomFichier = "fic"+String.format("%05d", numero)+fichierChemin.substring(fichierChemin.lastIndexOf("."));

			try
			{
				Path fileSource = Paths.get(fichierChemin);
				Path fileDest = Paths.get( q.getDossierChemin() + "/Compléments/" + nomFichier);

				Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println("Erreur : " + ex.getMessage());
				return false;
			}
			questionER.ajouterFichier(nomFichier);
			numero++;
		}

		this.lstQuestions.set(this.lstQuestions.indexOf(q),questionER);
		this.saveQuestions("java/data/");

		return true;
	}

	// Sauvegardes

	/**
	 * Methode saveNotions
	 * Cette méthode écrit dans un fichier csv toute les notions afin de les sauvers
	 * @param path Le chemin du fichier texte à écrire
	 */
    public void saveNotions(String path)
	{
        try
		{
			File dir = new File(path);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"notions.csv");
            for (Notion notion : this.lstNotions)
			{
				if(notion.getNom() != " "){writer.write(notion.getAsData() + "\n");}
            }
            writer.close();
        }
		catch(IOException e)
		{
            e.printStackTrace();
        }
    }

	/**
	 * Methode saveRessources
	 * Cette méthode écrit dans un fichier csv toute les ressources afin de les sauvers
	 * @param path Le chemin du fichier texte à écrire
	 */
    public void saveRessources(String path)
	{
        try
		{
			File dir = new File(path);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
            FileWriter writer = new FileWriter(path+"ressources.csv");
            for (Ressource ressource : this.lstRessources)
			{
				if(ressource.getNom() != " "){writer.write(ressource.getAsData() + "\n");}
            }
            writer.close();
        }
		catch(IOException e)
		{
            e.printStackTrace();
        }
    }

	/**
	 * Methode saveQuestions
	 * Cette méthode écrit dans un fichier csv toute les questions afin de les sauvers
	 * @param path Le chemin du fichier texte à écrire
	 */
	public void saveQuestions(String path)
	{
		try
		{
			File dir = new File(path);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			FileWriter writer = new FileWriter(path+"questions.csv");
			for (Question question : this.lstQuestions)
			{
				if (question instanceof QCM)
				{
					writer.write(((QCM)question).getAsData() + "\n");
				}
				else if (question instanceof EliminationReponse)
				{
					writer.write(((EliminationReponse)question).getAsData() + "\n");
				}
				else if (question instanceof AssociationElement)
				{
					writer.write(((AssociationElement)question).getAsData() + "\n");
				}
			}
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initQuestionnaire( Questionnaire q, String path)
	{
		if ( path == null || path.isEmpty() )
		{
			path = "./";
		}
		q.initLstQuestions(this);
		new QCMBuilder(q, path);
	}
}