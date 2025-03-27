# 📚 Backend Library Muhriz

## 🛠️ Setup & Running the Project

### 1️⃣ Prerequisites
Pastikan Anda telah menginstal:
- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/downloads)

### 2️⃣ Clone Repository
```sh
git clone https://github.com/rizki892/library-muhriz.git
cd library-muhriz
```

### 3️⃣ Konfigurasi Database
Pastikan PostgreSQL berjalan, lalu buat database baru.

```sql
CREATE DATABASE library_muhriz;
```

### 4️⃣ Konfigurasi Environment
Buat file  `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/library_muhriz
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 5️⃣ Build & Jalankan Aplikasi
```sh
mvn clean install
mvn spring-boot:run
```
Aplikasi akan berjalan di `http://localhost:8080`

### 6️⃣ Dokumentasi API
Gunakan Postman atau akses Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 🚀 Running with Docker (Optional)
```sh
docker-compose up -d
```

---

⚡ Backend Library Muhriz siap digunakan! Jika ada kendala, cek log atau pastikan dependensi sudah terinstall dengan benar.
