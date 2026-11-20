# Kasir Kantin UNS

Aplikasi kasir sederhana berbasis Java Swing untuk Kantin UNS, dibuat untuk **Soal Praktikum PBO Kelas 3B Pertemuan 11 (Multithreading) dan Pertemuan 12 (GUI Programming)**.

| | |
|---|---|
| **Nama** | Sepfi Nangsa Rigianti |
| **NIM** | L0325013 |
| **Program Studi** | S-1 Informatika, Universitas Sebelas Maret (UNS) |
| **Mata Kuliah** | Pemrograman Berorientasi Objek (PBO), Kelas 3B |

---

## Deskripsi
Kantin UNS membutuhkan aplikasi kasir berbasis jendela. Kasir memasukkan nama menu, harga, dan jumlah, lalu aplikasi menghitung total bayar. Pesanan dengan subtotal di atas Rp 50.000 mendapat diskon 10%. Selain itu, program mensimulasikan dua kasir yang melayani pelanggan secara bersamaan (multithreading) dan menyediakan fitur cetak struk yang berjalan di thread terpisah sehingga jendela tidak macet.

Aplikasi dikerjakan bertahap dalam 4 tahap:

| Tahap | Topik | Class |
|---|---|---|
| 1 | Class logika pesanan | `Pesanan`, `MainKasir` |
| 2 | Dua kasir sebagai thread | `Kasir`, `MainKasir` |
| 3 | Tampilan GUI kasir | `KasirFrame` |
| 4 | Tombol cetak struk dengan thread | `KasirFrame` |

## Fitur
- Menambahkan item pesanan (nama menu, harga, jumlah) dan mencatatnya di area struk.
- Menghitung total bayar dengan diskon otomatis 10% bila subtotal lebih dari Rp 50.000.
- Validasi input: field kosong, harga/jumlah bukan angka, harga negatif, dan jumlah nol atau negatif.
- Simulasi dua kasir (Dina dan Eko) yang melayani pelanggan bersamaan.
- Cetak struk di thread terpisah dengan status "Mencetak struk..." lalu "Struk selesai", tanpa membuat jendela macet.

## Teknologi
- Java (JDK 8 atau lebih baru)
- Java Swing (`JFrame`, `JTextField`, `JTextArea`, `JLabel`, `JButton`, `JScrollPane`, `JOptionPane`)
- `java.lang.Thread` dan `SwingUtilities.invokeLater`
- IDE: NetBeans (dapat juga memakai VS Code atau terminal)

## Struktur Project
Semua class berada dalam satu folder (default package, tanpa `package`):

```
Pertemuan11&12/
├── src/
│   ├── Pesanan.java      # Tahap 1: logika subtotal, diskon, reset
│   ├── Kasir.java        # Tahap 2: thread kasir (extends Thread)
│   ├── KasirFrame.java   # Tahap 3 & 4: GUI Swing + cetak struk
│   └── MainKasir.java    # Titik masuk: uji Tahap 1, jalankan Tahap 2, buka GUI
└── README.md
```

## Cara Menjalankan

### Lewat terminal
Dari folder yang berisi file `.java`:
```bash
javac *.java
java MainKasir
```

### Lewat NetBeans
1. Buat project Java baru, lalu buat empat class dengan nama persis `Pesanan`, `Kasir`, `KasirFrame`, dan `MainKasir` (satu class per file).
2. Tempel kode masing-masing class.
3. Klik kanan `MainKasir.java`, lalu pilih **Run File** (atau set sebagai Main Class lalu Run Project).

Urutan jalan program: output Tahap 1 dan Tahap 2 tampil di konsol, kemudian jendela GUI (Tahap 3 dan 4) terbuka.

---

## Penjelasan Tiap Tahap

### Tahap 1: Class Logika (`Pesanan`)
Atribut `subtotal` bersifat `private` dan hanya diakses lewat method.

| Method | Fungsi |
|---|---|
| `tambahItem(double harga, int jumlah)` | Menambahkan `harga x jumlah` ke subtotal |
| `getSubtotal()` | Mengembalikan nilai subtotal |
| `hitungTotal()` | Jika subtotal > 50.000 dikurangi diskon 10%, jika tidak dikembalikan apa adanya |
| `reset()` | Mengembalikan subtotal ke 0 |

Pengujian dilakukan di `MainKasir` dengan data:

| Menu | Harga | Jumlah |
|---|---|---|
| Nasi Goreng | 15.000 | 2 |
| Es Teh | 5.000 | 3 |
| Ayam Bakar | 25.000 | 1 |

### Tahap 2: Dua Kasir sebagai Thread (`Kasir`)
`Kasir` mewarisi `Thread`. Method `run()` melakukan perulangan 5 pelanggan, mencetak `Kasir <nama> melayani pelanggan ke-<i>`, lalu `Thread.sleep(500)` di antara pelanggan.

Di `MainKasir`, kasir Dina dan Eko dibuat lalu dijalankan dengan `start()`. Setelah itu `join()` dipanggil untuk keduanya agar thread `main` menunggu sampai kedua kasir selesai, baru mencetak `Semua pelanggan selesai dilayani`.

Catatan penting:
- `start()` membuat thread baru yang berjalan bersamaan. Jika memanggil `run()` langsung, kode berjalan di thread `main` secara berurutan dan tidak ada konkurensi.
- Urutan output Dina dan Eko dapat berbeda setiap program dijalankan karena penjadwalan thread diatur oleh sistem operasi dan JVM.
- Tanpa `join()`, kalimat "Semua pelanggan selesai dilayani" bisa muncul lebih awal. Dengan `join()`, kalimat itu selalu paling akhir.

