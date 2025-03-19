# HTTP Response Header Demo

Bu proje, Java Servlet teknolojilerini kullanarak HTTP response header'ları ve HTTP durum (status) kodlarının nasıl kullanıldığını öğrenmek amacıyla geliştirilmiş bir web uygulamasıdır.

## Proje Açıklaması

Bu web uygulaması, kullanıcıların ad-soyad, e-posta ve CV dosyası yükleyebileceği bir form sunmaktadır. Form verileri bir Servlet tarafından işlenir ve çeşitli HTTP durum kodları ve özel response header'lar döndürülür.

### Özellikler

- Form alanları (Ad Soyad, E-posta, Dosya Yükleme) validasyonu
- HTTP durum kodları (200 OK, 400 Bad Request, 500 Internal Server Error)
- Özel HTTP response header'ları
- Dosya yükleme işlemleri
- İşlem sonucu görselleştirme

## Teknolojiler

- Java Servlet 4.0
- JSP (JavaServer Pages)
- HTML/CSS
- Maven
- Tomcat

## Proje Yapısı

        my-webapp/
        ├── src/
        │   ├── main/
        │   │   ├── java/
        │   │   │   └── com/
        │   │   │       └── example/
        │   │   │           └── ResponseServlet.java
        │   │   ├── webapp/
        │   │   │   ├── WEB-INF/
        │   │   │   │   └── web.xml
        │   │   │   └── form.jsp
        │   │   └── resources/
        │   └── test/
        └── pom.xml

## Kurulum ve Çalıştırma

### Gereksinimler

- JDK 11 veya üzeri
- Apache Maven 3.6.3 veya üzeri
- Apache Tomcat 9.0 veya üzeri

### Kurulum Adımları

1. Projeyi klonlayın:

        git clone https://github.com/kullaniciadi/http-response-demo.git
        cd http-response-demo

2. Maven ile projeyi derleyin:

        mvn clean package

3. Oluşturulan WAR dosyasını Tomcat'e deploy edin veya aşağıdaki komutu kullanarak projeyi çalıştırın:

        mvn tomcat7:run

4. Tarayıcınızda aşağıdaki URL'yi açın:

        http://localhost:8080/http-response-demo-1.0-SNAPSHOT/

## Kullanım

1. Form sayfasında Ad Soyad, E-posta ve CV Dosyası alanlarını doldurun
2. "Gönder" butonuna tıklayın
3. Sonuç sayfasında HTTP durum kodu, form verileri ve response header bilgilerini görüntüleyin

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

