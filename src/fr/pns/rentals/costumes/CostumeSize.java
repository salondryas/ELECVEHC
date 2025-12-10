package fr.pns.rentals.costumes;

public enum CostumeSize {
    SMALL,      // Petit
    MEDIUM,     // Moyen
    LARGE,      // Grand
    XLARGE,     // Très grand
    XXLARGE ,   // Très très grand (par exemple, XXL)
    ONE_SIZE    // Taille unique
    ;

    public String getFrenchName() {
        switch (this) {
            case SMALL:
                return "Petit";
            case MEDIUM:
                return "Moyen";
            case LARGE:
                return "Grand";
            case XLARGE:
                return "Très grand";
            case XXLARGE:
                return "Très très grand";
            case ONE_SIZE:
                return "Taille unique";
            default:
                return "Taille inconnue";
        }
    }
}