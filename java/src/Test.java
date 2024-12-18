package src;

import src.Metier.*;

public class Test {
	public static void main(String[] args) {

		Metier metier = new Metier();

		metier.getRessourcesFromData("java/data/");
		metier.getQuestionFromData("java/data/");
		metier.getQuestionFromData("java/data/");
		
		// Test des Ressources

		System.out.println("\nTest des Ressources\n");

		System.out.println("\nRecherche d'une ressource par son id (r1.01) :\n");
		Ressource ressource = metier.getRessourceById("R1.01");
		System.out.println(ressource);

		System.out.println("\nAffichage des ressources :\n");

		for(Ressource r : metier.getRessources()) {
			System.out.println(r);
		}

		// Test des Notions

		System.out.println("Test des Notions\n");

		System.out.println("\nRecherche d'une notion par son id (n1.01) :\n");
		Notion notion = metier.getNotionByNom("Les types de base");
		System.out.println(notion);

		// ----------- Test des Questions -------------

		// Test de la classe QCM
		
		System.out.println("\nTest de la classe QCM\n");

		for(Question q : metier.getQuestions()){
			if(q instanceof QCM){
				QCM qcm = (QCM)(q);
				System.out.println(qcm);
			}
		}

		// Test de la classe EliminationReponse
		
		System.out.println("\nTest de la classe EliminationReponse\n");

		for(Question q : metier.getQuestions()){
			if(q instanceof EliminationReponse){
				EliminationReponse ea = (EliminationReponse)(q);
				System.out.println(ea);
			}
		}

		// Test de la classe AssociationElement

		System.out.println("\nTest de la classe AssociationElement\n");

		for(Question q : metier.getQuestions()){
			if(q instanceof AssociationElement){
				AssociationElement ae = (AssociationElement)(q);
				System.out.println(ae);
			}
		}
	}
}