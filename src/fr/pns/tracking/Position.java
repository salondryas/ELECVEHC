package fr.pns.tracking;

import java.util.Objects;

public class Position {
    private final double x;
    private final double y;
    private final double z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Un constructeur 2D est souvent pratique (suggéré plus loin dans le TD [cite: 504])
    public Position(double x, double y) {
        this(x, y, 0);
    }

    // Un constructeur par défaut (0,0,0) est aussi utile
    public Position() {
        this(0, 0, 0);
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // toString
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        // 1. Vérifie si c'est le même objet en mémoire
        if (this == o) return true;

        // 2. Vérifie si l'autre est null ou d'un type différent
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Convertit l'objet pour accéder aux champs
        Position position = (Position) o;

        // 4. Compare les valeurs des coordonnées
        return Double.compare(position.x, x) == 0 &&
                Double.compare(position.y, y) == 0 &&
                Double.compare(position.z, z) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x,y,z);
    }
    public double distance(Position other) {
        if (other == null) {
            return 0;
        }
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;

        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}