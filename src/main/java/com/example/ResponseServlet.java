package com.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * ResponseServlet - HTTP Response Header ve Status kodlarını 
 * yönetmek için örnek bir servlet.
 */
@WebServlet("/response")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class ResponseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * POST isteğini işleyip, formdan gelen verileri alır ve
     * HTTP response header'larını ve status kodlarını ayarlar.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Response içeriğini UTF-8 olarak ayarla
        response.setContentType("text/html;charset=UTF-8");
        
        // Özel header'lar ekle
        response.setHeader("X-Server-Name", "Java-Servlet-Demo");
        
        // İşlem zamanını header olarak ekle
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        response.setHeader("X-Process-Time", dateFormat.format(new Date()));
        
        try {
            // Form verilerini al
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            Part filePart = request.getPart("cvFile");
            
            // Form alanlarının boş olup olmadığını kontrol et
            if (fullName == null || fullName.trim().isEmpty() || 
                email == null || email.trim().isEmpty() ||
                filePart == null || filePart.getSize() == 0) {
                
                // Eksik veri varsa HTTP 400 Bad Request
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                showResponse(response, "HTTP 400 Bad Request", 
                        "Form alanlarından biri veya birkaçı eksik.", 
                        fullName, email, filePart);
                return;
            }
            
            // Dosyayı kaydet
            String fileName = getSubmittedFileName(filePart);
            boolean uploadSuccess = saveFile(filePart, fileName);
            
            if (uploadSuccess) {
                // Başarılı ise HTTP 200 OK
                response.setStatus(HttpServletResponse.SC_OK);
                showResponse(response, "HTTP 200 OK", 
                        "Form başarıyla işlendi ve dosya yüklendi.", 
                        fullName, email, filePart);
            } else {
                // Başarısız ise HTTP 500 Internal Server Error
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                showResponse(response, "HTTP 500 Internal Server Error", 
                        "Dosya yüklenirken bir hata oluştu.", 
                        fullName, email, filePart);
            }
            
        } catch (Exception e) {
            // Herhangi bir hata durumunda HTTP 500 Internal Server Error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            showResponse(response, "HTTP 500 Internal Server Error", 
                    "İşlem sırasında bir hata oluştu: " + e.getMessage(), 
                    null, null, null);
        }
    }
    
    /**
     * Kullanıcıya form verilerini, header bilgilerini ve HTTP durum kodunu
     * gösteren bir cevap üretir.
     */
    private void showResponse(HttpServletResponse response, String statusTitle, 
            String statusMessage, String fullName, String email, Part filePart) 
            throws IOException {
        
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>İşlem Sonucu</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }");
        out.println("        .card { border: 1px solid #ddd; border-radius: 5px; padding: 20px; margin-bottom: 20px; }");
        out.println("        .status { padding: 10px; border-radius: 5px; margin-bottom: 15px; }");
        out.println("        .status-200 { background-color: #d4edda; color: #155724; }");
        out.println("        .status-400 { background-color: #f8d7da; color: #721c24; }");
        out.println("        .status-500 { background-color: #f8d7da; color: #721c24; }");
        out.println("        table { width: 100%; border-collapse: collapse; }");
        out.println("        th, td { text-align: left; padding: 8px; border-bottom: 1px solid #ddd; }");
        out.println("        th { background-color: #f2f2f2; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <h1>İşlem Sonucu</h1>");
        
        // Status kodu ve mesajı
        String statusClass = "status-" + response.getStatus();
        out.println("    <div class='card'>");
        out.println("        <h2>HTTP Status</h2>");
        out.println("        <div class='status " + statusClass + "'>");
        out.println("            <h3>" + statusTitle + "</h3>");
        out.println("            <p>" + statusMessage + "</p>");
        out.println("        </div>");
        out.println("    </div>");
        
        // Form verileri
        out.println("    <div class='card'>");
        out.println("        <h2>Form Verileri</h2>");
        out.println("        <table>");
        out.println("            <tr><th>Alan</th><th>Değer</th></tr>");
        
        if (fullName != null) {
            out.println("            <tr><td>Ad Soyad</td><td>" + fullName + "</td></tr>");
        }
        
        if (email != null) {
            out.println("            <tr><td>E-posta</td><td>" + email + "</td></tr>");
        }
        
        if (filePart != null) {
            out.println("            <tr><td>Dosya Adı</td><td>" + getSubmittedFileName(filePart) + "</td></tr>");
            out.println("            <tr><td>Dosya Boyutu</td><td>" + filePart.getSize() + " byte</td></tr>");
        }
        
        out.println("        </table>");
        out.println("    </div>");
        
        // Response Header'lar
        out.println("    <div class='card'>");
        out.println("        <h2>Response Header Bilgileri</h2>");
        out.println("        <table>");
        out.println("            <tr><th>Header</th><th>Değer</th></tr>");
        
        for (String headerName : response.getHeaderNames()) {
            out.println("            <tr><td>" + headerName + "</td><td>" + response.getHeader(headerName) + "</td></tr>");
        }
        
        out.println("        </table>");
        out.println("    </div>");
        
        out.println("    <a href='form.jsp'>Forma Geri Dön</a>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Part nesnesinden dosya adını almanın güvenli bir yolu.
     * Farklı tarayıcılar ve uygulamalar farklı yollar kullanabilir.
     */
    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
    
    /**
     * Yüklenen dosyayı sisteme kaydetmeye çalışır.
     */
    private boolean saveFile(Part filePart, String fileName) {
        try {
            // Kaydedilecek dosya dizini
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // Dosyayı kaydet
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}