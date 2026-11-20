/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hype AMD
 */
import javax.swing.SwingUtilities;

public class MainKasir {
    public static void main(String[] args) throws InterruptedException {
        // ===== Tahap 1: uji class Pesanan =====
        System.out.println("=== Tahap 1: Uji Pesanan ===");
        Pesanan p = new Pesanan();
        p.tambahItem(15000, 2); // Nasi Goreng
        p.tambahItem(5000, 3);  // Es Teh
        System.out.println("Subtotal: " + p.getSubtotal() + " | Total: " + p.hitungTotal());
        p.tambahItem(25000, 1); // Ayam Bakar
        System.out.println("Subtotal: " + p.getSubtotal() + " | Total: " + p.hitungTotal());
        p.reset();

        // ===== Tahap 2: dua kasir sebagai thread =====
        System.out.println("\n=== Tahap 2: Dua Kasir ===");
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");
        dina.start();
        eko.start();
        dina.join();
        eko.join();
        System.out.println("Semua pelanggan selesai dilayani");

        // ===== Tahap 3 & 4: GUI =====
        SwingUtilities.invokeLater(KasirFrame::new);
    }
}