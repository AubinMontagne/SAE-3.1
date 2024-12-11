import Metier.*;
import Metier.Notion;
import Metier.Ressource;

public class Test {
	public static void main(String[] args) {

		Metier metier = new Metier();
		Notion notion = new Notion(1, "Notion", new Ressource(1,"Ressource","R"),metier);
		metier.ajouterNotion(notion);

		// Test de la classe QCM
		
		QCM qcm = new QCM("QCM", Difficulte.FACILE, notion, 10, 10,metier);
		
		qcm.ajouterReponse("A", true);
		qcm.ajouterReponse("B", false);
		qcm.ajouterReponse("C", true);
		
		qcm.getAsData("data/QCM");
		QCM qcm2 = QCM.getAsInstance("data/QCM/", metier);
		
		System.out.println(qcm2);


		// Test de la classe EliminationReponse
		
		EliminationReponse eq = new EliminationReponse("Question Éliminatoire", Difficulte.FACILE, notion, 10, 10,metier);
		
		eq.ajouterReponse("A", 1,1);
		eq.ajouterReponse("B", 2,1);
		eq.ajouterReponse("C", 3, 2);
		eq.setReponseCorrecte("A");
		eq.setExplication("Ceci est incorrect, car la courbe de demande est une droite décroissante.");

		eq.getAsData("data/EliminationQuestion");
		EliminationReponse eq2 = EliminationReponse.getAsInstance("data/EliminationQuestion/", metier);

		System.out.println(eq2);

		
		// Test de la classe AssociationElement

		AssociationElement ae = new AssociationElement("Question Association", Difficulte.FACILE, notion, 10, 10,metier);
		ae.ajouterAssociation("A", "1");
		ae.ajouterAssociation("B", "2");
		ae.ajouterAssociation("C", "3");

		ae.getAsData("data/AssociationElement");
		AssociationElement ae2 = AssociationElement.getAsInstance("data/AssociationElement/", metier);

		System.out.println(ae2);
	}
}