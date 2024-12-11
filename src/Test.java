import Metier.Difficulte;
import Metier.Metier;
import Metier.QCM;

public class Test {
	public static void main(String[] args) {
		Metier metier = new Metier();
		Notion notion = new Notion(1, "Notion", new Ressource(1,"Ressource","R"), metier);
		metier.ajouterNotion(notion);
		QCM qcm = new QCM("QCM", Difficulte.FACILE, notion, 10, 10,
				metier);
		qcm.ajouterReponse("A", true);
		qcm.ajouterReponse("B", false);
		qcm.ajouterReponse("C", true);
		qcm.getAsData("data/QCM");
		QCM qcm2 = getAsInstance("data/QCM/", metier);
		System.out.println(qcm2);
	}
}
