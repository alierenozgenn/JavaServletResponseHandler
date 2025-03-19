<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>HTTP Response Header Demo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="email"], input[type="file"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .error {
            color: red;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <h1>HTTP Response Header Demo</h1>
    <p>Bu form HTTP response header'ları ve status kodlarını test etmek için kullanılır.</p>
    
    <!-- enctype="multipart/form-data" dosya yüklemek için gerekli -->
    <form action="response" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="fullName">Ad Soyad:</label>
            <input type="text" id="fullName" name="fullName" required>
        </div>
        
        <div class="form-group">
            <label for="email">E-posta:</label>
            <input type="email" id="email" name="email" required>
        </div>
        
        <div class="form-group">
            <label for="cvFile">CV Dosyası:</label>
            <input type="file" id="cvFile" name="cvFile" required>
            <small>Maksimum dosya boyutu: 5MB</small>
        </div>
        
        <button type="submit">Gönder</button>
    </form>
</body>
</html>