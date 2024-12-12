package src.Metier;

public class TestJS
{
	public static void main(String[] args)
	{

		Ressource r1 = new Ressource("R3.01_Dev_WEB", "DevWEB");
		Notion n1 = new Notion("Ecriture en PHP", r1);
		Questionnaire questionnaire = new Questionnaire("Test1", r1 , false);
		questionnaire.ajouterNotion(n1);


		//Questions
		questionnaire.addQuestion(new QCM("Comment utiliser le PHP ?", Difficulte.MOYEN, n1, 20,2, "Pour aller coder en PHP vous devez d'abord lancer un serveur"));
		questionnaire.addQuestion(new QCM("Quels outils ?", Difficulte.FACILE, n1, 15,1, "Vous devez utilisr VSCODE"));


		//Reponses
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("Docker", true);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("XAMPP", true);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("rien", false);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("Test", false);


		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("Intellij", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("Eclypse", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("rien", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("VS code", true);



		new QCMBuilder(questionnaire, "./");
	}
}