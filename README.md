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

- **Google AdMob Reklam Entegrasyonu**:
  - Ekranın alt kısmında kullanıcı deneyimini bozmayan sabit Banner reklam alanı.
  - Geliştirme ve test aşamasında Google resmi test kimlikleri (`ca-app-pub-3940256099942544...`) kullanılmıştır.

---

## 💰 Reklam Geliri & Google Play Yayını (Nasıl Para Kazanılır?)

1. **Google AdMob Hesabı Oluşturma**:
   - [Google AdMob](https://admob.google.com) adresine gidip Google hesabınızla giriş yapın.
   - Sol menüden **Uygulamalar > Uygulama Ekle** seçin ve Android platformunu seçin.
   - Size verilen `ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy` formatındaki **Uygulama Kimliğini (App ID)** kopyalayın.
   - Bu ID'yi `app/src/main/AndroidManifest.xml` dosyasındaki `APPLICATION_ID` meta-data kısmına yazın.

2. **Banner Reklam Birimi Oluşturma**:
   - AdMob panelinizde uygulamanızın içine girip **Reklam Birimi Ekle > Banner** seçin.
   - Size verilen `ca-app-pub-xxxxxxxxxxxxxxxx/zzzzzzzzzz` formatındaki **Reklam Birimi Kimliğini** kopyalayın.
   - Bu ID'yi `app/src/main/kotlin/com/example/zikirmatik/ui/components/AdBanner.kt` içerisindeki `BANNER_AD_UNIT_ID` sabitine yapıştırın.

3. **Google Play Store'a Yükleme**:
   - Google Play Console geliştirici hesabı açın (tek seferlik \$25).
   - Android Studio üzerinden `Build > Generate Signed Bundle / APK > Android App Bundle (.aab)` oluşturun.
   - Google Play Console'a yükleyip uygulamanızı yayınlayın.
   - Reklamlar görüntülendikçe ve tıklandıkça geliriniz AdMob hesabınızda birikir ve her ay banka hesabınıza aktarılır.

---

## 🛠️ Teknolojiler
- **Kotlin** & **Jetpack Compose**
- **Material Design 3**
- **Google Mobile Ads SDK** (AdMob v23.6.0)
- **Architecture**: MVVM, StateFlow, Coroutines
- **Target SDK**: Android 36 (Android 15+)
- **Min SDK**: Android 24 (Android 7.0+)

## 🚀 Derleme ve Çalıştırma

```bash
# Debug APK derleme
./gradlew assembleDebug

# Release AAB derleme (Play Store için)
./gradlew bundleRelease
```
