package src.Metier;

public class TestJS{
	public static void main(String[] args){

		Ressource r1 = new Ressource("R3.01_Dev_WEB", "DevWEB");
		Notion n1 = new Notion("Ecriture en PHP", r1);
		Questionnaire questionnaire = new Questionnaire("Test1", r1 , false);
		questionnaire.ajouterNotion(n1);


		//Questions
		questionnaire.addQuestion(new QCM("Comment utiliser le PHP ?", Difficulte.MOYEN, n1, 20,2, "Pour aller coder en PHP vous devez d'abord lancer un serveur", false));
		questionnaire.addQuestion(new QCM("Quels outils ?", Difficulte.FACILE, n1, 15,1, "Vous devez utilisr VSCODE", true));
		questionnaire.addQuestion(new QCM("Comment definir une var ?", Difficulte.TRES_FACILE, n1, 18,1, "avec $nomVar = Tests", true));
		//questionnaire.addQuestion(new AssociationElement("Reliez les éléments entre eux :", Difficulte.MOYEN, n1, 23, 3, "Il fallait faire le 1 avec le 3 et le 2 avec le 4"));
		//questionnaire.addQuestion(new EliminationReponse("Choisissez la bonne réponse :", Difficulte.DIFFICILE, n1, 12,2, "La 6 car feur. "));
		//questionnaire.addQuestion(new AssociationElement("Asso 2 :", Difficulte.MOYEN, n1, 23, 3, "Feur."));


		//Reponses
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("Docker", true);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("XAMPP", true);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("rien", false);
		((QCM)questionnaire.getLstQuestion().get(0)).ajouterReponse("Test", false);


		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("Intellij", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("Eclypse", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("rien", false);
		((QCM)questionnaire.getLstQuestion().get(1)).ajouterReponse("VS code", true);

		((QCM)questionnaire.getLstQuestion().get(2)).ajouterReponse("Machin", false);
		((QCM)questionnaire.getLstQuestion().get(2)).ajouterReponse("Chouette", true);

		//((AssociationElement)questionnaire.getLstQuestion().get(3)).ajouterAssociation("La une", "La trois");
		//((AssociationElement)questionnaire.getLstQuestion().get(3)).ajouterAssociation("La deux", "La 4");

		//((EliminationReponse)questionnaire.getLstQuestion().get(4)).ajouterReponse("Salut !", 1.0, 1.0);
		//((EliminationReponse)questionnaire.getLstQuestion().get(4)).ajouterReponse("Yop !", 2.0, 2.0);
		//((EliminationReponse)questionnaire.getLstQuestion().get(4)).ajouterReponse("Hey !", 0.0, -1.0);

		//((AssociationElement)questionnaire.getLstQuestion().get(5)).ajouterAssociation("La une", "La trois");
		//((AssociationElement)questionnaire.getLstQuestion().get(5)).ajouterAssociation("La deux", "La 4");
		//((AssociationElement)questionnaire.getLstQuestion().get(5)).ajouterAssociation("Feur", "Coubeh");
		//((AssociationElement)questionnaire.getLstQuestion().get(5)).ajouterAssociation("La deuxE", "La 4");

		new QCMBuilder(questionnaire, "./");
	}
}