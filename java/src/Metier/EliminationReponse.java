package src.Metier;

import java.util.HashMap;
import java.util.Scanner;

public class EliminationReponse extends Question
{
	private String                   reponseCorrecte;
	private HashMap<String,Double[]> hmReponses;

	// Constructeur
	/**
	 * Constructeur de la class EliminationReponse
	 * @param dossierChemin Le chemin vers le rtf de l'énoncé et de l'explication
	 * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
	 * @param notion        La notion concernée par la question.
	 * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
	 * @param points        Le nombre de points que rapporte la question.
	 * @param imageChemin   Le chemin ver les images et fichier conplémentaire
	 * @param id            L'id de la question
	 */
	public EliminationReponse(String dossierChemin, Difficulte difficulte, Notion notion, int temps, int points, String imageChemin, int id)
	{
		super(dossierChemin, difficulte, notion, temps, points, imageChemin, id);
		this.reponseCorrecte = "";
		this.hmReponses      = new HashMap<>();
	}

	/**
	 * Méthode ajouterReponse
	 * Cette métode sert a ajouter une réponse a la HashMap des réponses
	 * @param reponse	Le text de la réponse
	 * @param points	Le nombre de points
	 * @param ordre		L'ordre dans le quelle éliminé la réponse, si elle peut l'ètre
	 */
	public void ajouterReponse(String reponse, Double points, Double ordre)
	{
		if (!this.hmReponses.containsKey(reponse) && ordre <= this.getLastIndex()+1)
		{
			this.hmReponses.put(reponse, new Double[]{points, ordre});
		}
	}

	/**
	 * Method supprimerReponse
	 * @param reponse La réponse a supprimer
	 */
	public void supprimerReponse(String reponse){this.hmReponses.remove(reponse); }

	/**
	 * Méthode estReponseCorrecte
	 * @param reponse	La réponse qu'on vérifie
	 * @return			Vrai ou faux selon si la réponse est bonne ou pas
	 */
	public boolean estReponseCorrecte(String reponse) {return this.reponseCorrecte.equals(reponse); }

	// Getters
	public String                   getReponseCorrecte() {return this.reponseCorrecte; }
	public HashMap<String,Double[]> getHmReponses()      {return this.hmReponses; }

	public Double getLastIndex()
	{
		Double indexMax = 0.0;
		for (Double[] index : this.hmReponses.values())
		{
			if (index[1] > indexMax)
			{
				indexMax = index[1];
			}
		}
		return indexMax + 1;
	}

	public String getAsData()
	{
		String res = "ER;" + super.getAsData() + ";" ;
		res += this.reponseCorrecte + ";";
		for(String reponse : this.hmReponses.keySet())
		{
			res += reponse + "," + this.hmReponses.get(reponse)[0] + "," + this.hmReponses.get(reponse)[1] + "|";
		}
		return res;
	}

	public static EliminationReponse getAsInstance(String ligne, Metier metier)
	{
		Scanner scanner = new Scanner(ligne);
		scanner.useDelimiter(";");

		String[] parts = new String[11];
		for (int i = 0; i < 11; i++)
		{
			parts[i] = scanner.next();
		}

		EliminationReponse er = new EliminationReponse(parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])), metier.getNotionByNom(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[7], Integer.parseInt(parts[6]));

		er.setReponseCorrecte(parts[9]);

		Scanner reponseScanner = new Scanner(parts[10]);
		reponseScanner.useDelimiter("\\|");
		while (scanner.hasNext())
		{
			String reponse = scanner.next();
			Scanner eliminationScanner = new Scanner(reponse);
			eliminationScanner.useDelimiter(",");

			String[] reponseParts = new String[3];
			for (int i = 0; i < 3; i++)
			{
				reponseParts[i] = eliminationScanner.next();
			}

			er.ajouterReponse(reponseParts[0], Double.parseDouble(reponseParts[1]), Double.parseDouble(reponseParts[2]));
			eliminationScanner.close();
		}

		reponseScanner = new Scanner(parts[8]);
		reponseScanner.useDelimiter(",");
		while (reponseScanner.hasNext())
		{
			String fichier = reponseScanner.next();
			er.ajouterFichier(fichier);
		}

		reponseScanner.close();
		scanner.close();
		return er;
	}

	// Setters
	public void setReponseCorrecte(String reponseCorrecte)
	{
		this.reponseCorrecte = reponseCorrecte;
		this.hmReponses.put(reponseCorrecte, new Double[]{0.0, -1.0});
	}
	public void setHmReponses     (HashMap<String,Double[]>  hmReponses) {this.hmReponses = hmReponses; }

	// toString
	public String toString()
	{
		String res = super.toString();
		res += "Reponse correcte : " + this.reponseCorrecte + "\n";
		res += "Reponses : \n";
		for (String reponse : this.hmReponses.keySet())
		{
			res += reponse + " : " + this.hmReponses.get(reponse)[0] + " , " + this.hmReponses.get(reponse)[1] + "\n";
		}
		return res;
	}
}