### Tahap 3: GUI Kasir (`KasirFrame`)
Jendela berukuran 400 x 450 piksel dengan `setLayout(null)` dan penempatan komponen memakai `setBounds()`.

Komponen: judul, tiga field (`txtMenu`, `txtHarga`, `txtJumlah`), tombol **Tambah** dan **Hitung Total**, area struk (`JTextArea` dalam `JScrollPane`), serta label total bayar.

- **Tombol Tambah**: membaca field, memanggil `pesanan.tambahItem(...)`, menulis satu baris ke area struk, lalu mengosongkan field.
- **Tombol Hitung Total**: menampilkan hasil `pesanan.hitungTotal()` pada label total dengan format ribuan (contoh `Rp 63.000`).
- **Validasi**: field kosong atau input bukan angka ditangani dengan `try-catch` untuk `NumberFormatException` dan pesan lewat `JOptionPane.showMessageDialog`.

### Tahap 4: Tombol Cetak Struk dengan Thread
Tombol **Cetak Struk** dan label status ditambahkan di bagian bawah jendela. Saat ditekan:

1. Thread baru dibuat dan dijalankan dengan `start()`.
2. Label status berubah menjadi "Mencetak struk...".
3. Thread tidur selama 2 detik (`Thread.sleep(2000)`).
4. Label status berubah menjadi "Struk selesai".

Penantian dilakukan di thread terpisah, jadi Event Dispatch Thread (EDT) Swing tetap bebas dan tombol Tambah serta Hitung Total masih bisa ditekan selama 2 detik itu. Perubahan label dari thread lain dibungkus `SwingUtilities.invokeLater(...)` karena komponen Swing hanya boleh diubah dari EDT.

---

## Hasil Pengujian

### Tahap 1 (konsol)
```
=== Tahap 1: Uji Pesanan ===
Subtotal: 45000.0 | Total: 45000.0
Subtotal: 70000.0 | Total: 63000.0
```

### Tahap 2 (konsol)
Contoh output (urutan baris Dina dan Eko dapat berbeda pada setiap eksekusi):
```
=== Tahap 2: Dua Kasir ===
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-2
Kasir Dina melayani pelanggan ke-2
...
Semua pelanggan selesai dilayani
```

| Eksekusi | Urutan sama dengan eksekusi sebelumnya? | Keterangan |
|---|---|---|
| Ke-1 | - | (isi hasil pengamatan) |
| Ke-2 | (ya/tidak) | (isi hasil pengamatan) |

### Tahap 3 (GUI)
| Skenario | Hasil yang diharapkan |
|---|---|
| Tambah Nasi Goreng 15000 x 2, Es Teh 5000 x 3, lalu Hitung Total | Total Bayar : Rp 45.000 |
| Lalu tambah Ayam Bakar 25000 x 1, Hitung Total | Total Bayar : Rp 63.000 |
| Ada field kosong lalu klik Tambah | Muncul pesan "Semua field harus diisi!" |
| Harga diisi huruf (mis. `abc`) | Muncul pesan harga dan jumlah harus angka |

### Tahap 4 (GUI)
| Skenario | Hasil yang diharapkan |
|---|---|
| Klik Cetak Struk | Status "Mencetak struk...", setelah 2 detik menjadi "Struk selesai" |
| Klik Tambah atau Hitung Total saat status "Mencetak struk..." | Tombol tetap responsif, jendela tidak macet |

---

## Konsep PBO yang Diterapkan
- **Enkapsulasi**: atribut `subtotal` (di `Pesanan`) dan `nama` (di `Kasir`) bersifat `private`, diakses lewat method.
- **Pewarisan (inheritance)**: `Kasir extends Thread`, dan `KasirFrame extends JFrame`.
- **Overriding**: `run()` di `Kasir` menimpa method `run()` milik `Thread`.
- **Multithreading**: `start()`, `sleep()`, dan `join()` untuk konkurensi.
- **Event handling**: `ActionListener` dengan lambda pada tombol.
- **Penanganan exception**: `NumberFormatException` dan `InterruptedException`.

## Screenshot
Tambahkan tangkapan layar hasil di sini:

1. Output Tahap 1: `screenshots/tahap1.png`
2. Output Tahap 2 (eksekusi pertama dan kedua): `screenshots/tahap2a.png`, `screenshots/tahap2b.png`
3. GUI Tahap 3 dengan total 63.000: `screenshots/tahap3.png`
4. Pesan validasi `JOptionPane`: `screenshots/validasi.png`
5. GUI Tahap 4 (status mencetak dan selesai): `screenshots/tahap4.png`

```markdown
![Tahap 1](screenshots/tahap1.png)
```

## Troubleshooting
| Masalah | Penyebab dan solusi |
|---|---|
| `class Kasir is public, should be declared in a file named Kasir.java` | Class `public` harus berada di file bernama sama. Pisahkan setiap class ke file sendiri. |
| `cannot find symbol: class KasirFrame` | File `KasirFrame.java` salah isi atau tidak ada. Pastikan berisi class `KasirFrame`. |
| `package ... does not match the expected package` | Hapus baris `package ...;` atau pindahkan file ke package yang sesuai. |
| Input `15.000` memunculkan pesan error | Ketik angka tanpa titik ribuan, misalnya `15000`. |
| Jendela tidak muncul di terminal tanpa layar | Swing butuh lingkungan grafis; jalankan di komputer dengan desktop. |

---

Dibuat oleh **Sepfi Nangsa Rigianti (L0325013)** untuk keperluan praktikum PBO, Informatika UNS.
