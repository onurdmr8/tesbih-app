# Zikirmatik & Akıllı Tesbih (Android)

Modern, şık ve zengin özellikli bir dijital tesbih ve zikirmatik Android uygulaması. Jetpack Compose ve Material 3 ile geliştirilmiştir.

## ✨ Özellikler

- **Gerçekçi Dijital Zikirmatik Arayüzü**:
  - Ergonomik cihaz gövdesi ve fosforlu dijital LCD ekran.
  - 3D dokunsal sayaç butonu (basıldığında gerçekçi yaylanma animasyonu).
  - Tur sayacı (her hedef tamamlandığında tur otomatik artar).
  - Yanlış basımları düzeltmek için **Geri Al (-1)** butonu.
  - Kazara sıfırlamaları önleyen onay pencereli **Sıfırla** butonu.

- **Kapsamlı Zikir Kütüphanesi**:
  - Sübhanallah (33), Elhamdülillah (33), Allahu Ekber (33), Lâ ilâhe illallâh (100), Estağfirullah (100), Salavat-ı Şerife (100), vb.
  - Arapça hat, Türkçe okunuş ve fazilet/anlam açıklamaları.
  - **Özel Zikir Ekleme**: İstediğiniz dua veya zikri kendi belirlediğiniz hedefle ekleyebilme.

- **Çoklu Geri Bildirim**:
  - Dokunsal Titreşim (Haptic Feedback) - Her zikirde hafif, hedef tamamlandığında kutlama titreşimi.
  - Akustik Ses Efekti (Audio Click) - Açılıp kapatılabilir mekanik tıklama sesi.

- **Tam Ekran Dokunma Modu (Kör Mod)**:
  - Yürürken veya ekrana bakmadan zikir çekebilmeniz için tüm ekranı sayaç butonuna dönüştürür.

- **Kalıcı Hafıza & Zikir Geçmişi**:
  - Uygulama kapansa dahi en son kalınan zikir, sayaç ve turlar SharedPreferences ile saklanır.
  - Tamamlanan zikirler tarih ve saat bilgisiyle geçmişe kaydedilir.

- **5 Farklı Zikirmatik Teması**:
  - Zümrüt Yeşili & Altın
  - Gece Mavisi & Safir
  - Gül Ağacı & Bronz
  - Metalik Titanyum
  - OLED Gece Siyahı (Pil tasarruflu karanlık mod)

## 🛠️ Teknolojiler
- **Kotlin** & **Jetpack Compose**
- **Material Design 3**
- **Architecture**: MVVM, StateFlow, Coroutines
- **Target SDK**: Android 36 (Android 15+)
- **Min SDK**: Android 24 (Android 7.0+)

## 🚀 Derleme ve Çalıştırma

Projeyi doğrudan Android Studio ile açabilir veya komut satırından derleyebilirsiniz:

```bash
# Debug APK derleme
./gradlew assembleDebug
```
