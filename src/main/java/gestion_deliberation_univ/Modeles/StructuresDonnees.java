package gestion_deliberation_univ.Modeles;

public class StructuresDonnees {

    public static class structure_filiere {
        private int id;
        private String nom_filiere;

        public structure_filiere(int id, String nom_filiere) {
            this.id = id;
            this.nom_filiere = nom_filiere;
        }

        public int getId_filiere() {
            return id;
        }

        public String getNom_filiere() {
            return nom_filiere;
        }
    }

    public static class structure_classe {
        private int id;
        private String niveau_classe;
        private int idFiliere;
        private String annee_univ;
        private int effectif;

        public structure_classe(int id, String niveau_classe, int idFiliere, String annee_univ, int effectif) {
            this.id = id;
            this.niveau_classe = niveau_classe;
            this.idFiliere = idFiliere;
            this.annee_univ = annee_univ;
            this.effectif = effectif;
        }

        public int getId_classe() {
            return id;
        }

        public String getNiveau_classe() {
            return niveau_classe;
        }

        public int getIdFiliere() {
            return idFiliere;
        }

        public String getAnnee_univ() {
            return annee_univ;
        }

        public int getEffectif() {
            return effectif;
        }
    }

    public static class structure_etudiant {
        private int id;
        private String nom_etudiant;
        private String prenom_etudiant;
        private String matricule;

        public structure_etudiant(int id, String nom_etudiant, String prenom_etudiant, String matricule) {
            this.id = id;
            this.nom_etudiant = nom_etudiant;
            this.prenom_etudiant = prenom_etudiant;
            this.matricule = matricule;
        }

        public int getId_etudiant() {
            return id;
        }

        public String getNom_prenom() {
            return nom_etudiant + " " + prenom_etudiant;
        }

        public String getMatricule() {
            return matricule;
        }
    }

    public static class structure_matiere {

        private int idMatiere;
        private String uniteEnseignement;
        private String nomMatiere;
        private Double coefficient;

        public structure_matiere(int idMatiere, String uniteEnseignement, String nomMatiere,
                Double coefficient) {
            this.idMatiere = idMatiere;
            this.uniteEnseignement = uniteEnseignement;
            this.nomMatiere = nomMatiere;
            this.coefficient = coefficient;
        }

        public int getId_matiere() {
            return idMatiere;
        }

        public String getUnite_enseignement() {
            return uniteEnseignement;
        }

        public String getNom_matiere() {
            return nomMatiere;
        }

        public Double getCoefficient() {
            return coefficient;
        }

    }

    public static class suggestion_matiere {
        private int id;
        private String nom;

        public suggestion_matiere(int id, String nom) {
            this.id = id;
            this.nom = nom;
        }

        public int getId_matiere() {
            return id;
        }

        public String getNom_matiere() {
            return nom;
        }

        @Override
        public String toString() {
            return nom;
        }
    }

    public static class structure_report_note {
        private String nom_matiere;
        private String note1;
        private String note2;
        private String session;

        public structure_report_note(String nom_matiere, String note1, String note2, String session) {
            this.nom_matiere = nom_matiere;
            this.note1 = note1;
            this.note2 = note2;
            this.session = session;
        }

        public String getNom_matiere() {
            return nom_matiere;
        }

        public String getNote1() {
            return note1;
        }

        public String getNote2() {
            return note2;
        }

        public String getSession() {
            return session;
        }
    }

    public static class structure_note {
        private String nom_ue;
        private String nom_matiere;
        private int coefficient;
        private Double note1;
        private Double note2;
        private Double moyenne;

        public structure_note(String nom_ue, String nom_matiere, int coefficient, Double note1, Double note2,
                Double moyenne) {
            this.nom_ue = nom_ue;
            this.nom_matiere = nom_matiere;
            this.coefficient = coefficient;
            this.note1 = note1;
            this.note2 = note2;
            this.moyenne = moyenne;
        }

        public String getNom_ue() {
            return nom_ue;
        }

        public String getNom_matiere() {
            return nom_matiere;
        }

        public int getCoefficient() {
            return coefficient;
        }

        public Double getNote1() {
            return note1;
        }

        public Double getNote2() {
            return note2;
        }

        public Double getMoyenne() {
            return moyenne;
        }
    }

}
