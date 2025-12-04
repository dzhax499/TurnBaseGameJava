/**
 * Panduan Dokumentasi JavaDoc untuk Proyek TurnBaseGameJava
 * 
 * File ini berisi template dan contoh dokumentasi yang harus diterapkan
 * pada semua file Java dalam proyek.
 */

// ============================================================================
// 1. DOKUMENTASI CLASS
// ============================================================================

/**
 * Deskripsi singkat class (satu baris).
 * 
 * <p>
 * Deskripsi detail tentang tujuan dan fungsi class ini.
 * Jelaskan tanggung jawab utama class dan bagaimana class ini
 * berinteraksi dengan komponen lain dalam sistem.
 * 
 * <p>
 * Jika ada design pattern yang digunakan, sebutkan di sini.
 * Contoh: "Class ini mengimplementasikan Strategy Pattern untuk..."
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see RelatedClass1
 * @see RelatedClass2
 */
public class ExampleClass {

    // ============================================================================
    // 2. DOKUMENTASI FIELD/ATRIBUT
    // ============================================================================

    /** Deskripsi singkat untuk field sederhana */
    private int simpleField;

    /**
     * Deskripsi detail untuk field kompleks.
     * Gunakan format multi-line jika perlu penjelasan lebih.
     */
    private ComplexType complexField;

    // ============================================================================
    // 3. DOKUMENTASI CONSTRUCTOR
    // ============================================================================

    /**
     * Konstruktor untuk membuat instance baru.
     * 
     * <p>
     * Jelaskan apa yang dilakukan constructor, termasuk
     * inisialisasi field dan validasi input.
     * 
     * @param param1 Deskripsi parameter pertama
     * @param param2 Deskripsi parameter kedua
     * @throws IllegalArgumentException jika parameter tidak valid
     * @throws NullPointerException     jika parameter null
     */
    public ExampleClass(String param1, int param2) {
        // Implementation
    }

    // ============================================================================
    // 4. DOKUMENTASI METHOD
    // ============================================================================

    /**
     * Deskripsi singkat apa yang dilakukan method ini.
     * 
     * <p>
     * Deskripsi detail tentang:
     * <ul>
     * <li>Apa yang dilakukan method</li>
     * <li>Bagaimana cara kerjanya (jika kompleks)</li>
     * <li>Side effects yang mungkin terjadi</li>
     * <li>Kondisi khusus atau edge cases</li>
     * </ul>
     * 
     * <p>
     * Jika ada formula atau algoritma khusus, jelaskan di sini:
     * <p>
     * Formula: result = (param1 * param2) / constant
     * 
     * @param param1 Deskripsi parameter pertama
     * @param param2 Deskripsi parameter kedua
     * @return Deskripsi nilai yang dikembalikan
     * @throws ExceptionType jika kondisi tertentu terjadi
     * @see RelatedMethod
     */
    public int exampleMethod(String param1, int param2) {
        // Implementation
        return 0;
    }

    /**
     * Method sederhana dengan dokumentasi minimal.
     * 
     * @return Nilai yang dikembalikan
     */
    public String getSimpleValue() {
        return "value";
    }

    // ============================================================================
    // 5. DOKUMENTASI METHOD GETTER/SETTER
    // ============================================================================

    /**
     * Mendapatkan nilai field.
     * 
     * @return Nilai field saat ini
     */
    public int getField() {
        return simpleField;
    }

    /**
     * Mengatur nilai field.
     * Tambahkan keterangan jika ada validasi atau side effect.
     * 
     * @param value Nilai baru untuk field
     * @throws IllegalArgumentException jika value tidak valid
     */
    public void setField(int value) {
        this.simpleField = value;
    }

    // ============================================================================
    // 6. DOKUMENTASI ENUM
    // ============================================================================

    /**
     * Enum untuk tipe-tipe tertentu.
     * Jelaskan tujuan enum dan kapan digunakan.
     */
    public enum ExampleEnum {
        /** Deskripsi untuk VALUE1 */
        VALUE1,

        /** Deskripsi untuk VALUE2 */
        VALUE2,

        /** Deskripsi untuk VALUE3 */
        VALUE3
    }

    // ============================================================================
    // 7. DOKUMENTASI INNER CLASS
    // ============================================================================

    /**
     * Inner class untuk menyimpan data tertentu.
     * Jelaskan tujuan inner class dan hubungannya dengan outer class.
     */
    public static class InnerClass {
        /** Field dalam inner class */
        private String data;

        /**
         * Konstruktor inner class.
         * 
         * @param data Data yang akan disimpan
         */
        public InnerClass(String data) {
            this.data = data;
        }
    }

    // ============================================================================
    // 8. DOKUMENTASI METHOD ABSTRACT
    // ============================================================================

    /**
     * Method abstrak yang harus diimplementasikan oleh subclass.
     * 
     * <p>
     * Jelaskan apa yang diharapkan dari implementasi method ini,
     * termasuk kontrak yang harus dipenuhi oleh subclass.
     * 
     * @param param Parameter yang dibutuhkan
     * @return Nilai yang harus dikembalikan oleh implementasi
     */
    public abstract String abstractMethod(int param);

    // ============================================================================
    // 9. KOMENTAR INLINE UNTUK LOGIKA KOMPLEKS
    // ============================================================================

    public void complexLogic() {
        // Step 1: Validasi input
        // Jelaskan mengapa validasi ini diperlukan

        // Step 2: Perhitungan utama
        // Jika ada formula kompleks, jelaskan di sini
        int result = 0;

        // Step 3: Post-processing
        // Jelaskan transformasi atau adjustment yang dilakukan
    }

    // ============================================================================
    // 10. TAG JAVADOC YANG BERGUNA
    // ============================================================================

    /**
     * Method dengan berbagai tag JavaDoc.
     * 
     * @param param Parameter input
     * @return Nilai return
     * @throws Exception Kondisi exception
     * @see RelatedClass#relatedMethod()
     * @since 1.0
     * @deprecated Gunakan {@link #newMethod()} sebagai gantinya
     */
    @Deprecated
    public String methodWithTags(String param) throws Exception {
        return "";
    }

}

// ============================================================================
// PRIORITAS DOKUMENTASI
// ============================================================================

/*
 * PRIORITAS TINGGI (Harus didokumentasikan dengan detail):
 * 1. Public classes dan interfaces
 * 2. Public methods
 * 3. Abstract methods
 * 4. Complex algorithms atau business logic
 * 5. Constructor dengan validasi
 * 
 * PRIORITAS SEDANG:
 * 1. Protected methods
 * 2. Package-private classes
 * 3. Important fields
 * 
 * PRIORITAS RENDAH (Boleh dokumentasi minimal):
 * 1. Simple getters/setters
 * 2. Private helper methods yang straightforward
 * 3. Obvious utility methods
 */
