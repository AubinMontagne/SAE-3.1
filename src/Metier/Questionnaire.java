public package src.Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Questionnaire {
   private final Metier metier;
   private String nom;
   private Ressource ressource;
   private HashMap<Notion, HashMap<Difficulte, Integer>> hmQuestionsParDifficulte;
   private int tempsEstimée;
   private int pointMax;
   private boolean chronoBool;
   private List<Notion> lstNotions;
   private List<Question> lstQuestion;

   public Questionnaire(String var1, Ressource var2, boolean var3, Metier var4) {
      this.nom = var1;
      this.ressource = var2;
      this.tempsEstimée = 0;
      this.chronoBool = var3;
      this.lstNotions = new ArrayList();
      this.lstQuestion = new ArrayList();
      this.hmQuestionsParDifficulte = new HashMap();
      this.pointMax = 0;
      this.metier = var4;
   }

   public void ajouterNotion(Notion var1) {
      if (!this.lstNotions.contains(var1)) {
         this.lstNotions.add(var1);
         this.hmQuestionsParDifficulte.put(var1, new HashMap());
      }

   }

   public void supprimerNotion(Notion var1) {
      if (this.lstNotions.contains(var1)) {
         this.lstNotions.remove(var1);
         this.hmQuestionsParDifficulte.remove(var1);
      }

   }

   public void defNbQuestion(Notion var1, Difficulte var2, int var3) {
      if (!this.hmQuestionsParDifficulte.containsKey(var1)) {
         this.ajouterNotion(var1);
      }

      ((HashMap)this.hmQuestionsParDifficulte.get(var1)).put(var2, var3);
   }

   public String getNom() {
      return this.nom;
   }

   public Ressource getRessource() {
      return this.ressource;
   }

   public List<Notion> getLstNotions() {
      return this.lstNotions;
   }

   public List<Question> getLstQuestion() {
      return this.lstQuestion;
   }

   public Notion getNotion(int var1) {
      return (Notion)this.lstNotions.get(var1);
   }

   public boolean getChronoBool() {
      return this.chronoBool;
   }

   public int getTempsEstimée() {
      return this.tempsEstimée;
   }

   public int getPointMax() {
      return this.pointMax;
   }

   public void setNom(String var1) {
      this.nom = var1;
   }

   public void setRessource(Ressource var1) {
      this.ressource = var1;
   }

   public void setChronoBool(boolean var1) {
      this.chronoBool = var1;
   }

   public void majValeurs() {
      this.pointMax = 0;
      this.tempsEstimée = 0;

      Question var2;
      for(Iterator var1 = this.lstQuestion.iterator(); var1.hasNext(); this.tempsEstimée += var2.getTemps()) {
         var2 = (Question)var1.next();
         this.pointMax += var2.getPoint();
      }

   }

   public void initLstQuestions() {
      Iterator var1 = this.hmQuestionsParDifficulte.keySet().iterator();

      while(var1.hasNext()) {
         Notion var2 = (Notion)var1.next();
         Iterator var3 = ((HashMap)this.hmQuestionsParDifficulte.get(var2)).entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry var4 = (Map.Entry)var3.next();
            Difficulte var5 = (Difficulte)var4.getKey();
            int var6 = (Integer)var4.getValue();

            for(int var7 = 0; var7 < var6; ++var7) {
               Question var8 = this.metier.getQuestionAleatoire(var2, var5);
               if (var8 != null) {
                  this.lstQuestion.add(var8);
               }
            }
         }
      }

   }

   public String toString() {
      String var1 = "Questionnaire : " + this.nom + "\n";
      var1 = var1 + "Ressource : " + this.ressource.toString() + "\n";
      var1 = var1 + "Chrono : " + this.chronoBool + "\n";
      var1 = var1 + "Temps estim\u00e9 : " + this.tempsEstimée + "\n";
      var1 = var1 + "Point Max : " + this.pointMax + "\n";
      var1 = var1 + "Liste des notions : \n";

      Iterator var2;
      Notion var3;
      for(var2 = this.lstNotions.iterator(); var2.hasNext(); var1 = var1 + var3.toString() + "\n") {
         var3 = (Notion)var2.next();
      }

      var1 = var1 + "\nListe des questions : \n\n";

      Question var4;
      for(var2 = this.lstQuestion.iterator(); var2.hasNext(); var1 = var1 + var4.toString() + "\n") {
         var4 = (Question)var2.next();
      }

      return var1;
   }
}
 {
	
}
