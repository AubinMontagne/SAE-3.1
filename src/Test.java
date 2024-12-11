import Metier.*;
import Metier.Notion;
import Metier.Ressource;

public class Test {
	public static void main(String[] args) {
		Metier metier = new Metier();
		Notion notion = new Notion(1, "Notion", new Ressource(1,"Ressource","R"),metier);
		metier.ajouterNotion(notion);
		QCM qcm = new QCM("QCM", Difficulte.FACILE, notion, 10, 10,metier);
		qcm.ajouterReponse("A", true);
		qcm.ajouterReponse("B", false);
		qcm.ajouterReponse("C", true);
		qcm.getAsData("data/QCM");
		QCM qcm2 = QCM.getAsInstance("data/QCM/", metier);
		System.out.println(qcm2);
	}
}
