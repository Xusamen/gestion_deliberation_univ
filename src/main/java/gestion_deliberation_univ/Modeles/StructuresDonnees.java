package gestion_deliberation_univ.Modeles;

public class StructuresDonnees {

    public static class structure_filiere {
        private int id;
        private String nom_filiere;

        public structure_filiere(int id,String nom_filiere) {
            this.id = id;
            this.nom_filiere = nom_filiere;
        }

        public int getId() {
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

        public int getId() {
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
}
