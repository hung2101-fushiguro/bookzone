<%-- 
    Document   : admin
    Created on : Jul 9, 2025, 5:31:23 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang Quản Lý Admin - BookZone</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f0f4f7;
                margin: 0;
                padding: 0;
                color: #333;
            }

            .header {
                background-color: #4fc3f7; /* Xanh nước biển nhạt */
                color: white;
                text-align: center;
                padding: 30px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            .header h1 {
                margin: 0;
                font-size: 28px;
            }

            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 50px;
                flex-wrap: wrap;
            }

            .dashboard-card {
                background-color: #e1f5fe;
                border-radius: 12px;
                width: 250px;
                margin: 20px;
                text-align: center;
                padding: 30px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .dashboard-card:hover {
                transform: translateY(-8px);
                box-shadow: 0 6px 18px rgba(0, 0, 0, 0.2);
            }

            .card-title {
                font-size: 20px;
                font-weight: 600;
                margin-bottom: 20px;
            }

            .card-link {
                display: inline-block;
                font-size: 16px;
                color: #ffffff;
                text-decoration: none;
                padding: 12px 20px;
                border-radius: 8px;
                background-color: #0288d1; /* Màu xanh đậm */
                transition: background-color 0.3s ease, transform 0.3s ease;
            }

            .card-link:hover {
                background-color: #0277bd;
                transform: scale(1.05);
            }

            .logout-button {
                background-color: #d32f2f;
                color: white;
                padding: 12px 20px;
                text-align: center;
                border-radius: 8px;
                text-decoration: none;
                margin-top: 40px;
                display: inline-block;
                font-size: 16px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: background-color 0.3s ease, transform 0.3s ease;
            }

            .logout-button:hover {
                background-color: #c62828;
                transform: scale(1.05);
            }
        </style>
    </head>

    <body>

        <!-- Header -->
        <div class="header">
            <h1>Chào mừng đến với Trang Quản Lý Admin - BookZone</h1>
        </div>

        <!-- Main Container -->
        <div class="container">
            <!-- Card Quản lý Sách -->
            <div class="dashboard-card">
                <div class="card-title">Quản lý Sách</div>
                <a href="${pageContext.request.contextPath}/books" class="card-link">Quản lý Sách</a>
            </div>

            <!-- Card Quản lý Người Dùng -->
            <div class="dashboard-card">
                <div class="card-title">Quản lý Người Dùng</div>
                <a href="${pageContext.request.contextPath}/user" class="card-link">Quản lý Người Dùng</a>
            </div>


            <!-- Card Quản lý Đơn Hàng -->
            <div class="dashboard-card">
                <div class="card-title">Quản lý Đơn Hàng</div>
                <a href="${pageContext.request.contextPath}/orders" class="card-link">Xem Đơn Hàng</a>
            </div>

            <div class="dashboard-card">
                <div class="card-title">Quản lý Phụ kiện</div>
                <a href="${pageContext.request.contextPath}/adminaccessory" class="card-link">Quản lý Phụ kiện</a>
            </div>
        </div>

        <!-- Đăng xuất -->
        <div style="text-align: center;">
            <a href="${pageContext.request.contextPath}/logout" class="logout-button">Đăng Xuất</a>
        </div>

    </body>

</html>