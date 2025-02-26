package src.Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class QCM extends Question
{
	private HashMap<String, Boolean> hmReponses;
	private Boolean vraiOuFaux;

	// Constructeur
	/**
	 * Constructeur de la class QCM
	 * @param intitule		L'intituler de la question type QCM
	 * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
	 * @param notion        La notion concernée par la question.
	 * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
	 * @param points        Le nombre de points que rapporte la question.
	 * @param vraiOuFaux   	Si le QCM est un vrai ou faux
	 */
	public QCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points, boolean vraiOuFaux, String imageChemin, int id)
	{
		super(intitule, difficulte, notion, temps, points, imageChemin, id);
		this.hmReponses = new HashMap<String, Boolean>();
		this.vraiOuFaux = vraiOuFaux;
	}

	/**
	 * Methode ajouterReponse
	 * Cette méthode ajoute une réponse, corecte ou non, a un QCM 
	 * @param reponse	Le text de la réponse
	 * @param correct	Si la reponse est corecte
	 * @return			Vrai si l'ajout a réussi, sinon faux
	 */
	public boolean ajouterReponse(String reponse, Boolean correct)
	{

		if (this.vraiOuFaux && correct)
		{
			for (Boolean value : this.hmReponses.values())
			{
				if (value)
				{
					return false;
				}
			}
		}
		if (this.hmReponses.containsKey(reponse))
		{
			this.hmReponses.replace(reponse, correct);
		}
		else
		{
			this.hmReponses.put(reponse, correct);
		}
		return true;
	}

	public boolean estVraiouFaux(){return this.vraiOuFaux;}

	/**
	 * Methode supprimerReponse
	 * Cette méthode supprime un réponse a un QCM
	 * @param reponse	La réponse à supprimer
	 */
	public void supprimerReponse(String reponse){this.hmReponses.remove(reponse); }
	// Get/
	public HashMap<String, Boolean> getReponses  (){return this.hmReponses; }
	public int                      getNbReponses(){return this.hmReponses.size(); }

	@Override
	public String getAsData()
	{
		String res = "QCM;" + super.getAsData() + ";" + this.vraiOuFaux + ";" ;
		for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet())
        {
			res += entry.getKey() + "," + entry.getValue() + "|";
		}
		return res;
	}

	public static QCM getAsInstance(String ligne, Metier metier)
	{
		Scanner scanner = new Scanner(ligne);
		scanner.useDelimiter(";");

		String[] parts = new String[11];
		for (int i = 0; i < 11; i++)
		{
			parts[i] = scanner.next();
		}

		QCM qcm = new QCM(
				parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])),
				metier.getNotionByNom(parts[3]),
				Integer.parseInt(parts[4]),
				Integer.parseInt(parts[5]),
                Boolean.parseBoolean(parts[9]), parts[7],
				Integer.parseInt(parts[6])
		);

		Scanner reponseScanner = new Scanner(parts[10]);
		reponseScanner.useDelimiter("\\|");
		while (reponseScanner.hasNext())
		{
			String reponse = reponseScanner.next();
			Scanner qcmScanner = new Scanner(reponse);

			qcmScanner.useDelimiter(",");
			String[] reponseParts = new String[2];
			for (int i = 0; i < 2; i++)
			{
				reponseParts[i] = qcmScanner.next();
			}

			qcm.ajouterReponse(reponseParts[0], Boolean.parseBoolean(reponseParts[1]));
			qcmScanner.close();
		}

		reponseScanner = new Scanner(parts[8]);
		reponseScanner.useDelimiter(",");
		while (reponseScanner.hasNext())
		{
			String fichier = reponseScanner.next();
			qcm.ajouterFichier(fichier);
		}

		return qcm;
	}

	// Set
	public void setHmReponses(HashMap<String, Boolean> hmReponses) {this.hmReponses = hmReponses; }

	public String toString()
	{
		String res = super.toString();
		res += "Une seule réponse : " + this.vraiOuFaux + "\n";
		res += "Réponses : \n";

		for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet())
		{
			res += entry.getKey() + " -> " + entry.getValue() + "\n";
		}
		return res;
	}
}