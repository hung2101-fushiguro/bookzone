<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>BookZone - Phụ kiện sách</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f5f5;
            }

            /* HEADER TOP & MAIN - giữ y như HOME */
            .header_top {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 30px;
                background: linear-gradient(90deg, #2193b0, #6dd5ed);
                color: white;
                font-size: 14px;
            }

            .header_top a {
                color: white;
                text-decoration: none;
                margin-left: 15px;
            }

            .header_top a:hover {
                text-decoration: underline;
            }

            .header_main {
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 15px 30px;
                background-color: white;
                border-bottom: 2px solid #e0e0e0;
            }

            .header_logo img {
                height: 80px;
                width: auto;
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

            .search-button:hover {
                background: linear-gradient(90deg, #1976d2, #64b5f6);
            }

            .header_utilities {
                display: flex;
                gap: 20px;
                align-items: center;
                font-size: 14px;
            }

            .utility_link {
                color: #333;
                text-decoration: none;
            }

            .utility_link:hover {
                color: #00a651;
            }

            /* DANH MỤC NGANG */
            .category-bar {
                background-color: #ffffff;
                border-bottom: 1px solid #ddd;
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                padding: 10px 0;
            }

            .category-bar a {
                margin: 5px 15px;
                text-decoration: none;
                color: #333;
                font-weight: 500;
                transition: color 0.3s;
            }

            .category-bar a:hover {
                color: #2193b0;
                border-bottom: 2px solid #2193b0;
            }

            /* TIÊU ĐỀ */
            h2.title {
                text-align: center;
                margin: 30px 0 20px;
                color: #2c3e50;
                text-transform: uppercase;
                letter-spacing: 1px;
            }

            /* GRID SẢN PHẨM */
            .product-grid {
                max-width: 1200px;
                margin: 0 auto 40px;
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: center;
                padding: 0 10px;
            }

            .product-card {
                background: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.05);
                width: 220px;
                text-align: center;
                transition: transform 0.3s, box-shadow 0.3s;
                padding: 15px;
            }

            .product-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 6px 15px rgba(0,0,0,0.1);
            }

            .product-card img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                border-radius: 4px;
                margin-bottom: 10px;
            }

            .product-card h4 {
                font-size: 16px;
                margin: 8px 0;
                color: #333;
                height: 40px;
                overflow: hidden;
            }

            .product-card p {
                font-size: 14px;
                color: #555;
                margin: 5px 0;
            }

            .product-card a {
                text-decoration: none;
                color: inherit;
            }

            .product-card a:hover h4 {
                color: #2193b0;
            }

            @media (max-width: 992px) {
                .product-card {
                    width: 30%;
                }
            }

            @media (max-width: 768px) {
                .product-card {
                    width: 45%;
                }
            }

            @media (max-width: 480px) {
                .product-card {
                    width: 100%;
                }
            }

            .product-price {
                font-size: 18px;
                font-weight: bold;
                color: #e53935;
                margin-top: 10px;
            }



        </style>
    </head>
    <body>

        <!-- HEADER -->
        <div class="header_top">
            <div>Hotline: <strong>0938 424 289</strong></div>
            <div>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span>${sessionScope.user.name}</span>
                        <a href="${pageContext.request.contextPath}/logout">| Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        <a href="${pageContext.request.contextPath}/register">| Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="header_main">
            <a class="header_logo" href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/image/logo1.jpg" alt="BookZone Logo">
            </a>
            <form class="header_search-box" action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" class="search-input" name="query" placeholder="Tìm kiếm sách...">
                <button type="submit" class="search-button">Tìm</button>
            </form>
            <div class="header_utilities">
                <c:set var="cartCount" value="0" />
                <c:if test="${not empty sessionScope.cart}">
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:set var="cartCount" value="${cartCount + item.quantity}" />
                    </c:forEach>
                </c:if>
                <a href="${pageContext.request.contextPath}/offers" class="utility_link"><strong>Ưu đãi & Tiện ích</strong></a>
                <a href="${pageContext.request.contextPath}/cart" class="utility_link"><strong>🛒 Giỏ hàng (${cartCount})</strong></a>
            </div>
        </div>

        <!-- DANH MỤC NGANG -->
        <div class="category-bar">
             <a href="accessorylist">Tất cả</a>
            <c:forEach var="cat" items="${categories}">
                <a href="accessorylist?category=${cat.id}">${cat.name}</a>
            </c:forEach>
        </div>

        <!-- TIÊU ĐỀ -->
        <h2 class="title">Danh sách phụ kiện</h2>

        <!-- GRID SẢN PHẨM -->
        <div class="product-grid">
            <c:forEach var="item" items="${accessories}">
                <div class="product-card">
                    <a href="accessorylist?action=detail&id=${item.id}">
                        <img src="${item.imageUrl}" alt="${item.name}" />
                        <h4>${item.name}</h4>
                    </a>
                    <div class="product-price">
                        <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>đ
                    </div>
                </div>

            </c:forEach>
        </div>
<jsp:include page="/footer.jsp" />
    </body>
</html>
