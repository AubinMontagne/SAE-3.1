package src.Metier;

public class TestJS
{
	public static void main(String[] args)
	{

		Ressource r1 = new Ressource("R3.01_Dev_WEB", "DevWEB");
		Notion n1 = new Notion("Ecriture en PHP", r1);
		Questionnaire questionnaire = new Questionnaire("Test1", r1 , false, new Metier());
		questionnaire.ajouterNotion(n1);
		questionnaire.addQuestion(new QCM("Comment utiliser le PHP", Difficulte.MOYEN, n1, 20,2, "Pour aller coder en PHP vous devez d'abord lancer un serveur"));
		questionnaire.getLstQuestion().get(0).

		new QCMBuilder(questionnaire, "./");
	}
}