<%-- 
    Document   : categoryBooks
    Created on : Jul 4, 2025, 11:32:10 AM
    Author     : Administrator
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sách Theo Danh Mục</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        /* === HEADER styles from bookdetail.jsp === */
        .header_top, .header_main {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 30px;
            background: linear-gradient(90deg, #2193b0, #6dd5ed);
            color: white;
            font-size: 14px;
        }
        .header_main {
            background: white;
            border-bottom: 2px solid #e0e0e0;
            color: black;
        }
        .header_logo img {
            height: 80px;
        }
        .header_search-box {
            flex: 1;
            display: flex;
            justify-content: center;
            margin: 0 40px;
        }
        .search-input {
            width: 400px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px 0 0 4px;
        }
        .search-button {
            padding: 8px 15px;
            border: none;
            background: linear-gradient(90deg, #2193b0, #6dd5ed);
            color: white;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
        }
        .header_utilities {
            display: flex;
            gap: 20px;
            align-items: center;
            font-size: 14px;
        }
        .header_utilities a {
            color: black;
            text-decoration: none;
        }

        /* === Book card styles === */
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 20px;
            background: #f5f5f5;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
        .books-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }
        .book-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            width: 200px;
            text-align: center;
            padding: 15px;
            transition: transform 0.3s;
        }
        .book-card:hover {
            transform: translateY(-5px);
        }
        .book-card img {
            width: 100%;
            height: 250px;
            object-fit: contain;
            border-radius: 6px;
        }
        .book-card h2 {
            font-size: 16px;
            margin: 10px 0;
            height: 40px;
            overflow: hidden;
        }
        .book-price {
            font-size: 16px;
            color: #e53935;
            font-weight: bold;
        }
        .old-price {
            font-size: 14px;
            color: #888;
            text-decoration: line-through;
            margin-left: 8px;
        }
        .discount-badge {
            display: inline-block;
            background-color: #e53935;
            color: white;
            font-size: 12px;
            padding: 2px 6px;
            border-radius: 4px;
            margin-left: 5px;
        }
    </style>
</head>
<body>

<!-- HEADER giống bookdetail.jsp -->
<div class="header_top">
    <div>Hotline: <strong>0938 424 289</strong></div>
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span>${sessionScope.user.name}</span>
                <a href="${pageContext.request.contextPath}/logout">| Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Đăng Nhập</a>
                <a href="${pageContext.request.contextPath}/register">| Đăng ký</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="header_main">
    <a class="header_logo" href="${pageContext.request.contextPath}/home">
        <img src="image/logo1.jpg" alt="Logo BookZone">
    </a>
    <form class="header_search-box" action="${pageContext.request.contextPath}/search" method="get">
        <input type="text" class="search-input" name="query" placeholder="Tìm kiếm sách...">
        <button type="submit" class="search-button">Tìm</button>
    </form>
    <div class="header_utilities">
        <a href="${pageContext.request.contextPath}/offers">Ưu đãi & Tiện ích</a>
        <a href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng (0)</a>
    </div>
</div>

<!-- Tiêu đề danh mục -->
<h1>${categoryName}</h1>

<!-- Danh sách sách -->
<div class="books-container">
    <c:forEach var="book" items="${books}">
<a href="${pageContext.request.contextPath}/bookdetail?id=${book.id}" class="book-card" style="text-decoration: none; color: inherit;">
    <img src="${book.imageURL}" alt="${book.title}">
    <h2>${book.title}</h2>
    <div>
        <c:set var="discountValue" value="${empty book.discount ? 0 : book.discount}" />
        <c:set var="finalPrice" value="${book.price * (1 - discountValue / 100.0)}" />

        <span class="book-price">
            <fmt:formatNumber value="${finalPrice}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
        </span>

        <c:if test="${discountValue > 0}">
            <span class="old-price">
                <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
            </span>
            <span class="discount-badge">-${discountValue}%</span>
        </c:if>
    </div>
</a>
    </c:forEach>
</div>

</body>
</html>
