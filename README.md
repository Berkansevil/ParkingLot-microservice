# 🅿️ ParkingLot Microservice

Bu proje, **mikroservis mimarisi** ile geliştirilmiş bir **otopark rezervasyon sistemidir**. Kullanıcılar, otoparklarda mevcut alanları görebilir, rezervasyon yapabilir ve ödeme işlemlerini gerçekleştirebilirler. Proje **Spring Boot** ile geliştirilmiş olup, her bir servis kendi başına çalışabilen ve bağımsız olarak ölçeklendirilebilen mikroservislerden oluşmaktadır.

Projede, **Spring Security**, **JWT** tabanlı kimlik doğrulama, ve **Keycloak** entegrasyonu kullanılarak güvenli bir yapı sağlanmıştır. Ayrıca, **PostgreSQL** veritabanı kullanılarak veri yönetimi yapılmıştır. 

## 🛠️ Kullanılan Teknolojiler
- **Java 17**
- **Spring Boot** (REST API geliştirme)
- **Spring Security** (Güvenlik)
- **JWT** (Kimlik doğrulama)
- **Keycloak** (Kimlik ve yetkilendirme yönetimi)
- **PostgreSQL** (Veritabanı yönetimi)
- **OpenFeign** (Mikroservisler arası iletişim)
- **Lombok** (Kod sadeleştirme)

## 📌 Mikroservisler
Bu projede aşağıdaki mikroservisler bulunmaktadır:

1. **User Service**  
   Kullanıcıların kaydını ve kimlik doğrulama işlemlerini gerçekleştiren mikroservis.

2. **Parking Service**  
   Otopark bilgilerini, otoparklarda mevcut alanları ve otopark bilgilerini yöneten mikroservis.

3. **Reservation Service**  
   Kullanıcıların otoparklarda yer ayırtmasını sağlayan mikroservis.

4. **Payment Service**  
   Kullanıcıların rezervasyonlarının ödemelerini işleyen mikroservis.

5. **Email Service**  
   Kullanıcılarla iletişime geçmek için e-posta gönderen mikroservis. Örneğin, rezervasyon onayı, hatırlatma e-postaları vb.

6. **Report Service**  
   Otoparklar, rezervasyonlar ve ödemelerle ilgili raporlar oluşturan ve yöneticilere sunan mikroservis.

## 🚀 Kurulum ve Çalıştırma

Projeyi bilgisayarınıza klonlamak için aşağıdaki adımları izleyebilirsiniz:

1. Projeyi GitHub'dan klonlayın:
   ```sh
   git clone https://github.com/kullanici-adi/ParkingLot-Microservice.git
   cd ParkingLot-Microservice
