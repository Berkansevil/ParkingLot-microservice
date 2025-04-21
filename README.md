# 🄹️ ParkingLot Microservice

**ParkingLot**, mikroservis mimarisi ile gelistirilmis bir otopark rezervasyon sistemidir. Kullanıcılar; otoparklardaki mevcut alanları görüntüleyebilir, rezervasyon yapabilir ve ödeme işlemlerini güvenli bir şekilde gerçekleştirebilir.  

Sistem, Spring Boot kullanılarak inşa edilmiş olup, yüksek erişilebilirlik, esneklik ve kolay ölçeklenebilirlik hedeflenmiştir.

---

## 📚 İçindekiler

- [Özellikler](#özellikler)
- [Mimari](#mimari)
- [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
- [Mikroservisler](#mikroservisler)
- [Kurulum](#kurulum)
- [API İletisimi](#api-iletisimi)
- [Katkıda Bulunma](#katkıda-bulunma)

---

## ✨ Özellikler

- JWT ve Keycloak ile kimlik doğrulama ve yetkilendirme
- Stripe API ile güvenli ödeme altyapısı
- Mikroservis mimarisi ile esnek ve bağımsız servis yapısı
- PostgreSQL ile kalıcı veri saklama
- OpenFeign ile servisler arası RESTful iletişim
- E-posta bildirimleri ve detaylı raporlama sistemi

---

## 🛠️ Mimari

Proje, bağımsız çalışabilen ve ihtiyaca göre ölçeklenebilen mikroservislerden oluşmaktadır. Servisler arası iletişim HTTP REST ve OpenFeign aracılığıyla sağlanır.

```
Kullanıcı -> API Gateway -> [User | Parking | Reservation | Payment | Email | Report] Service
                                            |
                                     Keycloak Server
```

---

## 🛠️ Kullanılan Teknolojiler

- Java 17  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Token)  
- Keycloak  
- PostgreSQL  
- OpenFeign  
- Lombok  
- Stripe API  
- Docker (isteğe bağlı olarak)

---

## 🛌 Mikroservisler

### 1. User Service
- Kullanıcı kayıt, giriş, profil ve kimlik doğrulama işlemleri.

### 2. Parking Service
- Otopark bilgileri, mevcut alanlar, konum bazlı arama.

### 3. Reservation Service
- Rezervasyon oluşturma, iptal etme ve listeleme.

### 4. Payment Service
- Stripe API ile ödeme alma, ödeme geçmişi takibi.

### 5. Email Service
- Rezervasyon onayı, hatırlatma ve bildirim e-postalarının gönderimi.

### 6. Report Service
- Yönetici paneli için otopark ve rezervasyon raporları oluşturma.

---

## 🚀 Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/Berkansevil/ParkingLot-Microservice.git
cd ParkingLot-Microservice
```

2. Ortam değişkenlerini ayarlayın (application.yml veya .env):
```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/parkingdb
    username: postgres
    password: password
...
```

3. Her mikroservisi bağımsız olarak çalıştırabilir veya Docker Compose ile butun sistem ayağa kaldırılabilir.

4. Keycloak kurulumunu yapın ve client/realm ayarlarını yapılandırın.

5. Stripe API anahtarını sisteminize eklemeyi unutmayın.

---

## 🔗 API İletisimi

Servisler, REST API mantığıyla haberleşir. OpenFeign kullanılarak bir servisten diğerine istek yapılabilir. Swagger/OpenAPI entegrasyonu da eklenerek API dokümantasyonu sunulabilir.

---


Proje sahibi: [Berkan Sevil](https://github.com/Berkansevil)

Lisans: MIT

---


