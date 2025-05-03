# 🅿️ ParkingLot Microservice

**ParkingLot**, mikroservis mimarisi ile geliştirilmiş bir otopark rezervasyon sistemidir. Kullanıcılar; otoparklardaki mevcut alanları görüntüleyebilir, rezervasyon yapabilir ve ödeme işlemlerini güvenli bir şekilde gerçekleştirebilir.

---

## 🛠️ Kullanılan Teknolojiler

| Teknoloji             | Açıklama                                           |
|-----------------------|----------------------------------------------------|
| Java 17               | Backend dili                                       |
| Spring Boot           | Uygulama geliştirme çatısı                         |
| Spring Cloud Gateway  | API yönlendirme ve güvenlik                        |
| Spring Security       | Güvenlik yapılandırmaları                          |
| Keycloak              | Merkezi kimlik doğrulama ve yetkilendirme sistemi |
| OpenFeign             | Servisler arası REST iletişimi                     |
| Kafka                 | Asenkron mikroservis iletişimi                     |
| Stripe API            | Ödeme işlemleri                                    |
| PostgreSQL            | Veritabanı yönetimi                                |
| Redis                 | Önbellekleme ve geçici veri saklama                |
| Lombok                | Kod tekrarını azaltmak için                        |


---

## 🔍 Mikroservisler

### 1. **User Service**
- Kullanıcı kayıt, giriş ve profil işlemleri.
- Keycloak ile entegre çalışır.
- Kimlik doğrulama ve rol bazlı yetkilendirme Keycloak üzerinden gerçekleştirilir.

### 2. **Parking Service**
- Otopark bilgileri yönetilir.
- Kullanıcılar mevcut alanları görebilir ve konum bazlı filtreleme yapabilir.
- Admin kullanıcıları yeni otoparklar ekleyebilir ve kapasite ayarlaması yapabilir.

### 3. **Reservation Service**
- Kullanıcılar, otoparklara rezervasyon yapabilir ve geçmiş rezervasyonlarını görüntüleyebilir.
- Rezervasyon sırasında otopark kapasitesi kontrol edilir.
- Kafka üzerinden Payment ve Email servislerine mesaj gönderilir.

### 4. **Payment Service**
- Stripe API entegrasyonu ile güvenli ödeme işlemleri gerçekleştirilir.
- Ödeme başarılı/başarısız bilgisi Kafka üzerinden Email servisine iletilir.
- Ödeme geçmişi kullanıcı bazında takip edilir.

### 5. **Email Service**
- Kafka’dan gelen rezervasyon ve ödeme mesajlarına göre e-posta gönderimleri yapılır.
- E-posta bildirimleri (rezervasyon onayı, ödeme sonucu vs.) otomatik tetiklenir.

### 6. **Report Service**
- Raporlama ve istatistik servisi.
- Admin kullanıcıları için otopark doluluk oranı, rezervasyon sayıları ve finansal veriler sunulur.

---

## 🚀 Kurulum

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/Berkansevil/ParkingLot-Microservice.git
cd ParkingLot-Microservice
```

### 2. Veritabanı Yapılandırması
`application.yml` ya da `.env` dosyasına aşağıdaki bilgileri ekleyin:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/parkingdb
    username: postgres
    password: yourpassword
```

### 3. Keycloak Kurulumu
- Yeni bir realm oluşturun (örneğin: `parking-lot-realm`)
- Her servis için bir client tanımlayın
- `USER`, `ADMIN` rollerini oluşturun
- İlgili kullanıcıları ve rolleri Keycloak panelinden yönetin

### 4. Stripe API Anahtarları
- Stripe'dan bir test hesabı oluşturun
- `application.yml` dosyasına API anahtarını ekleyin:

```yaml
stripe:
  api-key: your_stripe_secret_key
```

---

## 📡 API İletişimi

- Mikroservisler RESTful mimari ile haberleşir.
- Servisler arası çağrılar OpenFeign üzerinden yapılır.
- Swagger (SpringDoc) ile her servise özel API dokümantasyonu sağlanabilir.

---

## 🔗 Daha Fazlası

📁 Projenin tüm kaynak kodlarına ve detaylarına aşağıdaki GitHub bağlantısından ulaşabilirsiniz:

👉 [ParkingLot-Microservice GitHub Repo](https://github.com/Berkansevil/ParkingLot-Microservice)
