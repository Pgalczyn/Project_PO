package agh.oop.pdw.model;

// Interfejs elementu świata
public interface WorldElement {
    // Zwracanie pozycji elementu
    Vector2D getPosition();

    // Wyświetlanie elementu
    String toString();

    // ścieżka do wyświetlanego zdjęcia
    String srcImage();
}
