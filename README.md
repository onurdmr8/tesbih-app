# Zikirmatik & Akıllı Tesbih (Android)

Modern, şık ve zengin özellikli bir dijital tesbih ve zikirmatik Android uygulaması. Jetpack Compose ve Material 3 ile geliştirilmiştir.

## ✨ Özellikler

- **Çoklu Dil Desteği (Global Erişim)**:
  - 🇹🇷 **Türkçe** (Varsayılan)
  - 🇬🇧 **İngilizce** (Digital Tasbih & Dhikr)
  - 🇸🇦 **Arapça** (المسبحة الإلكترونية والأذكار)
  - 🇩🇪 **Almanca** (Digitaler Tasbih & Zikr)
  - Cihazın sistem diline göre tüm arayüz, butonlar ve mesajlar otomatik olarak kendi dilinde açılır.

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

- **Gelişmiş Google AdMob Monetizasyonu**:
  - **Sabit Alt Banner Reklam**: Ekranın alt kısmında kullanıcı deneyimini bozmayan sürekli gelir kaynağı.
  - **Akıllı Geçiş Reklamı (Interstitial)**: Her 3 tur tamamlandığında ve en az 90 saniye arayla saygılı bir şekilde gösterilen yüksek eCPM gelirli geçiş reklamı.

---

## 💰 Reklam Geliri & Google Play Yayını (Nasıl Para Kazanılır?)

1. **Google AdMob Hesabı**:
   - [Google AdMob](https://admob.google.com) adresinden uygulamanız oluşturuldu.
   - Uygulama Kimliği (`APPLICATION_ID`) ve Banner Birimi (`BANNER_AD_UNIT_ID`) kodlara entegre edildi.

2. **Geçiş (Interstitial) Reklam Birimi Ekleme**:
   - AdMob panelinizde **Reklam Birimleri > Reklam Birimi Ekle > Geçiş (Interstitial)** seçin.
   - Size verilen `ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy` kodunu `app/src/main/kotlin/com/example/zikirmatik/util/InterstitialAdManager.kt` içindeki `INTERSTITIAL_AD_UNIT_ID` kısmına yazarak geçiş reklamlarını da aktif edebilirsiniz.

3. **Google Play Store'a Yükleme**:
   - Google Play Console geliştirici hesabı açın.
   - `./gradlew bundleRelease` komutu ile veya Android Studio'dan `.aab` dosyası oluşturun.
   - Play Console'a yükleyip uygulamanızı yayınlayın.

---

## 🛠️ Teknolojiler
- **Kotlin** & **Jetpack Compose**
- **Material Design 3**
- **Google Mobile Ads SDK** (AdMob v23.6.0)
- **Architecture**: MVVM, StateFlow, Coroutines
- **Target SDK**: Android 36 (Android 15+)
- **Min SDK**: Android 24 (Android 7.0+)
