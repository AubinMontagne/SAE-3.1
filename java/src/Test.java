package src;

import src.Metier.*;

public class Test {
	public static void main(String[] args) {

		Metier metier = new Metier();
		
		// Test des Ressources

		System.out.println("\nTest des Ressources\n");

		Ressource ressource = new Ressource( "Ressource", "R");
		System.out.println(ressource);
		metier.ajouterRessource(ressource);

		// Test des Notions

		System.out.println("Test des Notions\n");
		Notion notion = new Notion( "Notion", ressource);
		metier.ajouterNotion(notion);
		Notion notion2 = new Notion( "Notion2", ressource);
		System.out.println(notion2);

		// ----------- Test des Questions -------------

		// Test de la classe QCM
		
		System.out.println("\nTest de la classe QCM\n");

		QCM qcm = new QCM("QCM", Difficulte.FACILE, notion, 10, 10);
		
		qcm.ajouterReponse("A", true);
		qcm.ajouterReponse("B", false);
		qcm.ajouterReponse("C", true);

		System.out.println(qcm.getAsData());
		QCM qcm2 = QCM.getAsInstance("data/QCM/", metier);
		metier.ajouterQuestion(qcm2);

		System.out.println(qcm2);


		// Test de la classe EliminationReponse
		
		System.out.println("\nTest de la classe EliminationReponse\n");

		EliminationReponse eq = new EliminationReponse("Question Éliminatoire", Difficulte.MOYEN, notion, 10, 10);
		
		eq.ajouterReponse("A", 1,1);
		eq.ajouterReponse("B", 2,1);
		eq.ajouterReponse("C", 3, 2);
		eq.setReponseCorrecte("A");
		//eq.setExplicationFich("Ceci est incorrect, car la courbe de demande est une droite décroissante."); Should be file path

		System.out.println(eq.getAsData());
		
		// Test de la classe AssociationElement

		System.out.println("\nTest de la classe AssociationElement\n");

		AssociationElement ae = new AssociationElement("Question Association", Difficulte.DIFFICILE, notion, 10, 10);
		ae.ajouterAssociation("A", "1");
		ae.ajouterAssociation("B", "2");
		ae.ajouterAssociation("C", "3");

		System.out.println(ae.getAsData());

		// Test Questionnaire

		System.out.println("\nTest de la classe Questionnaire\n");

		Questionnaire questionnaire = new Questionnaire("Questionnaire", ressource, true);
		questionnaire.ajouterNotion(notion);
		questionnaire.defNbQuestion(notion, Difficulte.FACILE, 2);
		questionnaire.defNbQuestion(notion, Difficulte.MOYEN, 2);
		questionnaire.defNbQuestion(notion, Difficulte.DIFFICILE, 2);
		questionnaire.initLstQuestions(metier);

		System.out.println(questionnaire.toString());

		// Test de la classe Metier

		System.out.println("\nTest de la classe Metier\n");

		System.out.println("\nAffichage des notions :\n");
		System.out.println(metier.getNotions());

		System.out.println("\nAffichage des ressources :\n");
		System.out.println(metier.getRessources());

		System.out.println("\nAffichage des questions :\n");
		System.out.println(metier.getQuestions());


		System.out.println("\nSauvegarde des données\n");

		metier.saveNotions("data/Notions");
		metier.saveRessources("data/Ressources");

		System.out.println("\nAffichage d'une question sélectionné à l'aléatoire :\n");
		System.out.println(metier.getQuestionAleatoire(notion, Difficulte.FACILE));	

		System.out.println("\nAffichage de la notion avec l'id 1 :\n");
	}
}