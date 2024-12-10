package Metier;

import java.util.HashMap;

public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private HashMap<String,int> reponses;
	private int pointsPerdusParElimination;

	/**
	 * Liste des réponses dans l'ordre d'élimination.
	 * @param reponseCorrecte La réponse correcte.
	 * @param reponses         Les réponses incorrectes et la réponses correcte dans l'ordre définit par le concepteur.
	 * @param pointsPerdusParElimination Les points perdus par élimination.
	 */
	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new HashMap<>();
	}

	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte, String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new ArrayList<>();
		this.pointsPerdusParElimination = pointsPerdusParElimination;
	}

	// Getters
	public String getReponseCorrecte() {
		return this.reponseCorrecte;
	}

	public ArrayList<String> getreponses() {
		return this.reponses;
	}

	public int getPointsPerdusParElimination() {
		return this.pointsPerdusParElimination;
	}

	public boolean isReponseCorrecte(String reponse) {
		return this.reponseCorrecte.equals(reponse);
	}

	// Setters
	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public void setreponses(ArrayList<String> reponses) {
		this.reponses = reponses;
	}

	public void setPointsPerdusParElimination(int pointsPerdusParElimination) {
		this.pointsPerdusParElimination = pointsPerdusParElimination;
	}

	// Methods to add and remove incorrect responses
	public void addReponseIncorrecte(String reponseIncorrecte) {
		this.reponses.add(reponseIncorrecte);
	}

	public void removeReponseIncorrecte(String reponseIncorrecte) {
		this.reponses.remove(reponseIncorrecte);
	}

	public void echangerPlace(String reponse1, String reponse2) {
		int index1 = this.reponses.indexOf(reponse1);
		int index2 = this.reponses.indexOf(reponse2);
		if (index1 != -1 && index2 != -1) {
			this.reponses.set(index1, reponse2);
			this.reponses.set(index2, reponse1);
		}
	}

	@override
	public void getAsData(String directoryPath) {
		try {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String fileName = Paths.get(directoryPath, "EliminationReponse.rtf").toString();
			DefaultStyledDocument doc = new DefaultStyledDocument();
			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			Style s = doc.addStyle("bold", def);
			StyleConstants.setBold(s, true);
			doc.insertString(doc.getLength(), "Question : ", s);
			doc.insertString(doc.getLength(), this.intitule + "\n", def);
			doc.insertString(doc.getLength(), "Difficulté : ", s);
			doc.insertString(doc.getLength(), this.difficulte + "\n", def);
			doc.insertString(doc.getLength(), "Notion : ", s);
			doc.insertString(doc.getLength(), this.notion + "\n", def);
			doc.insertString(doc.getLength(), "Temps : ", s);
			doc.insertString(doc.getLength(), this.temps + "\n", def);
			doc.insertString(doc.getLength(), "Points : ", s);
			doc.insertString(doc.getLength(), this.points + "\n", def);
			doc.insertString(doc.getLength(), "Réponse correcte : ", s);
			doc.insertString(doc.getLength(), this.reponseCorrecte + "\n", def);
			doc.insertString(doc.getLength(), "Points perdus par élimination : ", s);
			doc.insertString(doc.getLength(), this.pointsPerdusParElimination + "\n", def);
			doc.insertString(doc.getLength(), "Réponses : ", s);
			for (String reponse : this.reponses) {
				doc.insertString(doc.getLength(), reponse + "\n", def);
			}
			doc.insertString(doc.getLength(), "Explication : ", s);
			doc.insertString(doc.getLength(), this.explication + "\n", def);
			RTFEditorKit kit = new RTFEditorKit();
			FileOutputStream file = new FileOutputStream(fileName);
			kit.write(file, doc, 0, doc.getLength());
			file.close();
		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
		}
	}
}